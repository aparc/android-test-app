package com.example.aparc.testapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "locationDb";
    public static final String TABLE_LOCATIONS = "locations";

    public static final String KEY_ID = "_id";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LON = "longitude";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_LOCATIONS + "(" + KEY_ID + " integer primary key," +
        KEY_LAT + " text," + KEY_LON + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_LOCATIONS);

        onCreate(sqLiteDatabase);
    }
}
