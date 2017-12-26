package counter;

import api.TwitterHashTagDatesReader;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by nikita on 30.09.16.
 */
public class FrequencyCounter {

    private final TwitterHashTagDatesReader client;

    public FrequencyCounter(TwitterHashTagDatesReader client) {
        this.client = client;
    }

    public int[] countUntilDate(String hashTag, Date until) {
        ArrayList<Date> dates = client.getDates(hashTag, until);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(until);
        calendar.add(Calendar.HOUR, 1);
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);
        int i = 0;
        for (int k = dates.size() - 1; k >= 0; k--) {
            if (dates.get(k).before(calendar.getTime()))
                result.set(i, result.get(i) + 1);
            else {
                i++;
                result.add(1);
                calendar.add(Calendar.HOUR, 1);
            }
        }
        Collections.reverse(result);
        int[] answer = new int[result.size()];
        for (i = 0; i < result.size(); i++)
            answer[i] = result.get(i);
        return answer;

    }

    public int[] count(String hashTag, int n) {
        assert n >= 1 && n <= 24;
        Date until = DateUtils.getInGMTWithOffset(n, TwitterHashTagDatesReader.DATE_FORMAT);
        return countUntilDate(hashTag, until);
    }
}
