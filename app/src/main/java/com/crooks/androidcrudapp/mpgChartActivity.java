package com.crooks.androidcrudapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johncrooks on 10/17/16.
 */

public class mpgChartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Button buttonBack = (Button) findViewById(R.id.buttonReturnToMain);
        buttonBack.setOnClickListener(this);

        createTotalCostTable();
    }

    public void createTotalCostTable(){
        //Custom color scheme for the charts
        int[] CROOKS_CUSTOM = {
                Color.rgb(97, 181, 204), Color.rgb(80, 149, 168), Color.rgb(65, 124, 140),
                Color.rgb(46, 90, 102), Color.rgb(35, 69, 79), Color.rgb(46, 90, 102),
                Color.rgb(65, 124, 140), Color.rgb(80, 149, 168)
        };

        BarChart chart = (BarChart) findViewById(R.id.chart);

        /*
            This swipe code was found on StackOverflow (more details in the OnSwipeTouchListener Class) and re-purposed
            for my particular use case. ITS SO COOL!
         */
        chart.setOnTouchListener(new OnSwipeTouchListener(mpgChartActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(mpgChartActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(mpgChartActivity.this, "right", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mpgChartActivity.this, ChartActivity.class);
                startActivity(intent);
            }
            public void onSwipeLeft() {
                Toast.makeText(mpgChartActivity.this, "left", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mpgChartActivity.this, MainActivity.class);
                startActivity(intent);

            }
            public void onSwipeBottom() {
                Toast.makeText(mpgChartActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });

        //Formatting the chart
        chart.setDescription("Miles Per Gallon Through Time.");

        chart.setNoDataTextDescription("No data to analyze yet!");
        chart.setNoDataText("No data to analyze yet");

        /*
            Formatting the charts X and Y Axis
        */
        XAxis xaxis = chart.getXAxis();

        xaxis.setAxisMinValue(new TableControllerFillUp(this).returnMinID() - 1);
        xaxis.setAxisMaxValue(new TableControllerFillUp(this).returnMaxID() + 1);
        xaxis.setDrawLabels(false);
        xaxis.setDrawGridLines(false);

        YAxis yAxisLeft =  chart.getAxisLeft();
        yAxisLeft.setAxisMinValue(0);
        yAxisLeft.setAxisMaxValue((float) (new TableControllerFillUp(this).returnMaxSingleCost() * 1.35));

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setDrawGridLines(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawLabels(false);

        /*
            Formatting data for chart usage
        */


        ArrayList<BarEntry> entries = new ArrayList<>();

        //TODO Change this SQL call once the DB is redone and working properly
        List<FillUp> fillUpList = new TableControllerFillUp(this).returnAllRecords();

        for(FillUp fill: fillUpList){
            entries.add(new BarEntry(fill.getId(), (float) fill.getTotalCost()));
            System.out.println("ID: " + fill.getId() + "  Total: " + fill.getTotalCost());
        }

        BarDataSet dataset = new BarDataSet(entries, "Label");
        dataset.setColors(CROOKS_CUSTOM);

        BarData lineData = new BarData(dataset);
        lineData.setValueTextSize(12);
        chart.setData(lineData);

        chart.invalidate();

    }

    @Override
    public void onClick(View v) {
        Intent mainActivityIntent = new Intent(mpgChartActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
