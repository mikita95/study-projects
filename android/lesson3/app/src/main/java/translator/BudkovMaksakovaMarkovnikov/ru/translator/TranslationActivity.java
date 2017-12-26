/*
© Budkov Sergey, Maksakova Maria, Markovnikov Nikita 2014
All Rights Reserved
 */
package translator.BudkovMaksakovaMarkovnikov.ru.translator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class TranslationActivity extends Activity {
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        String word = getIntent().getStringExtra("Word");
        intent = new Intent(this, DrawItActivity.class);
        try {
            new TranslatorToWord(word).execute();
            new TranslatorToPic(word).execute();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.FAIL_MSG),
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public class TranslatorToWord extends AsyncTask<Void, Void, String> {
        private String word;

        TranslatorToWord(String word) {
            this.word = word;
        }

        @Override
        public String doInBackground(Void... params) {
            try {

                String requestUrl = getString(R.string.RESOURCE_URL_FOR_TEXT)
                        + getString(R.string.API_KEY) + "&text=" + URLEncoder.encode(word, "UTF-8");

                URL url = new URL(requestUrl);
                HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
                httpConnection.setConnectTimeout(8000);
                httpConnection.connect();
                int rc = httpConnection.getResponseCode();
                if (rc == 200) {
                    String line;
                    BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder strBuilder = new StringBuilder();
                    while (true) {
                        line = buffReader.readLine();
                        if (line == null)
                            break;
                        strBuilder.append(line).append('\n');
                    }
                    return parseJSON(strBuilder.toString());
                }
                httpConnection.disconnect();
                return null;
            } catch (Exception e) {

                return null;
            }
        }

        private String parseJSON(String line) {
            int begin = line.indexOf("\"text\":[\"");
            int end = line.indexOf("\"]");
            return line.substring(begin + 9, end);
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            InputActivity.ruWord = result;
        }
    }

    public class TranslatorToPic extends AsyncTask<Void, Void, ArrayList<Drawable>> {
        private String word;
        private String stringToTranslate;
        private static final int COUNT = 10;

        TranslatorToPic(String word) {
            this.word = word;
        }

        @Override
        public ArrayList<Drawable> doInBackground(Void... params) {
            stringToTranslate = word;
            stringToTranslate = URLEncoder.encode(stringToTranslate);
            ArrayList<String> pictures = new ArrayList<String>();
            URLConnection connection;

            try {
                for (int j = 0; j < 3; j++) {
                    URL pageURL = new URL(getString(R.string.RESOURCE_URL_FOR_IMAGE) + stringToTranslate + "&start=" + (4 * j));
                    connection = pageURL.openConnection();
                    connection.connect();
                    connection.setConnectTimeout(10000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
                    JSONObject request = new JSONObject(in.readLine());
                    request = request.getJSONObject("responseData");
                    JSONArray images = request.getJSONArray("results");
                    for (int i = 0; i < (j == 2 ? 2 : 4); i++) {
                        JSONObject cur = images.getJSONObject(i);
                        pictures.add(cur.getString("url"));
                    }
                }
                ArrayList<Drawable> imageList = new ArrayList<Drawable>();
                int k = 0;
                for (String picture : pictures) {
                    Drawable tmp = getImageFromURL(picture);
                    if (tmp != null) {
                        imageList.add(getImageFromURL(picture));
                        k++;
                    }
                    if (k == COUNT - 1)
                        break;
                }
                return imageList;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public void onPostExecute(ArrayList<Drawable> result) {
            super.onPostExecute(result);
            if (result != null)
                InputActivity.imagePack = result.toArray(new Drawable[COUNT]);
            else
                InputActivity.imagePack = null;
            startActivity(intent);
            finish();
        }

        public Drawable getImageFromURL(String imageURL) throws IOException {
            Drawable drawable;
            try {
                drawable = Drawable.createFromStream((InputStream) new URL(imageURL).getContent(), "Picture");
            } catch (FileNotFoundException e) {
                return null;
            }
            return drawable;
        }
    }
}
