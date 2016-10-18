package com.crooks.androidcrudapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by johncrooks on 10/12/16.
 */

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "GasHistory";

    public DbHandler(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE fillups " + " (id INTEGER PRIMARY KEY AUTOINCREMENT, softID INT, date CHAR, costpergallon FLOAT, gallonspumped FLOAT, totalcost FLOAT) ";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS fillups";
        db.execSQL(sql);

        onCreate(db);
    }
}
