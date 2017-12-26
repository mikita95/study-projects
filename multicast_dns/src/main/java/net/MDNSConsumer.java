package net;

import messages.DNSMessage;
import messages.DNSQuery;
import messages.Record;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Nikita_Markovnikov on 2/21/2017.
 */
public class MDNSConsumer extends MDNSNode {

    private final Map<Integer, String> executions;
    private AtomicInteger executionCounter;

    public MDNSConsumer(String name, int port) throws IOException {
        super(name, port, ServiceType.CONSUMER);
        this.logger = LoggerFactory.getLogger(MDNSConsumer.class);
        this.executions = new ConcurrentHashMap<>();
        this.executionCounter = new AtomicInteger(0);
    }

    private class TimeStatistics {
        public long requestTime;
        public long sendStartTime;
        public long sendFinishTime;
        public long receiveFinishTime;

        @Override
        public String toString() {
            return String.format("Request time:\t\t\t%s\nSending start time:\t\t%s\nSending finish time:\t%s\nReceiving finish:\t\t%s",
                    requestTime, sendStartTime, sendFinishTime, receiveFinishTime);
        }
    }

    protected void work() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (; ; ) {
            try {
                String request = br.readLine().trim();
                String[] split = request.split(" ");
                switch (split[0]) {
                    case "hosts": {
                        System.out.println("Known hosts:");
                        System.out.println(cache);
                        break;
                    }
                    case "ask": {
                        System.out.println("Enter file name asking for");
                        try {
                            String aimName = split[1];
                            String fileName = br.readLine().trim();
                            if (!cache.contains(aimName)) {
                                requestForNetInfo(aimName);
                                Thread.sleep(3000);

                                if (!cache.contains(aimName)) {
                                    System.out.println("Sorry, there is no such producer");
                                    continue;
                                }
                            }

                            if (cache.get(aimName).serviceType != ServiceType.PRODUCER) {
                                System.out.println(String.format("Service %s is of type %s, you can ask only %s",
                                        aimName, cache.get(aimName).serviceType, ServiceType.PRODUCER));
                                continue;
                            }

                            fileTransfer(aimName, fileName);
                        } catch (IOException e) {
                            System.out.println("Try again");
                            continue;
                        }
                        break;
                    }
                    case "execute": {
                        System.out.println("Enter script file name to execute:");
                        try {
                            String fileName = br.readLine().trim();
                            executeFile(fileName);
                        } catch (IOException e) {
                            System.out.println("Try again");
                            continue;
                        }
                        break;
                    }
                    case "stop": {
                        System.out.println("Enter executor id to stop");
                        try {
                            Integer id  = Integer.parseInt(br.readLine().trim());
                            DNSMessage.Builder builder = DNSMessage.newBuilder();
                            ByteBuffer buffer = ByteBuffer.wrap(executions.get(id).getBytes());
                            DNSMessage message = builder
                                    .setZ(true)
                                    .setQR(false)
                                    .addAdditionalRecord(new Record(serviceInfo.name, Record.Type.ANY,
                                            false, Record.RRClass.ANY,
                                            120, (short) buffer.array().length, buffer)).build();
                            sendMessage(multicastSocket, message, MULTICAST_PORT, MULTICAST_ADDRESS);
                            logger.info("Send message to stop execution with id = {}", id);
                            executions.remove(id);
                        } catch (IOException e) {
                            System.out.println("Try again");
                            continue;
                        }
                        break;
                    }
                    case "executions": {
                        for (Map.Entry<Integer, String> entry: executions.entrySet()) {
                            System.out.println(String.format("id = [%d] with uuid = \t[%s]", entry.getKey(), entry.getValue()));
                        }
                        break;
                    }
                    default: {
                        System.out.println("USAGE: ask [server name]");
                    }
                }
            } catch (Exception e) {
                logger.error("Error in consumer work", e);
            }
        }
    }

    private void executeFile(String fileName) throws Exception {
        sendCallbackMessage();
        Thread.sleep(1000);

        Optional<DNSCache.ExecutorInfo> executorOpt = cache.services().stream()
                .filter(s -> {
                    if (s.serviceType == ServiceType.EXECUTOR) {
                        DNSCache.ExecutorInfo executorInfo = (DNSCache.ExecutorInfo) s;
                        return executorInfo.load < MDNSExecutor.MAX_LOAD;
                    }
                    return false;
                })
                .findAny().map(o -> (DNSCache.ExecutorInfo) o);

        if (!executorOpt.isPresent()) {
            System.out.println("Sorry, all executors are busy. Please, call back later");
            throw new DNSException();
        }
        DNSCache.ServiceInfo executor = executorOpt.get();
        logger.info("There was chosen executor: {}", executor);

        fileSocket = new Socket(executor.netInfo.address, executor.netInfo.port);
        BufferedOutputStream outToExecutor = new BufferedOutputStream(fileSocket.getOutputStream());

        File script = new File(fileName);

        byte[] fileByteArray = new byte[(int) script.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(script));
        int readBytes = bis.read(fileByteArray, 0, fileByteArray.length);

        byte[] uuid = UUID.randomUUID().toString().getBytes();

        ByteBuffer sendByteBuffer = ByteBuffer.allocate(fileByteArray.length + uuid.length);
        sendByteBuffer.put(uuid);
        sendByteBuffer.put(fileByteArray);
        sendByteBuffer.flip();

        logger.info("Sending script {} to the executor...", fileName);

        executions.put(executionCounter.get(), new String(uuid));
        executionCounter.incrementAndGet();

        outToExecutor.write(sendByteBuffer.array(), sendByteBuffer.arrayOffset(), sendByteBuffer.array().length);
        outToExecutor.flush();
        fileSocket.shutdownOutput();

        InputStream is = fileSocket.getInputStream();

        new Thread(() -> {
            StringWriter writer = new StringWriter();
            try {
                byte[] time = new byte[8];
                is.read(time, 0, 8);
                long executionTime = Utils.bytesToLong(time);

                IOUtils.copy(is, writer, "UTF-8");
                final String answer = writer.toString();

                logger.info("Result was received [execution time = {}]:\n{}", executionTime, answer);
            } catch (IOException e) {
                logger.error("Error in consumer work", e);
            }
        }).start();
    }

    private void fileTransfer(String aimName, String fileName) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(fileName.getBytes());
        DNSMessage.Builder builder = DNSMessage.newBuilder();
        builder
                .setQR(false)
                .addDNSQuery(new DNSQuery(aimName, Record.Type.ANY, Record.RRClass.IN, true))
                .addAdditionalRecord(new Record(serviceInfo.name, Record.Type.ANY, false, Record.RRClass.ANY, 120,
                        (short) buffer.array().length, buffer));

        DNSMessage message = builder.build();
        ByteBuffer askBuffer = message.toByteBuffer();

        logger.debug("Sending info: {}", askBuffer.array());

        TimeStatistics timeStatistics = new TimeStatistics();

        fileSocket = new Socket(cache.get(aimName).netInfo.address, cache.get(aimName).netInfo.port);
        DataOutputStream outputStream = new DataOutputStream(fileSocket.getOutputStream());
        outputStream.write(askBuffer.array(), askBuffer.arrayOffset(), askBuffer.array().length);
        outputStream.flush();

        timeStatistics.requestTime = System.currentTimeMillis();

        fileSocket.shutdownOutput();

        logger.info("Request for file [{}] was sent", fileName);
        InputStream is = fileSocket.getInputStream();

        if (is != null) {
            try {
                logger.info("Start receiving the file...");
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("new_" + fileName));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] time = new byte[8];
                is.read(time, 0, 8);
                timeStatistics.sendStartTime = Utils.bytesToLong(time);

                byte[] aByte = new byte[1];

                LinkedList<Byte> queue = new LinkedList<>();
                while (is.read(aByte) != -1) {
                    queue.addFirst(aByte[0]);
                    if (queue.size() == 9) {
                        baos.write(new byte[]{queue.pollLast()});
                    }
                }

                timeStatistics.receiveFinishTime = System.currentTimeMillis();

                Byte[] queueArray = queue.toArray(new Byte[8]);
                for (int i = 0; i < 8; i++) {
                    time[i] = queueArray[8 - i - 1];
                }

                timeStatistics.sendFinishTime = Utils.bytesToLong(time);

                fileSocket.shutdownInput();

                bos.write(baos.toByteArray());
                bos.flush();
                bos.close();

                logger.info("File was received and saved as [{}]", "new_" + fileName);
                logger.info("Time statistics:\n{}", timeStatistics);

                fileSocket.close();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void unicastListen() {

    }

    public static void main(String[] args) throws IOException {
        Thread t = new Thread(new MDNSConsumer("Petr", 5355));
        t.run();
    }
}