package com.crooks.androidcrudapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johncrooks on 10/12/16.
 */

public class TableControllerFillUp extends DbHandler{
    public TableControllerFillUp(Context context){
        super(context);
    }

    public boolean create(FillUp fillUp){
        ContentValues values = new ContentValues();
        values.put("date", String.valueOf(fillUp.date));
        values.put("costpergallon", fillUp.costPerGallon);
        values.put("gallonspumped", fillUp.gallonsPumped);
        values.put("totalcost", fillUp.totalCost);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccesful = db.insert("fillups", null, values)>0;
        db.close();

        return createSuccesful;
    }

    public int count(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM fillups";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;
    }

    public List<FillUp> read() {

        List<FillUp> recordsList = new ArrayList<FillUp>();

        String sql = "SELECT * FROM fillups ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                double gallonsPumped = Double.parseDouble(cursor.getString(cursor.getColumnIndex("gallonspumped")));
                double costPerGallon = Double.parseDouble(cursor.getString(cursor.getColumnIndex("costpergallon")));
                double totalCost = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totalcost")));

                FillUp fillup = new FillUp();
                fillup.id = id;
                fillup.date = date;
                fillup.gallonsPumped = gallonsPumped;
                fillup.costPerGallon = costPerGallon;
                fillup.totalCost = totalCost;

                recordsList.add(fillup);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public FillUp readSingle(int id){
        FillUp fillUp = null;
        String sql = "SELECT * FROM fillups WHERE id = " + id;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            int oldId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String oldDate = cursor.getString(cursor.getColumnIndex("date"));
            double costPerGallon = Double.parseDouble(cursor.getString(cursor.getColumnIndex("costpergallon")));
            double gallonsPumped = Double.parseDouble(cursor.getString(cursor.getColumnIndex("gallonspumped")));
            double totalCost = Double.parseDouble(cursor.getString(cursor.getColumnIndex("totalcost")));

            fillUp = new FillUp(oldDate,costPerGallon,gallonsPumped,totalCost);
            fillUp.id = oldId;
        }

        cursor.close();
        db.close();

        return fillUp;
    }

}
