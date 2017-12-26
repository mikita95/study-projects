package api;


import org.json.JSONObject;

/**
 * Created by nikita on 30.09.16.
 */
public interface TwitterSearcher {
    public String getStringByQuery(URLSearchQuery query);
    public JSONObject getJsonByQuery(URLSearchQuery query);
}
