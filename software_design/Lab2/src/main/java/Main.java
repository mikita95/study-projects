import api.TwitterHashTagDatesReader;
import api.TwitterHashTagSearcher;
import counter.FrequencyCounter;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by nikita on 30.09.16.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        FrequencyCounter frequencyCounter = new FrequencyCounter(new TwitterHashTagDatesReader(new TwitterHashTagSearcher()));
        System.out.println(Arrays.toString(frequencyCounter.count("trump", 2)));
    }
}
