package com.example.jason.mooo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Login;

/**
 * Created by Stuart on 8/26/2015.
 */
public class TempPasswordAlert {

    public String realPassword;


    public TempPasswordAlert(final Context context, final String oldPassword) {
        realPassword = "";
        final EditText new_password = new EditText(context);
        final EditText retype_password = new EditText(context);

        new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        retype_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new_password.setHint("New Password");
        retype_password.setHint("Retype Password");
        LinearLayout password_layout = new LinearLayout(context);
        password_layout.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        password_layout.addView(new_password);
        password_layout.addView(retype_password);


        new AlertDialog.Builder(context)
                .setTitle("Type New Password")
                .setMessage("Reset your password")
                .setView(password_layout)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (new_password.getText().toString().equalsIgnoreCase(retype_password.getText().toString())) {

                            realPassword = new_password.getText().toString();
                            Database data = new Database(context);
                            data.changePassword(oldPassword, realPassword);
                        } else {
                            Log.i("Purchase Check", "Resetting and creating a new one");
                            new TempPasswordAlert(context, oldPassword);
                        }
                    }
                })


                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }


}
