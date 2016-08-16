package com.example.jason.mooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;

public class change_password extends AppCompatActivity {

    Button confirm_change_password;
    Button bottom_home;
    Button bottom_medicine;
    Button bottom_livestock;
    Button top_profile;
    TextView old_password, new_password, re_type_password, top_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);


        confirm_change_password = (Button) findViewById(R.id.button_confirm_change_password);
        old_password = (TextView) findViewById(R.id.old_password_change);
        new_password = (TextView) findViewById(R.id.new_password_change);
        re_type_password = (TextView) findViewById(R.id.re_type_password_change);
        top_username = (TextView) findViewById(R.id.text_username);
        Database data2 = new Database(this);

        top_username.setText(data2.getUsername());

        top_profile = (Button) findViewById(R.id.button_profile);

        bottom_medicine = (Button) findViewById(R.id.button_changepass_bottom_medicine);
        bottom_home = (Button) findViewById(R.id.button_changepass_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_changepass_bottom_livestock);

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(change_password.this, User_info.class);
                change_password.this.startActivity(user_info_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(change_password.this, homeScreen.class);
                change_password.this.startActivity(home_main_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(change_password.this, livestock_main.class);
                change_password.this.startActivity(livestock_main_intent);
            }
        });



        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(change_password.this, main_puchase.class);
                change_password.this.startActivity(chemicals_main_intent);
            }
        });


        confirm_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database data = new Database(change_password.this);
                data.changePassword(old_password.getText().toString(), new_password.getText().toString());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_template_pages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
