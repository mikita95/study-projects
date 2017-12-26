package api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by nikita on 10.10.16.
 */

public class TwitterHashTagSearcherTest {
    private final TwitterHashTagSearcher searcher = new TwitterHashTagSearcher();
    @Test
    public void testGetStringByQuery() throws Exception {
        TwitterHashTagSearchQuery query = new TwitterHashTagSearchQuery("window");
        String result = searcher.getStringByQuery(query);
        Assert.assertTrue(result.length() > 0);
    }

    @Test
    public void testGetJsonByQuery() throws Exception {
        TwitterHashTagSearchQuery query = new TwitterHashTagSearchQuery("window", 20);
        JSONObject result = searcher.getJsonByQuery(query);
        Assert.assertTrue(result.toString().length() > 0);

        JSONArray posts = result.getJSONArray("statuses");
        Assert.assertTrue(posts.length() <= 20);
    }


}