package markovnikov.android.ifmo.ru.colloquium2;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RSSActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, new String[]{"name", "p"}, new int[]{android.R.id.text1, android.R.id.text2}, 0));

    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, new String[]{"name", "p"}, new int[]{android.R.id.text1, android.R.id.text2}, 0));
        return new CursorLoader(this, Uri.parse("content://markovnikov.android.ifmo.ru.colloquium2.feeds/persons"), null, null, null, null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        ((CursorAdapter) getListAdapter()).swapCursor(cursor);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((CursorAdapter) getListAdapter()).swapCursor(null);
    }
}
