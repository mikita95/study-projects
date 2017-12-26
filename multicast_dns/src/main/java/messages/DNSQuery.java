package messages;

import net.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by Nikita_Markovnikov on 2/21/2017.
 */
public class DNSQuery {
    private final static Logger logger = LoggerFactory.getLogger(DNSQuery.class);

    private final String name;
    private final Record.Type type;
    private final Record.RRClass rrClass;
    private final Boolean unicastResponse;

    public DNSQuery(String name, Record.Type type, Record.RRClass rrClass, Boolean unicastResponse) {
        this.name = name;
        this.type = type;
        this.rrClass = rrClass;
        this.unicastResponse = unicastResponse;

        logger.debug("DNS Query was created: {}", this.toString());
    }

    public DNSQuery(String name, Record.Type type, Record.RRClass rrClass) {
        this(name, type, rrClass, false);
    }

    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(516);

        ByteBuffer qName = Utils.toQName(name);
        buffer.put(qName);
        buffer.putShort(type.value());

        short temp = rrClass.value();
        if (unicastResponse) temp |= (1 << 15);
        buffer.putShort(temp);

        logger.debug("Convert DNS Query to ByteBuffer");
        return (ByteBuffer) buffer.flip();
    }

    public static DNSQuery parse(ByteBuffer buffer) {
        logger.debug("Parsing DNS Query...");

        String name = Utils.parseName(buffer);
        Record.Type type = Record.Type.parse(buffer.getShort());
        short temp = buffer.getShort();
        Boolean unicastResponse = false;
        if (((temp >> 15) & 1) == 1) {
            unicastResponse = true;
            temp &= ~(1 << 15);
        }
        Record.RRClass clazz = Record.RRClass.parse(temp);

        return new DNSQuery(name, type, clazz, unicastResponse);

    }

    @Override
    public String toString() {
        return String.format("name = %s, type = %s, class = %s, unicast_response = %s", name, type, rrClass, unicastResponse);
    }

    public String name() {
        return name;
    }

    public Record.Type type() {
        return type;
    }

    public Record.RRClass rrClass() {
        return rrClass;
    }

    public Boolean getUnicastResponse() {
        return unicastResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DNSQuery query = (DNSQuery) o;

        if (!name.equals(query.name)) return false;
        if (type != query.type) return false;
        if (rrClass != query.rrClass) return false;
        return unicastResponse.equals(query.unicastResponse);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + rrClass.hashCode();
        result = 31 * result + unicastResponse.hashCode();
        return result;
    }
}
