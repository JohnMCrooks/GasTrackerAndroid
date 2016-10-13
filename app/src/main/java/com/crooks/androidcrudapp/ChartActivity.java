package com.crooks.androidcrudapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        createTotalCostTable();

    }

    public void createTotalCostTable(){
        LineChart chart = (LineChart) findViewById(R.id.chart);
        chart.setDescription("Testing this Shit");

        ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(4, 0));
            entries.add(new Entry(8, 1));
            entries.add(new Entry(6, 2));
            entries.add(new Entry(2, 3));
            entries.add(new Entry(18, 9));

        //Confirming the entries are being added the way I they should be
        for (Entry entry : entries){
            System.out.println("X: " + entry.getX() + " Y: " + entry.getY());
        }

        LineDataSet dataset = new LineDataSet(entries, "Y-Data");

        LineData lineData = new LineData(dataset);
        chart.setData(lineData);
        chart.invalidate();
    }

}
