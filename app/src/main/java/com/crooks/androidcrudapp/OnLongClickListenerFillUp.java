package com.crooks.androidcrudapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

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

    }
}
