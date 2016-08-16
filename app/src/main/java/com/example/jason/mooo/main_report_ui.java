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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Resources.DatePickerFragment;


import java.util.Map;


public class main_report_ui extends AppCompatActivity implements DatePickerFragment.setViewItems,
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button bottom_home;
    Button bottom_medicine;
    Button bottom_livestock;
    Button top_profile;
    Spinner dropdown;
    Button from_date;
    Button to_date;
    TextView top_username;
    static Button genReport;

    int toInt;
    int fromInt;

    final String[] data = {"administered", "stock", "purchase"};
    public String item = "";

    String userID = "";
    static String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui_reports);

        top_username = (TextView) findViewById(R.id.text_username);
        Database data2 = new Database(this);

        top_username.setText(data2.getUsername());

        top_profile = (Button) findViewById(R.id.button_profile);

        bottom_medicine = (Button) findViewById(R.id.button_pdfmain_bottom_medicine);
        bottom_home = (Button) findViewById(R.id.button_pdfmain_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_pdfmain_bottom_livestock);


        userID = new Database(this).getUserID();
        userName = new Database(this).getUsername();


        dropdown = (Spinner) findViewById(R.id.report_selector);
        from_date = (Button) findViewById(R.id.from_date);
        to_date = (Button) findViewById(R.id.to_date);

        genReport = (Button) findViewById(R.id.genReport);
        genReport.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, data);
        dropdown.setAdapter(adapter);

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(main_report_ui.this, User_info.class);
                main_report_ui.this.startActivity(user_info_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(main_report_ui.this, homeScreen.class);
                main_report_ui.this.startActivity(home_main_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(main_report_ui.this, livestock_main.class);
                main_report_ui.this.startActivity(livestock_main_intent);
            }
        });



        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(main_report_ui.this, main_puchase.class);
                main_report_ui.this.startActivity(chemicals_main_intent);
            }
        });

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker_from = new DatePickerFragment();
                datepicker_from.show(getFragmentManager(), "FromDate");
            }
        });


        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datepicker_from = new DatePickerFragment();
                datepicker_from.show(getFragmentManager(), "ToDate");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_template_pages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setExpiryDate(Map<String, Integer> value) {

    }

    @Override
    public void setReceivedDate(Map<String, Integer> value) {

    }

    @Override
    public void setFromDate(Map<String, Integer> mappy) {
        String text = "";
        for (String key : mappy.keySet()) {
            text = key;
        }

        fromInt = mappy.get(text);
        from_date.setText(text);
    }

    @Override
    public void setToDate(Map<String, Integer> mappy) {
        String text = "";
        for (String key : mappy.keySet()) {
            text = key;
        }

        toInt = mappy.get(text);
        to_date.setText(text);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        String option = String.valueOf(dropdown.getSelectedItem());
        if (id == R.id.genReport) {

            Database data = new Database(this);



            if (option.equalsIgnoreCase("administered")) {
                genReport.setEnabled(false);
                data.getCowTreatmentHistory(toInt, fromInt);

            } else if (option.equalsIgnoreCase("stock")) {
                genReport.setEnabled(false);
                data.getCurrentStock(toInt, fromInt);
            } else if (option.equalsIgnoreCase("purchase")) {
                genReport.setEnabled(false);
                Log.i("PurchaseCheck", Integer.toString(toInt));
                data.getPurchasesHistory(toInt, fromInt);
            }
        }

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        item = data[pos];
    }

    public void onNothingSelected(AdapterView<?> parent) {
        item = "-1";
    }


    static public void toggle() {
        genReport.setEnabled(true);
    }
}

