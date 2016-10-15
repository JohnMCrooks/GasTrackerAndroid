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

    public boolean createRecord(FillUp fillUp){
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

    public int recordCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM fillups";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;
    }

    public float sumTotal() {
        SQLiteDatabase db = this.getWritableDatabase();
        float total = 0;
        Cursor cursor = db.rawQuery("Select sum(totalcost) from fillups", null);
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(0);
        }
        db.close();

        return total;
    }

    public float averageTotalCost(){
        SQLiteDatabase db = this.getWritableDatabase();
        float average = 0;
        Cursor cursor =  db.rawQuery("Select avg(totalcost) from fillups", null);
        if(cursor.moveToFirst()){
            average = cursor.getFloat(0);
        }
        db.close();

        return average;
    }

    public float averageCostPerGallon(){
        SQLiteDatabase db = this.getWritableDatabase();
        float average = 0;
        Cursor cursor =  db.rawQuery("Select avg(costpergallon) from fillups", null);
        if(cursor.moveToFirst()){
            average = cursor.getFloat(0);
        }
        db.close();

        return average;

    }

    public List<FillUp> returnAllRecords() {

        List<FillUp> recordsList = new ArrayList<FillUp>();

        String sql = "SELECT * FROM fillups ORDER BY id";

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

    public boolean updateRecord(FillUp updatedFill){
        ContentValues values = new ContentValues();
        values.put("gallonspumped", updatedFill.gallonsPumped);
        values.put("costpergallon", updatedFill.costPerGallon);
        values.put("totalcost", updatedFill.totalCost);

        String where = "id = ?";
        String[] whereArgs = {Integer.toString(updatedFill.getId())};
        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("fillups", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;
    }

    public boolean deleteSingle(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("fillups", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }

    public boolean deleteAll(){
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM fillups";

        deleteSuccessful =  db.rawQuery(sql,null).getCount() < 1;
        db.close();

        return deleteSuccessful;

    }

    public float returnMinID(){
        SQLiteDatabase db = this.getWritableDatabase();
        float minID = 0;
        Cursor cursor =  db.rawQuery("SELECT MIN(id) from fillups", null);
        if(cursor.moveToFirst()){
            minID = cursor.getFloat(0);
        }
        db.close();

        return minID;

    }

    public float returnMaxID(){
        SQLiteDatabase db = this.getWritableDatabase();
        float maxID = 0;
        Cursor cursor =  db.rawQuery("SELECT max(id) from fillups", null);
        if(cursor.moveToFirst()){
            maxID = cursor.getFloat(0);
        }
        db.close();

        return maxID;

    }
}
