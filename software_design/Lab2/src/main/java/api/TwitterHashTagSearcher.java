package api;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.json.JSONObject;
import utils.ConfigPropertiesReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URLConnection;

/**
 * Created by nikita on 30.09.16.
 */
public class TwitterHashTagSearcher implements TwitterSearcher {

    @Override
    public String getStringByQuery(URLSearchQuery query) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connect(query).getInputStream()))) {
            StringBuilder builder = new StringBuilder();

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
                builder.append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public JSONObject getJsonByQuery(URLSearchQuery query)  {
        return new JSONObject(getStringByQuery(query));
    }

    private URLConnection connect(URLSearchQuery query)  {
        try {

            ConfigPropertiesReader config = new ConfigPropertiesReader();
            OAuthConsumer consumer = new DefaultOAuthConsumer(config.getConsumerKey(), config.getConsumerSecret());
            consumer.setTokenWithSecret(config.getAccessKey(), config.getTokenSecret());

            URLConnection request = query.getUrl().openConnection();

            try {
                consumer.sign(request);
            } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
                e.printStackTrace();
            }

            request.connect();
            return request;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
