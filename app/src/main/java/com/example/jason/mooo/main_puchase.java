package com.example.jason.mooo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;


public class main_puchase extends AppCompatActivity {

    Button purchaseDrug;
    Button getBatch;
    Button getDrugs;
    Button bottom_livestock;
    Button bottom_home;
    Button top_profile;
    TextView top_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_puchase);
        purchaseDrug = (Button)findViewById(R.id.button_pruchase_Medicine);
        getBatch = (Button)findViewById(R.id.button_get_batch);
        getDrugs = (Button)findViewById(R.id.button_get_Medicines);
        bottom_livestock = (Button)findViewById(R.id.button_Medicine_bottom_livestock);
        bottom_home = (Button)findViewById(R.id.button_Medicine_bottom_home);
        top_profile = (Button) findViewById(R.id.button_top_profile);
        top_username = (TextView) findViewById(R.id.text_username);
        Database data = new Database(this);

        top_username.setText(data.getUsername());

        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_puchase, menu);
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

    private void setListeners()
    {





        purchaseDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent = new Intent(main_puchase.this,purchaseDrugActivity.class);
                startActivity(purchaseIntent);
            }
        });

        getDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getDrugsIntent = new Intent(main_puchase.this,chemical_main.class);
                startActivity(getDrugsIntent);
            }
        });

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(main_puchase.this,User_info.class);
                startActivity(profileIntent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(main_puchase.this,homeScreen.class);
                startActivity(homeIntent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent livestockIntent = new Intent(main_puchase.this, livestock_main.class);
                startActivity(livestockIntent);
            }
        });

        getBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent batchIntent = new Intent(main_puchase.this, history_livestock.class);
                startActivity(batchIntent);
            }
        });

    }
}
