package ru.ifmo.android.markovnikov.rssreader;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class RSSActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getLongExtra("extra_channel_id", -1) == -1)
            finish();
        getLoaderManager().initLoader(0, null, this);
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"title", "url"}, new int[]{android.R.id.text1, android.R.id.text2}, 0));
        RSSIntentService.start(this, getIntent().getLongExtra("extra_channel_id", -1));
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Cursor cursor = ((Cursor) listView.getAdapter().getItem(position));
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("rss_link", cursor.getString(cursor.getColumnIndex("url")));
        intent.putExtra("rss_description", cursor.getString(cursor.getColumnIndex("description")));
        intent.putExtra("rss_title", cursor.getString(cursor.getColumnIndex("title")));
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"title"}, new int[]{android.R.id.text1}, 0));
        return new CursorLoader(this, Uri.parse(Uri.parse("content://ru.ifmo.android.markovnikov.rssreader.feeds/news").toString() + "/" + getIntent().getLongExtra("extra_channel_id", -1)), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (!(((CursorAdapter) getListAdapter()).getCursor() == null || ((CursorAdapter) getListAdapter()).getCursor().getCount() == 0))
            Toast.makeText(this, getString(R.string.updateMsg), Toast.LENGTH_SHORT).show();
        ((CursorAdapter) getListAdapter()).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((CursorAdapter) getListAdapter()).swapCursor(null);
    }
}
