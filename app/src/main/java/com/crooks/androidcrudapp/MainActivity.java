package com.crooks.androidcrudapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddFillUp = (Button) findViewById(R.id.buttonAddFillUp);
        Button buttonCharts = (Button) findViewById(R.id.buttonCharts);

        buttonAddFillUp.setOnClickListener(new OnClickListenerAddFillUp());
        buttonCharts.setOnClickListener(this);

        countRecords();
        readRecords();

    }

    public void countRecords(){
        int recordCount = new TableControllerFillUp(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " records found.");
    }

    public void readRecords(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<FillUp> fillUpList = new TableControllerFillUp(this).read();
        if (fillUpList.size()>0){
            for(FillUp fill : fillUpList){
                int id = fill.id;
                String date = fill.date.substring(0,11);
                double gallonsPumped = fill.gallonsPumped;
                double costPerGallon = fill.costPerGallon;
                double totalCost = fill.totalCost;

                String textViewContents =  date + " Gallons: " + gallonsPumped + " Cost: " + costPerGallon + " $/g  Total: " + totalCost;

                TextView textViewFillItem = new TextView(this);
                textViewFillItem.setPadding(0,10,0,10);
                textViewFillItem.setText(textViewContents);
                textViewFillItem.setTag(Integer.toString(id));
                textViewFillItem.setOnLongClickListener(new OnLongClickListenerFillUp());

                linearLayoutRecords.addView(textViewFillItem);
            }
        } else {
            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }
    }

    @Override
    public void onClick(View v) {
        Intent chartIntent = new Intent(MainActivity.this, ChartActivity.class);
        startActivity(chartIntent);

    }
}
