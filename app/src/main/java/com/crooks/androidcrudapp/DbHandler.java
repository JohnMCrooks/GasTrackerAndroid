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
        String sql = "CREATE TABLE fillups " + " (id INTEGER PRIMARY KEY AUTOINCREMENT, softid INTEGER, date CHAR, costpergallon FLOAT, gallonspumped FLOAT, totalcost FLOAT, odometer INTEGER, mpg FLOAT)";
        // TODO 10/17/16 - don't forget to finish adding the cars table (double check syntax) and changing the fillups table to remove any redundant entries
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: 10/17/16 - Update this appropriately after researching the proper syntax
        String sql = "DROP TABLE IF EXISTS fillups";
        db.execSQL(sql);

        onCreate(db);
    }
}
