package com.example.jason.mooo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mooo.Business.Database;

public class add_new_chemical extends AppCompatActivity {

    Database data;
    EditText drugName;
    EditText drugWitholdingMilk;
    EditText getDrugWitholdingSlaughter;
    Button addDrug;
    Button bottom_livestock;
    Button bottom_chemical;
    Button bottom_home;
    Button top_profile;
    TextView top_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_chemical);

        data = new Database(this);
        drugName = (EditText)findViewById(R.id.drugName);
        top_username = (TextView) findViewById(R.id.text_username);
        top_username.setText(data.getUsername());
        drugWitholdingMilk = (EditText)findViewById(R.id.milkText);
        getDrugWitholdingSlaughter = (EditText)findViewById(R.id.slaughterText);
        addDrug = (Button) findViewById(R.id.addDrug);
        bottom_chemical = (Button) findViewById(R.id.button_addnewchem_bottom_chemical);
        bottom_home = (Button) findViewById(R.id.button_addnewchem_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_addnewchem_bottom_livestock);
        top_profile = (Button) findViewById(R.id.button_profile);


        addDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drugWitholdingMilk.getText().toString().isEmpty()){
                    Toast.makeText(add_new_chemical.this, "Please enter a withholding value for milk.", Toast.LENGTH_LONG).show();
                }else if(getDrugWitholdingSlaughter.getText().toString().isEmpty()){
                    Toast.makeText(add_new_chemical.this, "Please enter a withholding value for slaughter.", Toast.LENGTH_LONG).show();
                }else if(drugName.getText().toString().isEmpty()){
                    Toast.makeText(add_new_chemical.this, "Please enter a medicine name.", Toast.LENGTH_LONG).show();
                }else{
                int withholdingMilk = Integer.parseInt(drugWitholdingMilk.getText().toString());
                int withholdingSlaughter = Integer.parseInt(getDrugWitholdingSlaughter.getText().toString());
                data.addDrug(drugName.getText().toString(),withholdingMilk,withholdingSlaughter);
                }
            }
        });

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(add_new_chemical.this, User_info.class);
                add_new_chemical.this.startActivity(user_info_intent);
            }
        });


        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(add_new_chemical.this, main_puchase.class);
                add_new_chemical.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(add_new_chemical.this, livestock_main.class);
                add_new_chemical.this.startActivity(livestock_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(add_new_chemical.this, homeScreen.class);
                add_new_chemical.this.startActivity(home_main_intent);
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
