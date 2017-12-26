package net;

import messages.DNSMessage;
import messages.Record;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by nikita on 24.02.17.
 */
public class MDNSProducer extends MDNSNode {

    public MDNSProducer(String name, int port) throws IOException {
        super(name, port, ServiceType.PRODUCER);
        this.logger = LoggerFactory.getLogger(MDNSProducer.class);
    }

    @Override
    protected void work() {
    }

    @Override
    protected void unicastListen() {
        for (; ; ) {
            try {
                ServerSocket welcomeSocket = new ServerSocket(serviceInfo.netInfo.port);

                logger.info("Unicast socket is listening...");
                Socket connectionSocket = welcomeSocket.accept();
                logger.info("Accepted from {}", connectionSocket.getInetAddress().getHostName());

                int readBytes;
                byte[] buffer = new byte[5 * 1024];
                byte[] readData;
                ByteBuffer clientData = ByteBuffer.allocate(5 * 1024);

                while ((readBytes = connectionSocket.getInputStream().read(buffer)) > -1) {
                    readData = new byte[readBytes];
                    System.arraycopy(buffer, 0, readData, 0, readBytes);
                    clientData.put(readData);
                }
                clientData.flip();
                connectionSocket.shutdownInput();

                DNSMessage clientMessage = DNSMessage.parse(clientData);
                Record record = clientMessage.getAdditionalRecords().get(0);
                String fileName = new String(record.getRData().array());

                logger.info("Request for file [{}] was received", fileName);

                BufferedOutputStream outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
                File fileToSend = new File(fileName);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileToSend));

                byte[] fileByteArray = new byte[(int) fileToSend.length()];
                bis.read(fileByteArray, 0, fileByteArray.length);

                logger.info("Writing file {} to the client...", fileName);

                ByteBuffer fileByteBuffer = ByteBuffer.allocate((int) (fileToSend.length() + 8));

                long dataStartTime = System.currentTimeMillis();

                fileByteBuffer.putLong(dataStartTime);
                fileByteBuffer.put(fileByteArray);
                fileByteBuffer.flip();

                outToClient.write(fileByteBuffer.array(), 0, fileByteBuffer.array().length);
                outToClient.flush();

                fileByteBuffer = ByteBuffer.allocate(8);
                long dataFinishTime = System.currentTimeMillis();
                fileByteBuffer.putLong(dataFinishTime);
                fileByteBuffer.flip();

                outToClient.write(fileByteBuffer.array(), 0, fileByteBuffer.array().length);
                outToClient.flush();

                connectionSocket.shutdownOutput();

                logger.info("Finished write file {}", fileName);

                connectionSocket.close();
                welcomeSocket.close();
            } catch (IOException e) {
                logger.error("Unicast listening error", e);
            }

        }
    }

    public static void main(String[] args) throws IOException {
        Thread t = new Thread(new MDNSProducer("Nikita", 5354));
        t.run();
    }
}
