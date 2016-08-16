package com.example.jason.mooo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.Drug;
import com.example.jason.mooo.Model.Purchase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class history_livestock extends AppCompatActivity {

    public Button top_profile, bottom_home, bottom_livestock, bottom_chemical;
    TextView top_username;
    ArrayList<Purchase> purchaseList;
    ArrayAdapter<Purchase>purchases;
    ListView batches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_livestock);
        top_profile = (Button) findViewById(R.id.button_profile);
        top_username = (TextView) findViewById(R.id.text_username);

        Database data = new Database(this);
        top_username.setText(data.getUsername());
        batches = (ListView)findViewById(R.id.batch);
        purchaseList = new ArrayList<Purchase>();
        purchases = new ArrayAdapter<Purchase>(this,android.R.layout.simple_list_item_1,purchaseList);
        data.getAllPurchases(purchases);
        batches.setAdapter(purchases);
       // top_username.setText(data.getUsername());
        bottom_chemical = (Button) findViewById(R.id.button_history_bottom_chemicals);
        bottom_home = (Button) findViewById(R.id.button_history_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_history_bottom_livestock);

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(history_livestock.this, User_info.class);
                history_livestock.this.startActivity(user_info_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(history_livestock.this, livestock_main.class);
                history_livestock.this.startActivity(livestock_main_intent);
            }
        });




        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(history_livestock.this, main_puchase.class);
                history_livestock.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(history_livestock.this, homeScreen.class);
                history_livestock.this.startActivity(home_main_intent);
            }
        });

        batches.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Purchase purchasedDrug = purchases.getItem(position);

                final TextView Name = new TextView(history_livestock.this);
                final TextView batch_dialog = new TextView(history_livestock.this);
                final TextView exp_date = new TextView(history_livestock.this);
                final TextView purchase_date = new TextView(history_livestock.this);
                final TextView amount_left = new TextView(history_livestock.this);
                final TextView purchase_place_dialog = new TextView(history_livestock.this);
                final TextView status = new TextView(history_livestock.this);

                Name.setText("Medicine Name: " + purchasedDrug.getDrugName());
                batch_dialog.setText("Batch Name: " + purchasedDrug.getBatch());
                amount_left.setText("Amount Left: " + purchasedDrug.getAmountLeft());
                purchase_place_dialog.setText("Purchase Place: " + purchasedDrug.getPurchasePlace());
                String cur_status = "Batch is good to Administer";

                SimpleDateFormat date = new SimpleDateFormat("yymmdd", Locale.ENGLISH);

                String expDate1 = String.valueOf(purchasedDrug.getExpireDate());

                Calendar c = Calendar.getInstance(Locale.ENGLISH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);
                int day = c.get(Calendar.DATE);

                String curDate = String.valueOf(year).substring(2) + String.valueOf(month + 1) + String.valueOf(day);


                Date d1 = null;
                Date date2 = null;
                Date expdatetoshow = null;
                Date recievetoshow = null;



                try {
                    d1 = date.parse(expDate1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    date2 = date.parse(curDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    expdatetoshow = date.parse(String.valueOf(purchasedDrug.getExpireDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    recievetoshow = date.parse(String.valueOf(purchasedDrug.getPurchaseDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (d1.compareTo(date2) <= 0) {
                    cur_status = "Batch has expired";
                }

                exp_date.setText("Expiry Date: " + expdatetoshow.toString().replace("GMT+11:00", ""));
                purchase_date.setText("Purchase Date: " + recievetoshow.toString().replace("GMT+11:00", ""));

                if (purchasedDrug.getAmount() <= 0) {
                    cur_status = "Batch has run out";
                }


                //else{
                //    cur_status = "Batch is good to administer";
                // }

                status.setText(cur_status);


                LinearLayout password_layout = new LinearLayout(history_livestock.this);
                password_layout.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
                password_layout.addView(Name);
                password_layout.addView(batch_dialog);
                password_layout.addView(exp_date);
                password_layout.addView(purchase_date);
                password_layout.addView(amount_left);
                password_layout.addView(purchase_place_dialog);
                password_layout.addView(status);


                new AlertDialog.Builder(history_livestock.this)
                        .setTitle("Current Medicine Batch")
                        .setView(password_layout)
                        .setCancelable(true)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })


                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

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
