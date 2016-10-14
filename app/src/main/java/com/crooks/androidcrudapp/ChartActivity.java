package com.crooks.androidcrudapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Button buttonBack = (Button) findViewById(R.id.buttonReturnToMain);
        buttonBack.setOnClickListener(this);



        createTotalCostTable();
    }

    public void createTotalCostTable(){
        BarChart chart = (BarChart) findViewById(R.id.chart);
        chart.setDescription("Testing this Description, Yo");
        chart.setNoDataTextDescription("No data to analyze yet!");
        chart.setNoDataText("No data to analyze yet");


        //This swipe code was found on StackOverflow (more details in the OnSwipeTouchListener Class) and re-purposed
        // for my particular use case. ITS SO COOL!
        chart.setOnTouchListener(new OnSwipeTouchListener(ChartActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(ChartActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(ChartActivity.this, "right", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChartActivity.this, MainActivity.class);
                startActivity(intent);
            }
            public void onSwipeLeft() {
                Toast.makeText(ChartActivity.this, "left", Toast.LENGTH_SHORT).show();

            }
            public void onSwipeBottom() {
                Toast.makeText(ChartActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<FillUp> fillUpList = new TableControllerFillUp(this).read();

        for(FillUp fill: fillUpList){
            entries.add(new BarEntry(fill.getId(), (float) fill.getTotalCost()));
            System.out.println("ID: " + fill.getId() + "  Total: " + fill.getTotalCost());
        }

        BarDataSet dataset = new BarDataSet(entries, "Label");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData lineData = new BarData(dataset);
        lineData.setValueTextSize(12);
        chart.setData(lineData);

        chart.invalidate();
    }

    @Override
    public void onClick(View v) {
        Intent mainActivityIntent = new Intent(ChartActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }
}
