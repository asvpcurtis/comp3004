package com.comp3004.goodbyeworld.tournamentmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/** This provides a layer between the app and the database */
public class DataSource {
    private SQLiteDatabase database;
    private SQLiteHelper databaseHelper;

    /** TODO: this references the SQL columns */
    private String[] columns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_NAME,
            SQLiteHelper.COLUMN_ICON };

    public DataSource(Context context) {
        databaseHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    /** TODO: this creates a new player */
    public Org createOrg(String name, int icon) {
        ContentValues values = new ContentValues();
        // TODO: add additional columns here
        values.put(SQLiteHelper.COLUMN_NAME, name);
        values.put(SQLiteHelper.COLUMN_ICON, icon);
        long insertId = database.insert(SQLiteHelper.TABLE_NAME, null, values);

        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME,
                columns,
                SQLiteHelper.COLUMN_ID + " = " + insertId,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        Org org = cursorToOrg(cursor);
        cursor.close();

        return org;
    }

    public void deleteOrg(Org org) {
        long id = org.getId();
        database.delete(SQLiteHelper.TABLE_NAME,
                SQLiteHelper.COLUMN_ID + " = " + id,
                null);
    }

    public List<Org> getOrgs() {
        List<Org> orgList = new ArrayList<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            orgList.add(cursorToOrg(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return orgList;
    }

    private Org cursorToOrg(Cursor cursor) {
        return new Org(cursor.getLong(0),
                cursor.getString(1),
                cursor.getInt(2));
    }
}
