package ru.ifmo.android.markovnikov.rssreader;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class RSSParser extends DefaultHandler {
    private List<Record> recordList = null;
    private String title;
    private String description;
    private String link;
    private String string = "";
    private RSSParseHelper RSSParseHelper;

    public List<Record> getRecordList() {
        return recordList;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getString() {
        return string;
    }

    public RSSParseHelper getRSSParseHelper() {
        return RSSParseHelper;
    }

    class GetFeedTask extends AsyncTask<String, Void, Void> {
        boolean isSuccess = true;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URI.create(strings[0]));
                String encoding = "UTF-8";
                if (strings[0].contains("bash")) {
                    encoding = "Windows-1251";
                }
                Reader reader = new InputStreamReader(httpClient.execute(httpGet).getEntity().getContent(), encoding);
                XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                xmlReader.setContentHandler(RSSParser.this);
                xmlReader.parse(new InputSource(reader));
            } catch (Exception ignored) {
                isSuccess = false;
                return null;
            }
            isSuccess = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void arg) {
            if (isSuccess)
                RSSParseHelper.onFeedParsed(recordList);
            /*else
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();*/
        }
    }

    public RSSParser(RSSParseHelper RSSParseHelper, String url) {
        this.RSSParseHelper = RSSParseHelper;
        new GetFeedTask().execute(url);
    }

    @Override
    public void startDocument() throws SAXException {
        recordList = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        string = "";
    }

    @Override
    public void endElement(String uri, String name, String qName) throws SAXException {
        if (name.equals("item")) {
            recordList.add(new Record(title, description, link));
        } else if (name.equalsIgnoreCase("title")) {
            title = string;
        } else if (name.equalsIgnoreCase("description")) {
            description = string;
        } else if (name.equalsIgnoreCase("link")) {
            link = string;
        }
    }

    @Override
    public void characters(char[] chars, int begin, int size) throws SAXException {
        string += new String(chars, begin, size);
    }
}

