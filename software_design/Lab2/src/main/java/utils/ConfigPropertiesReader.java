package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by nikita on 30.09.16.
 */
public class ConfigPropertiesReader {

    public static final String PROPERTIES_FILE_NAME = "config.properties";
    private final Properties properties;

    public ConfigPropertiesReader() throws IOException {
        InputStream stream = null;
        properties = new Properties();
        try {
            stream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
            if (stream != null) {
                properties.load(stream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTIES_FILE_NAME + "' not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null)
                stream.close();
        }
    }

    public String getConsumerKey() {
        return properties.getProperty("consumer_key");
    }

    public String getConsumerSecret() {
        return properties.getProperty("consumer_secret");
    }

    public String getAccessKey() {
        return properties.getProperty("access_key");
    }

    public String getTokenSecret() {
        return properties.getProperty("token_secret");
    }
}
