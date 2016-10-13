package com.crooks.androidcrudapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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

        new AlertDialog.Builder(context).setTitle("Trip Number " + id)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(item == 0){
                            editRecord(Integer.parseInt(id));
                        }


                        dialog.dismiss();

                    }
                }).show();

        return false;
    }

    public void editRecord(final int id){
        final TableControllerFillUp tableControllerFillUp = new TableControllerFillUp(context);
        FillUp fillUp = tableControllerFillUp.readSingle(id);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.gas_input_form, null, false);
        final EditText editCostPerGallon = (EditText) formElementsView.findViewById(R.id.editCostPerGallon);
        final EditText editGallonsPumped = (EditText) formElementsView.findViewById(R.id.editGallonsPumped);

        editCostPerGallon.setText(String.valueOf(fillUp.costPerGallon));
        editGallonsPumped.setText(String.valueOf(fillUp.gallonsPumped));

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Edit Record")
                .setPositiveButton("Save Changes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                dialog.cancel();
                            }

                        }).show();
    }
}
