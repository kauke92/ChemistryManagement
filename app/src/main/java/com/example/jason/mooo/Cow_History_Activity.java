package com.example.jason.mooo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.MedicineAdministered;

import java.util.ArrayList;

public class Cow_History_Activity extends AppCompatActivity {

ListView cowHistory;

    ArrayAdapter<MedicineAdministered> medicines;
    ArrayList<MedicineAdministered> medicinesList;
    public Button top_profile, bottom_home, bottom_livestock, bottom_chemical;
    TextView top_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cow__history_);
        Database data = new Database(this);
        medicinesList = new ArrayList<MedicineAdministered>();
        medicines = new ArrayAdapter<MedicineAdministered>(this,android.R.layout.simple_list_item_1,medicinesList);
        cowHistory = (ListView)findViewById(R.id.History);
        bottom_chemical = (Button) findViewById(R.id.button_history_bottom_chemicals);
        bottom_home = (Button) findViewById(R.id.button_history_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_history_bottom_livestock);
        top_username = (TextView) findViewById(R.id.text_username);
        top_profile = (Button) findViewById(R.id.button_profile);

        Log.i("cow",getIntent().getExtras().getString("cowID"));

        top_username.setText(data.getUsername());

        data.getCowHistory(medicines, getIntent().getExtras().getString("cowID"));

        cowHistory.setAdapter(medicines);

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(Cow_History_Activity.this, User_info.class);
                Cow_History_Activity.this.startActivity(user_info_intent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(Cow_History_Activity.this, livestock_main.class);
                Cow_History_Activity.this.startActivity(livestock_main_intent);
            }
        });




        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(Cow_History_Activity.this, main_puchase.class);
                Cow_History_Activity.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(Cow_History_Activity.this, homeScreen.class);
                Cow_History_Activity.this.startActivity(home_main_intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cow__history_, menu);
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
