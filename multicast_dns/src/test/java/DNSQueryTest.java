import messages.DNSQuery;
import messages.Record;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by nikita on 22.02.17.
 */
public class DNSQueryTest {

    @Test
    public void testDNSQuery() throws Exception {
        DNSQuery query = new DNSQuery("Nikita", Record.Type.A, Record.RRClass.ANY, true);

        ByteBuffer buffer = query.toByteBuffer();
        DNSQuery result = DNSQuery.parse(buffer);

        Assert.assertEquals(query, result);
    }

}