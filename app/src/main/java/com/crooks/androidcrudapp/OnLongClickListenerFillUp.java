package com.crooks.androidcrudapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by johncrooks on 10/13/16.
 */

public class OnLongClickListenerFillUp implements View.OnLongClickListener {
    Context context;
    String id;


    @Override
    public boolean onLongClick(View v) {
        context = v.getContext();
        id = v.getTag().toString();
        final CharSequence[] items = { "Edit", "Delete" };

        new AlertDialog.Builder(context).setTitle("Fill Up Number " + id)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            editRecord(Integer.parseInt(id));
                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();
                        } else if (item ==1){
                            boolean deleteSuccessful = new TableControllerFillUp(context).deleteSingle(Integer.parseInt(id));

                            if (deleteSuccessful){
                                Toast.makeText(context, "Gas record was deleted.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Unable to delete gas record.", Toast.LENGTH_SHORT).show();
                            }

                            ((MainActivity) context).countRecords();
                            ((MainActivity) context).readRecords();
                        }
                        dialog.dismiss();
                    }
                }).show();

        return false;
    }

    public void editRecord(final int id){
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

                                ((MainActivity) context).countRecords();
                                ((MainActivity) context).readRecords();


                                dialog.cancel();
                            }

                        }).show();
    }
}
