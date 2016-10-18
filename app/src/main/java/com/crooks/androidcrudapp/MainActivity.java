package com.crooks.androidcrudapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddFillUp = (Button) findViewById(R.id.buttonAddFillUp);
        Button buttonCharts = (Button) findViewById(R.id.buttonCharts);
        Button buttonStartFresh = (Button) findViewById(R.id.buttonStartFresh);


        buttonAddFillUp.setOnClickListener(this);
        buttonCharts.setOnClickListener(this);
        buttonStartFresh.setOnClickListener(this);

        countRecords();
        readRecords();

    }

    public void countRecords(){
        int recordCount = new TableControllerFillUp(this).recordCount();
        if (recordCount > 0){
            TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
            textViewRecordCount.setText(recordCount + " records found.");


        } else {
            TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
            textViewRecordCount.setText("");
        }
    }

    public void readRecords(){
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<FillUp> fillUpList = new TableControllerFillUp(this).returnAllRecords();
        if (fillUpList.size()>0){
            for(FillUp fill : fillUpList){
                int id = fill.id;
                String date = fill.date.substring(0,11);
                double gallonsPumped = fill.gallonsPumped;
                double costPerGallon = fill.costPerGallon;
                double totalCost = fill.totalCost;

                String textViewContents =  date + " \nGallons: " + gallonsPumped + " Cost: " + costPerGallon + " $/g  Total: " + totalCost;

                TextView textViewFillItem = new TextView(this);
                textViewFillItem.setPadding(0,10,0,10);
                textViewFillItem.setText(textViewContents);
                textViewFillItem.setTag(Integer.toString(id));
                textViewFillItem.setOnLongClickListener(this);

                TextView textSumTotal = (TextView) findViewById(R.id.textSumTotal);
                textSumTotal.setText("$" + new TableControllerFillUp(this).sumTotal());

                TextView textAverageCost = (TextView) findViewById(R.id.textAverageCost);
                textAverageCost.setText("$" + new TableControllerFillUp(this).averageTotalCost());

                TextView textAvgCostPerGallon = (TextView) findViewById(R.id.costPerGallon);
                textAvgCostPerGallon.setText("$" + new TableControllerFillUp(this).averageCostPerGallon());

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
        Intent intent = null;
        switch(v.getId()){
            case R.id.buttonStartFresh:
                deleteAllDialogue(v);
                break;

            case R.id.buttonCharts:
                if (new TableControllerFillUp(this).returnAllRecords().size() >= 1   ) {
                    intent = new Intent(MainActivity.this, ChartActivity.class);
                }else {
                    Toast.makeText(this, "No Data, Therefore No Chart", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonAddFillUp:
                addRecordDialogue(v);
                break;

            default:
                break;
        }

        if (intent !=null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onLongClick(final View v) {
        final Context context = v.getContext();
        final String id = v.getTag().toString();
        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context)
                .setTitle("Fill Up Number " + id)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            editRecord(Integer.parseInt(id), context);
                            countRecords();
                            readRecords();
                        } else if (item ==1){
                            boolean deleteSuccessful = new TableControllerFillUp(context).deleteSingle(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Gas record was deleted.", Toast.LENGTH_SHORT).show();
                                updateStats(v);

                            }else{
                                Toast.makeText(context, "Unable to delete gas record.", Toast.LENGTH_SHORT).show();
                            }

                            countRecords();
                            readRecords();
                        }
                        dialog.dismiss();
                    }
                }).show();

        return false;
    }

    public void editRecord(final int id, final Context context){
        final TableControllerFillUp tableControllerFillUp = new TableControllerFillUp(context);
        final FillUp fillUp = tableControllerFillUp.readSingle(id);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.gas_input_form, null, false);
        final EditText editCostPerGallon = (EditText) formElementsView.findViewById(R.id.editCostPerGallon);
        final EditText editGallonsPumped = (EditText) formElementsView.findViewById(R.id.editGallonsPumped);
        final EditText editDate = (EditText) formElementsView.findViewById(R.id.editDate);

        editDate.setText(String.valueOf(fillUp.date));
        editCostPerGallon.setText(String.valueOf(fillUp.costPerGallon));
        editGallonsPumped.setText(String.valueOf(fillUp.gallonsPumped));

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String date = editDate.getText().toString();
                                double costPerGallon = Double.valueOf(editCostPerGallon.getText().toString());
                                double gallonsPumped = Double.valueOf(editGallonsPumped.getText().toString());
                                double totalCost = costPerGallon * gallonsPumped;

                                FillUp fillUp1 = new FillUp(date, costPerGallon,gallonsPumped,totalCost);
                                fillUp1.setId(fillUp.getId());

                                boolean updateSuccessful = tableControllerFillUp.updateRecord(fillUp1);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Gas record was updated.", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(context, "Unable to update gas record.", Toast.LENGTH_SHORT).show();
                                }

                                countRecords();
                                readRecords();


                                dialog.cancel();
                            }

                        }).show();


    }

    public void addRecordDialogue(View v){
        final Context context = v.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formElementsView = inflater.inflate(R.layout.gas_input_form,null, false);

        Calendar cal = Calendar.getInstance();
        final Date date = cal.getTime();
        final EditText editDate = (EditText) formElementsView.findViewById(R.id.editDate);
        editDate.setText((CharSequence) date.toString());


        final EditText editCostPerGallon = (EditText) formElementsView.findViewById(R.id.editCostPerGallon);
        final EditText editGallonsPumped = (EditText) formElementsView.findViewById(R.id.editGallonsPumped);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Add Fill Up")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( editCostPerGallon !=null && editGallonsPumped !=null){
                            double costPerGallon = Double.valueOf(editCostPerGallon.getText().toString());
                            double gallonsPumped = Double.valueOf(editGallonsPumped.getText().toString());
                            double totalCost = costPerGallon * gallonsPumped;

                            FillUp newTank = new FillUp(date.toString(), costPerGallon,gallonsPumped,totalCost);
                            boolean createSuccessful = new TableControllerFillUp(context).createRecord(newTank);

                            if(createSuccessful){
                                Toast.makeText(context, "New data was saved.", Toast.LENGTH_SHORT).show();
                                countRecords();
                                readRecords();

                            }else{
                                Toast.makeText(context, "Unable to save new data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.cancel();
                    }
                }).show();
    }

    public void deleteAllDialogue(final View v){
        final Context context = v.getContext();
        CharSequence[] items = { "Yes", "No" };
        new AlertDialog.Builder(context)
                .setTitle("Delete Everything?")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            boolean deleteSuccessful = new TableControllerFillUp(context).deleteAll();
                            if (deleteSuccessful){
                                Toast.makeText(context, "Gas records were deleted.", Toast.LENGTH_SHORT).show();
                                countRecords();
                                readRecords();
                                updateStats(v);
                            }else{
                                Toast.makeText(context, "Something went wrong, unable to delete the records.", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    public void updateStats(View v){
        //grabbing data for display at bottom of screen
        Context context = v.getContext();

        float sumTotal = new TableControllerFillUp(context).sumTotal();
        float avgTotalCost = new TableControllerFillUp(context).averageTotalCost();
        float avgCostPerGallon = new TableControllerFillUp(this).averageCostPerGallon();

        TextView textSumTotal = (TextView) findViewById(R.id.textSumTotal);
        textSumTotal.setText("$" + Math.round(sumTotal));

        TextView textAverageCost = (TextView) findViewById(R.id.textAverageCost);
        textAverageCost.setText("$" + Math.round(avgTotalCost));

        TextView textAvgCostPerGallon = (TextView) findViewById(R.id.costPerGallon);
        textAvgCostPerGallon.setText("$" + Math.round(avgCostPerGallon));
    }
}
