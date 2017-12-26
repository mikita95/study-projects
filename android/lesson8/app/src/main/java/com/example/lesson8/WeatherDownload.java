package com.example.lesson8;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherDownload extends IntentService {
    public static final String DEFAULT_KEY = "com.example.lesson8.WeatherDownload";
   private static final String KEY = "tjdqdcj7hbk9s8g6urxtpv4b";
   //private static final String KEY = "68b6c65fe1842c5ccb71b40db34d0";
    private static final String BASE_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx";

    private Forecast result;

    public WeatherDownload() {
        super("WeatherDownload");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String requestCity = intent.getStringExtra("city");
        String newRequestCity = requestCity.replace(" ", "%20");
        String link = BASE_URL + "?key=" + KEY + "&q=" + newRequestCity + "&num_of_days=3&format=json";
        JSONObject json = null;
        String currentBufferString;
        result = new Forecast(requestCity, "", "");
        String tmp = WeatherActivity.currentQueryCity;
        if (!requestCity.equals(tmp))
            return;
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            StringBuilder builder = new StringBuilder();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((currentBufferString = buffer.readLine()) != null) {
                builder.append(currentBufferString);
            }
            json = new JSONObject(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject root = json.getJSONObject("data");
            JSONArray jsonArray = root.getJSONArray("current_condition");
            JSONObject conditions = jsonArray.getJSONObject(0);
            String currentTemprature = conditions.getString("temp_C");
            JSONArray weatherArray = conditions.getJSONArray("weatherDesc");
            JSONObject currentWeather = weatherArray.getJSONObject(0);
            String weather = currentWeather.getString("value");

            JSONArray futureWeather = root.getJSONArray("weather");
            for (int i = 0; i < futureWeather.length(); i++) {
                JSONObject current = futureWeather.getJSONObject(i);
                DayForecast nextDay = new DayForecast();
                String nextDayDate = current.getString("date");
                Date date = new SimpleDateFormat("yyyy-M-d").parse(nextDayDate);
                String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
                nextDay.setDate(dayOfWeek);
                nextDay.setTemp(current.getString("tempMinC") + "/" + current.getString("tempMaxC"));
                JSONArray curArray = current.getJSONArray("weatherDesc");
                nextDay.setWeather(curArray.getJSONObject(0).getString("value"));
                if (nextDay.getTemp().equals(""))
                    continue;
                result.addForecast(nextDay);
            }

            result.setCity(requestCity);
            result.setWeather(weather);
            result.setTemp(currentTemprature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        update();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis() + 10000, 10000, pi);

    }

    private void update() {
        Intent intent = new Intent();
        intent.setAction(DEFAULT_KEY);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("forecast", result);
        sendBroadcast(intent);

    }
}
