package ru.ifmo.android.markovnikov.rssreader;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class RSSIntentService extends IntentService {

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {
            String actionUpdate = "ru.ifmo.android.markovnikov.rssreader.action.updateChannel";
            String actionUpdateAll = "ru.ifmo.android.markovnikov.rssreader.action.updateAllChannels";
            if (actionUpdate.equals(intent.getAction())) {
                if (DataBase.getDataBase(this).getUrlByChannelId(intent.getLongExtra("ru.ifmo.android.markovnikov.rssreader.extra.channel_id", -1)) != null) {
                    RSSParseHelper rssParseHelper = new RSSParseHelper() {
                        @Override
                        void changeContent(Record feedItem) {
                            inChangeContent(feedItem, intent.getLongExtra("ru.ifmo.android.markovnikov.rssreader.extra.channel_id", -1));
                        }

                        @Override
                        void broadcast() {
                            inBroadCast(intent.getLongExtra("ru.ifmo.android.markovnikov.rssreader.extra.channel_id", -1));
                        }
                    };
                    new RSSParser(rssParseHelper, DataBase.getDataBase(this).getUrlByChannelId(intent.getLongExtra("ru.ifmo.android.markovnikov.rssreader.extra.channel_id", -1)));
                }
            } else if (actionUpdateAll.equals(intent.getAction())) {
                final Cursor cursor = DataBase.getDataBase(this).sqLiteDatabase.query("channel", new String[]{"_id", "name", "url"}, null, null, null, null, null);
                if (!cursor.moveToFirst()) return;
                do {
                    handleActionUpdateChannel(cursor.getLong(cursor.getColumnIndex("_id")));
                    if (DataBase.getDataBase(this).getUrlByChannelId(cursor.getLong(cursor.getColumnIndex("_id"))) != null) {
                        RSSParseHelper rssParseHelper = new RSSParseHelper() {
                            @Override
                            void changeContent(Record feedItem) {
                                inChangeContent(feedItem, cursor.getLong(cursor.getColumnIndex("_id")));
                            }

                            @Override
                            void broadcast() {
                                inBroadCast(cursor.getLong(cursor.getColumnIndex("_id")));
                            }
                        };
                        new RSSParser(rssParseHelper, DataBase.getDataBase(this).getUrlByChannelId(cursor.getLong(cursor.getColumnIndex("_id"))));
                    }
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
    }

    public static void start(Context context, long rssNumber) {
        Intent intent = new Intent(context, RSSIntentService.class);
        intent.setAction("ru.ifmo.android.markovnikov.rssreader.action.updateChannel");
        intent.putExtra("ru.ifmo.android.markovnikov.rssreader.extra.channel_id", rssNumber);
        context.startService(intent);
    }

    public RSSIntentService() {
        super("RSSIntentService");
    }

    private void handleActionUpdateChannel(final long rssNumber) {
        if (DataBase.getDataBase(this).getUrlByChannelId(rssNumber) != null) {
            RSSParseHelper rssParseHelper = new RSSParseHelper() {
                @Override
                void changeContent(Record feedItem) {
                    inChangeContent(feedItem, rssNumber);
                }

                @Override
                void broadcast() {
                    inBroadCast(rssNumber);
                }
            };
            new RSSParser(rssParseHelper, DataBase.getDataBase(this).getUrlByChannelId(rssNumber));
        }
    }

    private void inChangeContent(Record record, final long rssNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", record.getDescription());
        contentValues.put("title", record.getTitle());
        contentValues.put("url", record.getLink());
        contentValues.put("channel_id", rssNumber);
        contentValues.put("time", System.currentTimeMillis() / 1000);
        getContentResolver().insert(Uri.parse(Uri.parse("content://ru.ifmo.android.markovnikov.rssreader.feeds/news").toString() + "/" + rssNumber), contentValues);
    }

    private void inBroadCast(final long rssNumber) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("channel updated");
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra("ru.ifmo.android.markovnikov.rssreader.extra.channel_id", rssNumber);
        sendBroadcast(broadcastIntent);
        getContentResolver().notifyChange(Uri.parse(Uri.parse("content://ru.ifmo.android.markovnikov.rssreader.feeds/news").toString() + "/" + rssNumber), null);

    }
}
