package api;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nikita on 14.10.16.
 */
public class TwitterHashTagDatesReader {
    public static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss +0000 yyyy";

    private final TwitterHashTagSearcher searcher;

    public TwitterHashTagDatesReader(TwitterHashTagSearcher searcher) {
        this.searcher = searcher;
    }

    public ArrayList<Date> getDates(TwitterHashTagSearchQuery query, Date until) {
        JSONObject jsonObject = searcher.getJsonByQuery(query);
        assert jsonObject != null;

        ArrayList<Date> dates = new ArrayList<>();

        Boolean isFinished = false;
        do {
            JSONArray posts = jsonObject.getJSONArray("statuses");
            for (int i = 0; i < posts.length(); i++) {
                String date = posts.getJSONObject(i).getString("created_at");
                Date postDate = DateUtils.getDate(date, DATE_FORMAT);
                if (postDate != null && postDate.after(until))
                    dates.add(postDate);
                else {
                    isFinished = true;
                    break;
                }
            }

            if (!isFinished) {
                JSONObject last = posts.getJSONObject(posts.length() - 1);
                String id = last.getString("id_str");

                query.setMaxID(id);
                jsonObject = searcher.getJsonByQuery(query);
            }

        } while (!isFinished);
        return dates;
    }

    public ArrayList<Date> getDates(String hashTag, Date until) {
        return getDates(new TwitterHashTagSearchQuery(hashTag), until);
    }

}
