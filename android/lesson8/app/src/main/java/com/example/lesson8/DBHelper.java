package com.example.lesson8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String dbName, Object o, int dbVersion) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) o, dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(WeatherDB.DB_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherDB.DB_TABLE);
        onCreate(sqLiteDatabase);
    }
}
