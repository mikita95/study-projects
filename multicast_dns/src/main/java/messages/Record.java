package messages;

import net.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by Nikita_Markovnikov on 2/21/2017.
 */
public class Record {
    private final static Logger logger = LoggerFactory.getLogger(DNSQuery.class);

    private final String name;
    private final Type type;
    private final Boolean cacheFlush;
    private final RRClass rrClass;
    private final int ttl;
    private final short rdLength;
    private final ByteBuffer rData;

    public Record(String name, Type type, RRClass rrClass, int ttl) {
        this(name, type, false, rrClass, ttl, (short) 0, ByteBuffer.allocate(0));
    }

    public Record(String name, Type type, Boolean cacheFlush, RRClass rrClass, int ttl, short rdLength, ByteBuffer rData) {
        this.name = name;
        this.type = type;
        this.cacheFlush = cacheFlush;
        this.rrClass = rrClass;
        this.ttl = ttl;
        this.rdLength = rdLength;
        this.rData = rData;

        logger.debug("messages.Record was created: {}", this.toString());
    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(2048);
        ByteBuffer nameBuffer = Utils.toQName(name);

        buffer.put(nameBuffer);
        buffer.putShort(type.value());

        short temp = rrClass.value();
        if (cacheFlush) temp |= (1 << 15);
        buffer.putShort(temp);

        buffer.putInt(ttl);
        buffer.putShort(rdLength);
        buffer.put(rData);

        rData.position(0);

        logger.debug("Convert messages.Record to ByteBuffer");
        return (ByteBuffer) buffer.flip();
    }

    public static Record parse(ByteBuffer buffer) {
        logger.debug("Parsing messages.Record...");

        String name = Utils.parseName(buffer);
        short type = buffer.getShort();

        short temp = buffer.getShort();
        Boolean cacheFlush = false;
        if (((temp >> 15) & 1) == 1) {
            cacheFlush = true;
            temp &= ~(1 << 15);
        }
        short rrClass = temp;

        int ttl = buffer.getInt();
        short rdlength = buffer.getShort();

        int bufferPosition = buffer.position();
        byte[] data = Arrays.copyOfRange(buffer.array(), bufferPosition, bufferPosition + rdlength);
        ByteBuffer rData = ByteBuffer.wrap(data);

        buffer.position(bufferPosition + rdlength);
        return new Record(name, Type.parse(type), cacheFlush, RRClass.parse(rrClass), ttl, rdlength, rData);
    }

    @Override
    public String toString() {
        return String.format("name = %s, type = %s, class = %s, ttl = %d", name, type, rrClass, ttl);
    }

    public String name() {
        return name;
    }

    public Type type() {
        return type;
    }

    public Boolean cacheFlush() {
        return cacheFlush;
    }

    public RRClass rrClass() {
        return rrClass;
    }

    public int ttl() {
        return ttl;
    }

    public int rdLength() {
        return rdLength;
    }

    public ByteBuffer getRData() {
        return rData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (ttl != record.ttl) return false;
        if (rdLength != record.rdLength) return false;
        if (!name.equals(record.name)) return false;
        if (type != record.type) return false;
        if (!cacheFlush.equals(record.cacheFlush)) return false;
        if (rrClass != record.rrClass) return false;
        return rData.equals(record.rData);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + cacheFlush.hashCode();
        result = 31 * result + rrClass.hashCode();
        result = 31 * result + ttl;
        result = 31 * result + (int) rdLength;
        result = 31 * result + rData.hashCode();
        return result;
    }

    public enum Type {
        A(1),
        AAAA(28),
        AFSDB(18),
        CERT(37),
        DHCID(49),
        DLV(32769),
        DNAME(39),
        DNSKEY(48),
        DS(43),
        HIP(55),
        IPSECKEY(45),
        KEY(25),
        LOC(29),
        NAPTR(35),
        NSEC(47),
        NSEC3(50),
        NSEC3PARAM(51),
        RRSIG(46),
        SIG(24),
        SPF(99),
        SRV(33),
        SSHFP(44),
        TA(32768),
        NS(2),
        MD(3),
        MF(4),
        CNAME(5),
        SOA(6),
        MB(7),
        MG(8),
        MR(9),
        NULL(10),
        WKS(11),
        PTR(12),
        HINFO(13),
        MINFO(14),
        MX(15),
        TXT(16),
        OPT(41),
        TKEY(249),
        TSIG(250),
        IXFR(251),
        AXFR(252),
        MAILB(253),
        MAILA(254),
        ANY(255);

        private final short value;

        Type(int value) {
            this.value = (short) value;
        }

        public static Type parse(short i) {
            for (Type t : values()) {
                if (t.value == i) return t;
            }
            return null;
        }

        public short value() {
            return value;
        }
    }

    public enum RRClass {
        IN(1),
        CS(2),
        CH(3),
        HS(4),
        ANY(255);

        private final short value;

        RRClass(int value) {
            this.value = (short) value;
        }

        public static RRClass parse(short i) {
            for (RRClass c : values()) {
                if (c.value == i) return c;
            }
            return null;
        }

        public short value() {
            return value;
        }
    }
}
