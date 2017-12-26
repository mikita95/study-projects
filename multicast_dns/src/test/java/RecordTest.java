import messages.Record;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Created by Nikita_Markovnikov on 2/22/2017.
 */
public class RecordTest {
    @Test
    public void testRecord() throws Exception {
        String dataString = "Eminem is a rap god";
        byte[] dataArray = dataString.getBytes();
        ByteBuffer data = ByteBuffer.wrap(dataArray);

        Record record = new Record("Nikita", Record.Type.A, true, Record.RRClass.IN, 30303, (short)dataArray.length, data);

        ByteBuffer buffer = record.toByteBuffer();
        Record result = Record.parse(buffer);

        Assert.assertEquals(result, record);
    }

}