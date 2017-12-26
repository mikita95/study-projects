package markovnikov.android.ifmo.ru.colloquium2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class RSSContentProvider extends ContentProvider {

    private DataBase database;

    private static final int newsCount;
    private static final int channelCount;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        newsCount = 20;
        channelCount = 10;
        uriMatcher.addURI("markovnikov.android.ifmo.ru.colloquium2.feeds", "persons", channelCount);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == channelCount) {
            if (database.sqLiteDatabase.delete("person", "_id=" + (Long.parseLong(selection)), null) == 1) {
                getContext().getContentResolver().notifyChange(uri, null);
                return 1;
            } else {
                getContext().getContentResolver().notifyChange(uri, null);
                return 0;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long number = -1;
       if (uriMatcher.match(uri) == channelCount) {
            number = database.sqLiteDatabase.insert("person", null, contentValues);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri.toString() + "/" + number);
    }

    @Override
    public boolean onCreate() {
        database = DataBase.getDataBase(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        if (uriMatcher.match(uri) == channelCount) {
            cursor = database.sqLiteDatabase.query("person", new String[]{DataBaseHelper.COLUMN_ID, DataBaseHelper.COLUMN_POINT, DataBaseHelper.COLUMN_NAME}, null, null, null, null, null);
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) != channelCount)
            throw new UnsupportedOperationException();
        if (database.sqLiteDatabase.update("person", contentValues, "_id=" + contentValues.getAsLong("_id"), null) == 1) {
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        } else {
            getContext().getContentResolver().notifyChange(uri, null);
            return 0;
        }

    }
}
