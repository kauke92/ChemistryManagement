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

public class view_purchases extends AppCompatActivity {

    public Button top_profile, bottom_home, bottom_livestock, bottom_chemical;
    TextView top_username;
    //Database data = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_livestock);
        top_profile = (Button) findViewById(R.id.button_history_top_profile);
        top_username = (TextView) findViewById(R.id.text_username);
        Database data = new Database(this);
        top_username.setText(data.getUsername());
        bottom_chemical = (Button) findViewById(R.id.button_history_bottom_chemicals);
        bottom_home = (Button) findViewById(R.id.button_history_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_history_bottom_livestock);

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(view_purchases.this, User_info.class);
                view_purchases.this.startActivity(user_info_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(view_purchases.this, livestock_main.class);
                view_purchases.this.startActivity(livestock_main_intent);
            }
        });




        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(view_purchases.this, main_puchase.class);
                view_purchases.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(view_purchases.this, homeScreen.class);
                view_purchases.this.startActivity(home_main_intent);
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
