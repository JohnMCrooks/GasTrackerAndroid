package com.crooks.androidcrudapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by johncrooks on 10/12/16.
 */

public class OnClickListenerAddFillUp implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        final Context context = v.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.gas_input_form,null, false);

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
                            boolean createSuccessful = new TableControllerFillUp(context).create(newTank);

                            if(createSuccessful){
                                Toast.makeText(context, "New data was saved.", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context, "Unable to save new data.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        dialog.cancel();
                    }

                }).show();

    }
}
