package ru.ifmo.android.markovnikov.rssreader;

import java.util.List;

public abstract class RSSParseHelper {
    public void onFeedParsed(List<Record> recordList) {
        for (int i = 0; i < recordList.size(); i++) {
            changeContent(recordList.get(i));
        }
        broadcast();
    }

    abstract void changeContent(Record record);

    abstract void broadcast();
}
