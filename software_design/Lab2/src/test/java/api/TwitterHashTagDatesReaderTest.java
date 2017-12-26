package api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Created by nikita on 14.10.16.
 */
public class TwitterHashTagDatesReaderTest {

    private TwitterHashTagDatesReader client;

    @Mock
    private TwitterHashTagSearcher searcher;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.client = new TwitterHashTagDatesReader(searcher);
    }

    @Test
    public void testGetDates() throws Exception {
        String hashTag = "window";
        Date until = new Date(1_000_000_000L);
        TwitterHashTagSearchQuery query = new TwitterHashTagSearchQuery(hashTag);
        ArrayList<Date> dates = getDates(until);
        Collections.reverse(dates);
        Mockito.when(searcher.getJsonByQuery(query))
                .thenReturn(makeJSON(dates));
        dates.remove(dates.size() - 1);
        ArrayList<Date> result = client.getDates(query, until);
        Assert.assertEquals(result.size(), dates.size());
        for (int i = 0; i < dates.size(); i++) {
            Assert.assertEquals(dates.get(i).toString(), result.get(i).toString());
        }
    }

    private ArrayList<Date> getDates(Date until) {
        return new ArrayList<>(
                Arrays.asList(
                        until,
                        new Date(1_000_001_018L),
                        new Date(1_010_001_020L),
                        new Date(1_010_001_080L),
                        new Date(1_020_002_000L),
                        new Date(1_020_070_005L),
                        new Date(1_020_090_005L),
                        new Date(1_020_100_005L),
                        new Date(1_020_200_005L)
                )
        );
    }

    private JSONObject makeJSON(ArrayList<Date> dates) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int i = 0;
        for (Date date: dates) {
            JSONObject dateJSON = new JSONObject();
            dateJSON.put("created_at", date.toString().replace("MSK", "+0000"));
            dateJSON.put("id_str", String.valueOf(i));
            array.put(dateJSON);
            i++;
        }
        jsonObject.put("statuses", array);
        return jsonObject;
    }
}