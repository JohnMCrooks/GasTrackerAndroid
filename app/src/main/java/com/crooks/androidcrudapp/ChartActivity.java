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
        //TODO: This list is super fickle - Must maintain order for it to display properly.
        // Find way to sort without streams before adding all the entries
            entries.add(new Entry(1, 2));
            entries.add(new Entry(2, 4));
            entries.add(new Entry(3, 9));
            entries.add(new Entry(4, 12));
            entries.add(new Entry(5, 4));

//        Confirming the entries are being added the way they should be
        for (Entry entry : entries){
            System.out.println("X: " + entry.getX() + " Y: " + entry.getY());
        }

        List<FillUp> fillUpList = new TableControllerFillUp(this).read();

//        for(FillUp fill: fillUpList){
//            entries.add(new Entry(fill.getId(), (float) fill.getTotalCost()));
//            System.out.println("ID: " + fill.getId() + "  Total: " + fill.getTotalCost());
//        }

        LineDataSet dataset = new LineDataSet(entries, "Label");
        LineData lineData = new LineData(dataset);
        chart.setData(lineData);
        chart.invalidate();
    }
}
