package api;

/**
 * Created by nikita on 30.09.16.
 */
public class TwitterHashTagSearchQuery extends URLSearchQuery {

    private final static String URL_BASE = "https://api.twitter.com/1.1/search/tweets.json?q=%23";

    private String hashTag;
    private int count = 100;
    private String maxID = null;


    public TwitterHashTagSearchQuery(String hashTag) {
        super(URL_BASE + hashTag + "&result_type=recent&count=100");
        this.hashTag = hashTag;
    }

    public TwitterHashTagSearchQuery(String hashTag, int count) {
        super(URL_BASE + hashTag + "&result_type=recent&count=" + count);
        this.hashTag = hashTag;
    }

    public TwitterHashTagSearchQuery(String hashTag, String maxID) {
        super(URL_BASE + hashTag + "&max_id=" + maxID + "&result_type=recent&count=100");
        this.hashTag = hashTag;
        this.maxID = maxID;
    }

    public TwitterHashTagSearchQuery(String hashTag, int count, String maxID) {
        super(URL_BASE + hashTag + "&max_id=" + maxID + "&result_type=recent&count=" + count);
        this.hashTag = hashTag;
        this.maxID = maxID;
        this.count = count;
    }


    public String getHashTag() {
        return hashTag;
    }

    public int getCount() {
        return count;
    }

    public String getMaxID() {
        return maxID;
    }

    public void setMaxID(String maxID) {
        this.maxID = maxID;
    }
}
