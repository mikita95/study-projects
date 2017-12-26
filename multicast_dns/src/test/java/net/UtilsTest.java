package net;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nikita on 05.03.17.
 */
public class UtilsTest {

    @Test
    public void testByteArrayToShort() throws Exception {
        short t = 78;
        Assert.assertEquals(t, Utils.byteArrayToShort(Utils.shortToByteArray(t)));
    }
}