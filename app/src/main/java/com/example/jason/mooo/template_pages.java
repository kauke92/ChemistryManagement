package com.example.jason.mooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.example.jason.mooo.Model.Purchase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class template_pages extends AppCompatActivity {
    Spinner drugs;
    Spinner amount;
    Spinner days;
    TextView cowID;
    TextView date_information;
    Date today;
    DateFormat myDate;
    ArrayList<Purchase> myDrugs;
    ArrayList<String> myAmounts;
    ArrayList<String> myDays;
    ArrayAdapter<Purchase> allDrugs;
    ArrayAdapter<CharSequence> allAmounts;
    ArrayAdapter<CharSequence> allDays;
    Button administer;
    Button history;
    Database data;
    Purchase purchase;
    int amountAdministered;
    int length;
    Button bottom_home;
    Button bottom_medicine;
    Button bottom_livestock;
    Button top_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_pages);
        data = new Database(this);
        final String id = getIntent().getStringExtra("cowID");
        today = new Date();
        cowID = (TextView) findViewById(R.id.cowIDVal);
        cowID.setText(id);
        myDays = new ArrayList<String>();
        myDrugs = new ArrayList<Purchase>();
        myAmounts = new ArrayList<String>();
        administer = (Button) findViewById(R.id.administer);
        history = (Button) findViewById(R.id.button_history_livestock);
        drugs = (Spinner) findViewById(R.id.Drugs);
        amount = (Spinner) findViewById(R.id.amount);
        days = (Spinner) findViewById(R.id.days);
        date_information = (TextView) findViewById(R.id.date_information);
        myDate = new SimpleDateFormat("dd/MM/yyy");
        date_information.setText(myDate.format(today).toString());

        top_profile = (Button) findViewById(R.id.button_profile);

        bottom_medicine = (Button) findViewById(R.id.livestock_info_bottom_medicine);
        bottom_home = (Button) findViewById(R.id.livestock_info_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.livestock_info_bottom_livestock);


        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(template_pages.this, User_info.class);
                template_pages.this.startActivity(user_info_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(template_pages.this, livestock_main.class);
                template_pages.this.startActivity(livestock_main_intent);
            }
        });


        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(template_pages.this, main_puchase.class);
                template_pages.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(template_pages.this, homeScreen.class);
                template_pages.this.startActivity(home_main_intent);
            }
        });


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set button to history here
                Intent historyIntent = new Intent(template_pages.this, Cow_History_Activity.class);
                historyIntent.putExtra("cowID", cowID.getText().toString());
                startActivity(historyIntent);
            }
        });

        administer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database data = new Database(template_pages.this);

                Calendar myCalendar = Calendar.getInstance();
                DateFormat intFormat = new SimpleDateFormat("yyMMdd");
                int today = Integer.valueOf(intFormat.format(myCalendar.getTime()));

                if (allDrugs.getCount() == 0 || drugs.getCount() == 0) {
                    Toast.makeText(template_pages.this, "No drug selected", Toast.LENGTH_LONG).show();
                    return;
                }


                data.administerDrug(allDrugs.getItem(drugs.getSelectedItemPosition()), id, today, amountAdministered, length);
                finish();

            }
        });

        allDrugs = new ArrayAdapter<Purchase>(this, android.R.layout.simple_spinner_item, myDrugs);
        drugs.setAdapter(allDrugs);

        data.adminsterList(allDrugs);
        drugs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                purchase = allDrugs.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parents) {

            }
        });


        allAmounts = ArrayAdapter.createFromResource(this, R.array.amounts, android.R.layout.simple_spinner_item);
        allAmounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amount.setAdapter(allAmounts);
        amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                amountAdministered = Integer.valueOf(allAmounts.getItem(position).toString());
     //           Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parents) {

            }
        });


        allDays = ArrayAdapter.createFromResource(this, R.array.periods, android.R.layout.simple_spinner_item);
        allDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(allDays);
        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String days = allDays.getItem(position).toString();
                 length = Integer.valueOf(days.substring(0, days.indexOf(" ")));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parents) {

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
