import messages.DNSMessage;
import messages.DNSQuery;
import messages.Record;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by Nikita_Markovnikov on 2/22/2017.
 */
public class DNSMessageTest {
    @Test
    public void testDNSMessage() throws Exception {
        DNSMessage.Builder builder = DNSMessage.newBuilder();
        builder
                .setId((short)5)
                .setQR(true)
                .setAD(true)
                .setZ(true)
                .setOpCode(DNSMessage.OpCode.NOTIFY)
                .setRCode(DNSMessage.RCode.REFUSED)
                .addDNSQuery(new DNSQuery("Nikita", Record.Type.A, Record.RRClass.CH, true))
                .addServersName(new Record("Nikita", Record.Type.A, Record.RRClass.HS, 12345))
                .addAnswer(new Record("Serega", Record.Type.AXFR, Record.RRClass.ANY, 54321))
                .addAdditionalRecord(new Record("Ufa", Record.Type.AXFR, Record.RRClass.IN, 777));
        DNSMessage message = builder.build();

        ByteBuffer buffer = message.toByteBuffer();
        DNSMessage result = DNSMessage.parse(buffer);

        Assert.assertEquals(message, result);
    }
}