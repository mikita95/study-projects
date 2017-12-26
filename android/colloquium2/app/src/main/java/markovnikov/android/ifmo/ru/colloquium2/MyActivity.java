package markovnikov.android.ifmo.ru.colloquium2;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    boolean was = false;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        was = false;
        getLoaderManager().initLoader(0, null, this);
        setListAdapter(new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, new String[]{"name"}, new int[]{android.R.id.text1}, 0));
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                ContentValues contentValues = new ContentValues();
                Cursor c = getContentResolver().query(Uri.parse("content://markovnikov.android.ifmo.ru.colloquium2.feeds/persons"), new String[]{"_id", "name", "p"}, null, null, null);
                c.moveToNext();
                contentValues.put("p", c.getString(c.getColumnIndex("p")) + 1);
                getContentResolver().update(Uri.parse("content://markovnikov.android.ifmo.ru.colloquium2.feeds/persons"), contentValues, null, null);
                c.close();
            }
        });
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                getContentResolver().delete(Uri.parse("content://markovnikov.android.ifmo.ru.colloquium2.feeds/persons"), "" + id, null);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.persons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!was && item.getItemId() == R.id.addDialogItem) {
            RSSDialog RSSDialog = new RSSDialog();
            getFragmentManager().beginTransaction().add(RSSDialog, "").commit();
        }
        if (item.getItemId() == R.id.start) {
            was = true;
        }
        if (was && item.getItemId() == R.id.finish) {
            Intent intent = new Intent(MyActivity.this, RSSActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, Uri.parse("content://markovnikov.android.ifmo.ru.colloquium2.feeds/persons"), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ((CursorAdapter) getListAdapter()).swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ((CursorAdapter) getListAdapter()).swapCursor(null);
    }
}
