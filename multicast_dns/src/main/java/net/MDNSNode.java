package net;

import messages.DNSMessage;
import messages.DNSQuery;
import messages.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Nikita_Markovnikov on 2/21/2017.
 */
public abstract class MDNSNode implements Runnable {
    protected Logger logger = LoggerFactory.getLogger(DNSQuery.class);

    protected final static String MULTICAST_ADDRESS = "224.0.0.251";
    protected final static int MULTICAST_PORT = 5353;

    protected final MulticastSocket multicastSocket;
    protected Socket fileSocket;

    protected final DNSCache.ServiceInfo serviceInfo;

    protected final DNSCache cache;

    public enum ServiceType {
        CONSUMER(0),
        PRODUCER(1),
        EXECUTOR(2);

        private final short value;

        ServiceType(int value) {
            this.value = (short) value;
        }

        public static ServiceType parse(short i) {
            for (ServiceType t : values()) {
                if (t.value == i) return t;
            }
            return null;
        }

        public short value() {
            return value;
        }
    }

    public MDNSNode(String name, int port, ServiceType serviceType) throws IOException {
        this.serviceInfo = new DNSCache.ServiceInfo(name, new DNSCache.NetInfo(Utils.getIP(), port), serviceType);

        this.multicastSocket = new MulticastSocket(MULTICAST_PORT);
        this.multicastSocket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));

        this.cache = new DNSCache();

        logger.info("Node {} of type {} was created", name, serviceType);
    }

    protected void sayHello() {
        logger.debug("Saying hello...");
        DNSMessage.Builder builder = DNSMessage.newBuilder();

        ByteBuffer info = ByteBuffer.wrap(serviceInfo.netInfo.address.getBytes());
        ByteBuffer port = ByteBuffer.wrap(String.valueOf(serviceInfo.netInfo.port).getBytes());
        ByteBuffer type = ByteBuffer.wrap(Utils.shortToByteArray(serviceInfo.serviceType.value()));

        builder
                .setQR(true)
                .setAA(true)
                .addServersName(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) info.array().length, info))
                .addServersName(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) port.array().length, port))
                .addAdditionalRecord(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) type.array().length, type));

        sendMessage(multicastSocket, builder.build(), MULTICAST_PORT, MULTICAST_ADDRESS);
    }

    protected final void sendMessage(DatagramSocket socket, DNSMessage message, int port, String address) {
        ByteBuffer buffer = message.toByteBuffer();

        DatagramPacket packet = new DatagramPacket(buffer.array(), buffer.arrayOffset(), buffer.array().length);
        packet.setPort(port);

        try {
            packet.setAddress(InetAddress.getByName(address));
            socket.send(packet);
        } catch (IOException e) {
            logger.error("Error while sending message", e);
        }

        logger.debug("Message sent on port = {}, address = {}, message = {}", port, address, message);
    }

    protected final DNSMessage getMessage(DatagramSocket socket) throws DNSException {
        logger.debug("Waiting for message on socket: {}", socket);

        DatagramPacket p = new DatagramPacket(new byte[2048], 2048);

        try {
            socket.receive(p);
        } catch (IOException e) {
            logger.error("Error while receiving message on socket: {}", socket.toString());
            throw new DNSException(e);
        }

        ByteBuffer b = ByteBuffer.wrap(p.getData(), p.getOffset(), p.getLength());
        DNSMessage message = DNSMessage.parse(b);

        logger.debug("Message was received: {}", message);
        return message;
    }

    protected final void handleHelloMessage(DNSMessage message) {
        Record address = message.getServersName().get(0);
        Record port = message.getServersName().get(1);
        Record type = message.getAdditionalRecords().get(0);

        ServiceType nodeType = ServiceType.parse(Utils.byteArrayToShort(type.getRData().array()));

        logger.debug("Hello message was received from {}, so caching it", address.name());
        DNSCache.NetInfo netInfo = new DNSCache.NetInfo(new String(address.getRData().array()),
                Integer.valueOf(new String(port.getRData().array())));

        DNSCache.ServiceInfo serviceInfo;

        if (nodeType == ServiceType.EXECUTOR) {
            Record load = message.getAdditionalRecords().get(1);
            ByteBuffer loadBuffer = load.getRData();
            serviceInfo = new DNSCache.ExecutorInfo(address.name(), netInfo, nodeType, loadBuffer.getInt());
        } else
            serviceInfo = new DNSCache.ServiceInfo(address.name(), netInfo, nodeType);

        cache.add(address.name(), serviceInfo);

        logger.debug("Cache state:\n{}", cache);
    }

    protected final void handleHelloQueryMessage(DNSMessage message) {
        DNSQuery query = message.getQuestions().get(0);
        String requiredName = query.name();

        logger.debug("Query for hello was received for {}", requiredName);

        if (requiredName.equals(serviceInfo.name)) {
            sayHello();
        }
    }

    protected final void requestForNetInfo(String name) {
        DNSMessage.Builder builder = DNSMessage.newBuilder();
        builder
                .setQR(false)
                .setAA(true)
                .addDNSQuery(new DNSQuery(name, Record.Type.A, Record.RRClass.IN, false));

        DNSMessage findMessage = builder.build();
        sendMessage(multicastSocket, findMessage, MULTICAST_PORT, MULTICAST_ADDRESS);
    }

    protected class Checker extends TimerTask {

        @Override
        public void run() {
            sendCallbackMessage();
            cache.clear();

            Timer timer = new Timer();
            timer.schedule(new Checker(), 10000);
        }
    }

    protected void sendCallbackMessage() {
        DNSMessage.Builder builder = DNSMessage.newBuilder();
        builder
                .setQR(false)
                .setRA(true)
                .setAA(true)
                .addDNSQuery(new DNSQuery(serviceInfo.name, Record.Type.A, Record.RRClass.IN, false));

        DNSMessage findMessage = builder.build();
        sendMessage(multicastSocket, findMessage, MULTICAST_PORT, MULTICAST_ADDRESS);
    }

    public final void run() {
        sayHello();

        Thread listenThread = new Thread(this::multicastListen);
        listenThread.start();

        Thread unicastListenThread = new Thread(MDNSNode.this::unicastListen);
        unicastListenThread.setDaemon(true);
        unicastListenThread.start();

        Thread thread = new Thread(new Checker());
        thread.start();

        work();
    }

    protected void multicastListen() {
        for (; ; ) {
            try {
                DNSMessage message;
                message = getMessage(multicastSocket);
                if (message.getQR() && message.getAA()) {
                    handleHelloMessage(message);
                } else if (!message.getQR() && message.getRA() && message.getAA()) {
                    sayHello();
                } else if (!message.getQR() && message.getAA()) {
                    handleHelloQueryMessage(message);
                } else {
                    logger.debug("Unknown message:\n{}", message);
                }
            } catch (Exception e) {
                logger.error("Multicast listening error", e);
            }
        }
    }

    protected abstract void work();

    protected abstract void unicastListen();
}
