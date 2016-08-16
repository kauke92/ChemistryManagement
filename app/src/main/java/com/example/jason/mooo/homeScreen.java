package com.example.jason.mooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.Cow;

public class homeScreen extends AppCompatActivity {

    public TextView top_username;
    public Button top_profile, bottom_home, bottom_livestock, bottom_chemical, middle_livestock, middle_pdf, middle_chemicals;
    private ArrayAdapter<Cow> cowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        Database data = new Database(this);
        top_username = (TextView) findViewById(R.id.text_username);
        top_username.setText(data.getUsername());

        top_profile = (Button) findViewById(R.id.button_profile);
        bottom_chemical = (Button) findViewById(R.id.button_home_bottom_chemical);
        bottom_home = (Button) findViewById(R.id.button_home_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_home_bottom_livestock);
        middle_chemicals = (Button) findViewById(R.id.button_middle_chemicals);
        middle_livestock = (Button) findViewById(R.id.button_middle_livestock);
        middle_pdf = (Button) findViewById(R.id.button_middle_pdf);
    // this is the new password dialog for when the user enters a temp password
        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(homeScreen.this, User_info.class);
                homeScreen.this.startActivity(user_info_intent);
            }
        });

        middle_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(homeScreen.this, livestock_main.class);
                homeScreen.this.startActivity(livestock_main_intent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(homeScreen.this, livestock_main.class);
                homeScreen.this.startActivity(livestock_main_intent);
            }
        });


        middle_chemicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(homeScreen.this, main_puchase.class);
                homeScreen.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(homeScreen.this, main_puchase.class);
                homeScreen.this.startActivity(chemicals_main_intent);
            }
        });

        middle_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pdf_intent = new Intent(homeScreen.this, main_report_ui.class);
                homeScreen.this.startActivity(pdf_intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        // discuss in meeting, we can do stuff with this back button
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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