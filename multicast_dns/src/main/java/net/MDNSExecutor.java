package net;

import messages.DNSMessage;
import messages.Record;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.StopWatch;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by nikita on 05.03.17.
 */
public class MDNSExecutor extends MDNSNode {

    public static final int MAX_LOAD = 10;
    private final ThreadPoolExecutor executorService;
    private final Map<String, Future<?>> threadID;

    public MDNSExecutor(String name, int port) throws IOException {
        super(name, port, ServiceType.EXECUTOR);
        this.executorService = new ThreadPoolExecutor(MAX_LOAD, MAX_LOAD, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        this.threadID = new ConcurrentHashMap<>();
    }

    @Override
    protected void work() {

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
                } else if (!message.getQR() && message.getZ()) {
                    stopThread(message);
                } else {
                    logger.debug("Unknown message:\n{}", message);
                }
            } catch (Exception e) {
                logger.error("Multicast listening error", e);
            }
        }
    }

    private void stopThread(DNSMessage dnsMessage) {
        Record uuidRecord = dnsMessage.getAdditionalRecords().get(0);
        String uuid = new String(uuidRecord.getRData().array());
        if (!threadID.containsKey(uuid)) return;
        if (!threadID.get(uuid).isCancelled()) {
            threadID.get(uuid).cancel(true);
            threadID.remove(uuid);
        }
        logger.info("Thread with uuid [] was interrupted", uuid);
    }

    @Override
    protected void sayHello() {
        logger.debug("Saying hello...");
        DNSMessage.Builder builder = DNSMessage.newBuilder();

        ByteBuffer info = ByteBuffer.wrap(serviceInfo.netInfo.address.getBytes());
        ByteBuffer port = ByteBuffer.wrap(String.valueOf(serviceInfo.netInfo.port).getBytes());
        ByteBuffer type = ByteBuffer.wrap(Utils.shortToByteArray(serviceInfo.serviceType.value()));
        ByteBuffer load = ByteBuffer.allocate(4);
        load.putInt(executorService.getActiveCount());
        load.flip();

        builder
                .setQR(true)
                .setAA(true)
                .addServersName(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) info.array().length, info))
                .addServersName(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) port.array().length, port))
                .addAdditionalRecord(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) type.array().length, type))
                .addAdditionalRecord(new Record(serviceInfo.name, Record.Type.A, false, Record.RRClass.IN, 120, (short) load.array().length, load));

        sendMessage(multicastSocket, builder.build(), MULTICAST_PORT, MULTICAST_ADDRESS);
    }

    @Override
    protected void unicastListen() {
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(serviceInfo.netInfo.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (; ; ) {
            try {
                logger.info("Unicast socket is listening... My load is {}", executorService.getActiveCount());
                assert welcomeSocket != null;
                Socket connectionSocket = welcomeSocket.accept();
                logger.info("Accepted from {}", connectionSocket.getInetAddress().getHostName());
                logger.info("Start receiving the script to execute...");

                InputStream is = connectionSocket.getInputStream();

                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");
                final String readString = writer.toString();

                String uuid = readString.substring(0, 36);
                String script = readString.substring(36, readString.length());

                connectionSocket.shutdownInput();
                logger.info("Script was received");
                logger.info("Executing it...");

                final Future<?> future = executorService.submit(() -> {
                    Thread help = null;
                    try {
                        Thread current = Thread.currentThread();
                        help = new Thread(() -> {

                            try {
                                while ((!threadID.get(uuid).isCancelled() || !threadID.get(uuid).isDone())) {
                                }
                            } catch (Exception ignored) {

                            } finally {
                                logger.info("CAC");
                                current.stop();
                                Thread.currentThread().interrupt();
                            }
                        });
                        help.setDaemon(true);
                        help.start();

                        StopWatch stopWatch = new StopWatch();
                        stopWatch.start();

                        final Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", script});

                        process.waitFor();

                        stopWatch.stop();

                        BufferedReader in =
                                new BufferedReader(new InputStreamReader(process.getInputStream()));

                        StringBuilder result = new StringBuilder();
                        String inputLine = in.readLine();
                        result.append(inputLine);
                        while ((inputLine = in.readLine()) != null) {
                            result.append("\n").append(inputLine);
                        }

                        in.close();

                        byte[] resultBytes = result.toString().getBytes();
                        ByteBuffer answer = ByteBuffer.allocate(resultBytes.length + 8);
                        answer.putLong(stopWatch.getTime());
                        answer.put(resultBytes);
                        answer.flip();

                        BufferedOutputStream outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
                        outToClient.write(answer.array(), 0, answer.array().length);
                        outToClient.flush();

                        connectionSocket.shutdownOutput();
                        connectionSocket.close();
                    } catch (Exception e) {
                        logger.error("Error in execution", e);
                    } finally {
                        Thread.currentThread().interrupt();
                    }
                });
                threadID.put(uuid, future);

                new Thread(() -> {
                    try {
                        while (!future.isDone() || !future.isCancelled()) {

                        }
                        if (!future.isCancelled())
                            future.get(1000, TimeUnit.MINUTES);
                    } catch (Exception ignored) {
                    }
                }).start();

            } catch (Exception e) {
                logger.error("Unicast listening error", e);
            }


        }
    }

    public static void main(String[] args) throws IOException {
        Thread t = new Thread(new MDNSExecutor("Ivan", 5356));
        t.run();
    }


}
