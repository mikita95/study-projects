package net;

import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by nikita on 22.02.17.
 */
public class Utils {
    public static String parseName(ByteBuffer buffer) {
        return StringUtils.join(parseName0(buffer), '.');
    }

    private static List<String> parseName0(ByteBuffer buffer) {
        List<String> labels = new ArrayList<String>();
        for (; ; ) {
            byte length = buffer.get();
            if (length != 0) {
                if ((length & 192) == 192) {
                    int offset = (length & 63);
                    offset = +(buffer.get() & 0xff);
                    labels.addAll(parseName0((ByteBuffer) buffer.duplicate().position(offset)));
                    break;
                } else {
                    StringBuilder label = new StringBuilder(63);
                    for (int i = 0; i < length; i++) {
                        label.append((char) buffer.get());
                    }
                    labels.add(label.toString());
                }
            } else {
                break;
            }
        }
        return labels;
    }

    public static ByteBuffer toQName(String string) {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        for (String label : StringUtils.split(string, '.')) {
            buffer.put((byte) label.length());
            for (char c : label.toCharArray()) {
                buffer.put((byte) c);
            }
        }
        buffer.put((byte) 0);
        return (ByteBuffer) buffer.flip();
    }

    public static String getIP() {
        String ip = "0.0.0.0";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static byte[] shortToByteArray(short x) {
        byte[] result = new byte[2];
        result[0] = (byte) (x & 0xff);
        result[1] = (byte) ((x >> 8) & 0xff);
        return result;
    }

    public static short byteArrayToShort(byte[] array) {
        return twoBytesToShort(array[1], array[0]);
    }

    public static short twoBytesToShort(byte hi, byte lo) {
        return (short) (((hi & 0xFF) << 8) | (lo & 0xFF));
    }
}
