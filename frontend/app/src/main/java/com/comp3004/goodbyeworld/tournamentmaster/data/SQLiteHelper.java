package com.comp3004.goodbyeworld.tournamentmaster.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** This is responsible for creating and updating the database. */
public class SQLiteHelper extends SQLiteOpenHelper {

    /** TODO: the database file name and version */
    private static final String DATABASE_NAME = "tm.db";
    private static final int DATABASE_VERSION = 1;

    /** TODO: the table name */
    public static final String TABLE_NAME = "organization";

    /** TODO: the table column names */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ICON = "icon";

    /** TODO: the SQL that creates the table (types: text, numeric, integer, real, blob) */
    private static final String DATABASE_CREATE = "create table " +
            TABLE_NAME +
            "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_ICON + " integer);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
