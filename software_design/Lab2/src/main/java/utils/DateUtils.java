package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by nikita on 01.10.16.
 */
public class DateUtils {

    public static Date getDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toGMT(Date date) {
        TimeZone timeZone = TimeZone.getDefault();
        Date result = new Date(date.getTime() - timeZone.getRawOffset());
        if (timeZone.inDaylightTime(result)) {
            Date dstDate = new Date(result.getTime() - timeZone.getDSTSavings());
            if (timeZone.inDaylightTime(dstDate)) {
                result = dstDate;
            }
        }
        return result;
    }

    public static Date getInGMTWithOffset(int n, String format) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        calendar.add(Calendar.HOUR_OF_DAY, -n);
        try {
            return toGMT(simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
