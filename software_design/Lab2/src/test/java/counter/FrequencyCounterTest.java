package counter;

import api.TwitterHashTagDatesReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;

/**
 * Created by nikita on 10.10.16.
 */
public class FrequencyCounterTest {

    private FrequencyCounter counter;

    @Mock
    private TwitterHashTagDatesReader client;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.counter = new FrequencyCounter(client);
    }

    @Test
    public void testCounter() throws Exception {
        String hashTag = "window";
        int n = 6;
        Date until = new Date(1_000_000_000L);
        ArrayList<Date> toReturn = createAnswer();
        Collections.reverse(toReturn);
        when(client.getDates(hashTag, until))
                .thenReturn(toReturn);
        int[] answer = counter.countUntilDate(hashTag, until);
        Assert.assertArrayEquals(new int[]{3, 1, 1, 1, 1, 1}, answer);
    }

    private ArrayList<Date> createAnswer() {
        return new ArrayList<>(
                Arrays.asList(
                        new Date(1_000_000_005L),
                        new Date(1_010_000_020L),
                        new Date(1_010_000_080L),
                        new Date(1_020_002_000L),
                        new Date(1_020_070_005L),
                        new Date(1_020_090_005L),
                        new Date(1_020_100_005L),
                        new Date(1_020_200_005L)
                )
        );
    }
}