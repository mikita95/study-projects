package ru.ifmo.android.markovnikov.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;


public class WebActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        WebView webView1 = (WebView) findViewById(R.id.web_view);
        Intent intent = getIntent();
        if (intent.getStringExtra("rss_title") == null || intent.getStringExtra("rss_description") == null) {
            webView1.loadUrl(intent.getStringExtra("rss_link"));
        } else {
            webView1.loadData(intent.getStringExtra("rss_title") + "<br>" + intent.getStringExtra("rss_description"), "text/html; charset=UTF-8", null);
        }
    }
}
