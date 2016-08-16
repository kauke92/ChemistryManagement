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

public class change_email extends AppCompatActivity {

    Button confirm_change_email;
    Button bottom_home;
    Button bottom_medicine;
    Button bottom_livestock;
    Button top_profile;
    TextView old_email, new_email, re_type_email, top_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_email);


        confirm_change_email = (Button)findViewById(R.id.button_confirm_change_email);
        old_email = (TextView)findViewById(R.id.old_email_change);
        new_email = (TextView)findViewById(R.id.new_email_change);
        re_type_email = (TextView)findViewById(R.id.re_type_email_change);

        Database data2 = new Database(this);
        top_username = (TextView) findViewById(R.id.text_username);

        top_username.setText(data2.getUsername());

        top_profile = (Button) findViewById(R.id.button_profile);

        bottom_medicine = (Button) findViewById(R.id.button_changeemail_bottom_medicine);
        bottom_home = (Button) findViewById(R.id.button_changeemail_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_changeemail_bottom_livestock);

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(change_email.this, User_info.class);
                change_email.this.startActivity(user_info_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(change_email.this, homeScreen.class);
                change_email.this.startActivity(home_main_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(change_email.this, livestock_main.class);
                change_email.this.startActivity(livestock_main_intent);
            }
        });



        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(change_email.this, main_puchase.class);
                change_email.this.startActivity(chemicals_main_intent);
            }
        });


        confirm_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database data = new Database(change_email.this);

                data.changeEmail(old_email.getText().toString(), new_email.getText().toString());

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
