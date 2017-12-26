package api;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nikita on 30.09.16.
 */
public class URLSearchQuery {
    private URL url;

    public URLSearchQuery(URL url) {
        this.url = url;
    }

    public URLSearchQuery(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
