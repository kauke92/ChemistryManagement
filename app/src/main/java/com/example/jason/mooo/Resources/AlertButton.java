package com.example.jason.mooo.Resources;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.jason.mooo.Login;

/**
 * Created by Stuart on 8/26/2015.
 */
public class AlertButton {

    public AlertButton(final Context context, String message) {

        new AlertDialog.Builder(context)
                .setTitle("Message")
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent test_intent = new Intent(context, Login.class);
                        context.startActivity(test_intent);
                    }
                })


                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }




}
