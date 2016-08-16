package com.example.jason.mooo;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.Drug;
import com.example.jason.mooo.Resources.DatePickerFragment;

import java.util.ArrayList;
import java.util.Map;

public class purchaseDrugActivity extends AppCompatActivity implements DatePickerFragment.setViewItems {

    Button expDate;
    Button receivedDate;
    Button addDrug;
    TextView drugName;
    TextView purchasePlace;
    ArrayList<Drug> drugs;
    ArrayAdapter<Drug> drugadapter;
    TextView batch;
    Database data;
    int receivedInt;
    int expiryInt;
    Button bottom_livestock;
    Button bottom_home;
    Button bottom_chemical;
    Button top_profile;
    public Spinner spinner;
    TextView top_username;
    TextView AmountID;
    String[] list_drugs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_drug);
        expDate = (Button) findViewById(R.id.expDate);
        receivedDate = (Button) findViewById(R.id.receivedDate);
        addDrug = (Button) findViewById(R.id.addMedicine);
        drugName = (TextView) findViewById(R.id.drugName);
        purchasePlace = (TextView) findViewById(R.id.purchasePlace);
        AmountID = (TextView) findViewById(R.id.AmountID);
        batch = (TextView) findViewById(R.id.Batch);
        spinner =(Spinner) findViewById(R.id.spinner_add_Medicine);
        drugs = new ArrayList<>();

        drugadapter = new ArrayAdapter<Drug>(this,android.R.layout.simple_spinner_dropdown_item,drugs);


        data = new Database(this);
        top_username = (TextView) findViewById(R.id.text_username);
        top_username.setText(data.getUsername());
        top_profile = (Button) findViewById(R.id.button_profile);


        receivedDate = (Button)findViewById(R.id.receivedDate);
        bottom_chemical = (Button)findViewById(R.id.button_purchaseMedicine_bottom_chemical);
        bottom_home = (Button)findViewById(R.id.button_purchaseMedicine_bottom_home);
        bottom_livestock = (Button)findViewById(R.id.button_purchaseMedicine_bottom_livestock);

        spinner.setAdapter(drugadapter);
        data.getDrugs(drugadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setListeners();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_purchase_drug, menu);
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


    @Override
    public void setExpiryDate(Map<String, Integer> mappy) {
        String text = "";
        for (String key : mappy.keySet()) {
            text = key;
        }

        expiryInt = mappy.get(text);
        expDate.setText(text);
    }

    @Override
    public void setReceivedDate(Map<String, Integer> mappy) {
        String text = "";
        for (String key : mappy.keySet()) {
            text = key;
        }

        receivedInt = mappy.get(text);
        receivedDate.setText(text);
    }

    @Override
    public void setFromDate(Map<String, Integer> value) {

    }

    @Override
    public void setToDate(Map<String, Integer> value) {

    }


    private void setListeners()
    {
        expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getFragmentManager(), "ExpiryDate");
            }
        });

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(purchaseDrugActivity.this,User_info.class);
                startActivity(profileIntent);
            }
        });

        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(purchaseDrugActivity.this, main_puchase.class);
                purchaseDrugActivity.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(purchaseDrugActivity.this, livestock_main.class);
                purchaseDrugActivity.this.startActivity(livestock_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(purchaseDrugActivity.this, homeScreen.class);
                purchaseDrugActivity.this.startActivity(home_main_intent);
            }
        });


        receivedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getFragmentManager(), "ReceivedDate");
            }
        });




        addDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (purchasePlace.getText().toString().isEmpty()){

                    Toast.makeText(purchaseDrugActivity.this, "Please enter a purchase place.", Toast.LENGTH_LONG).show();
                }
                else if (batch.getText().toString().isEmpty()) {

                    Toast.makeText(purchaseDrugActivity.this, "Please enter a batch.", Toast.LENGTH_LONG).show();
                }

                else if (expDate.getText().toString().equals("Expiry Date")){
                    Toast.makeText(purchaseDrugActivity.this, "Please enter an expiry date.", Toast.LENGTH_LONG).show();

                }
                else if(AmountID.getText().toString().isEmpty()){
                    Toast.makeText(purchaseDrugActivity.this, "Please enter an amount.", Toast.LENGTH_LONG).show();
                }
                else if(!AmountID.getText().toString().matches("[-+]?\\d*\\.?\\d+")){
                    Toast.makeText(purchaseDrugActivity.this, "Please enter a valid amount.", Toast.LENGTH_LONG).show();
                }
                else if (receivedDate.getText().toString().equals("Received Date")){
                    Toast.makeText(purchaseDrugActivity.this, "Please enter a received date.", Toast.LENGTH_LONG).show();

                }else {

                    String drugname = drugadapter.getItem(spinner.getSelectedItemPosition()).getName();
                    if (!drugname.isEmpty()) {
                        int drugWitholdingMilk = drugadapter.getItem(spinner.getSelectedItemPosition()).getWithholdingMilk();
                        int drugWitholdingSlaughter = drugadapter.getItem(spinner.getSelectedItemPosition()).getWithholdingSlaughter();
                        Drug drug = new Drug(drugname, drugWitholdingMilk, drugWitholdingSlaughter);
                        int amount = Integer.parseInt(AmountID.getText().toString());
                        data.addPurchase(drug, purchasePlace.getText().toString(), expiryInt, receivedInt, "Signed by", batch.getText().toString(), amount);
                    }else{
                        Toast.makeText(purchaseDrugActivity.this, "No Medicine has been selected.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
