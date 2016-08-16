package com.example.jason.mooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mooo.Business.Database;

public class add_new_livestock extends AppCompatActivity {
    Button button_scan_for_id;


    TextView idText;
    TextView top_username;
    Button addCows;
    Button top_profile;
    Button bottom_chemical;
    Button bottom_home;
    Button bottom_livestock;

    private final int BARCODE_CODE = 200;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_livestock);
        addCows = (Button)findViewById(R.id.addCow);
        top_username = (TextView) findViewById(R.id.text_username);


        top_profile = (Button) findViewById(R.id.button_profile);

        bottom_chemical = (Button)findViewById(R.id.button_addlivestock_bottom_chemical);
        bottom_home = (Button)findViewById(R.id.button_addlivestock_bottom_home);
        bottom_livestock = (Button)findViewById(R.id.button_addlivestock_bottom_livestock);

        final Database data = new Database(this);
        top_username.setText(data.getUsername());

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(add_new_livestock.this, User_info.class);
                add_new_livestock.this.startActivity(user_info_intent);
            }
        });

        addCows.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(!idText.getText().toString().isEmpty()) {

                    data.addCow(idText.getText().toString());
                    Intent myIntent = new Intent(add_new_livestock.this, livestock_main.class);
                    startActivity(myIntent);

                }
                else{
                    Toast.makeText(add_new_livestock.this, "Please specify an ID.", Toast.LENGTH_LONG).show();
                }
            }
        });



        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(add_new_livestock.this, main_puchase.class);
                add_new_livestock.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(add_new_livestock.this, livestock_main.class);
                add_new_livestock.this.startActivity(livestock_main_intent);

            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(add_new_livestock.this, homeScreen.class);
                add_new_livestock.this.startActivity(home_main_intent);
            }
        });





        button_scan_for_id = (Button) findViewById(R.id.button_scan_for_id);
        final Intent test_intent = new Intent(this, BarcodeScan.class);

        button_scan_for_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_livestock.this.startActivity(test_intent);
                startActivity(test_intent);
            }
        });
        idText = (TextView) findViewById(R.id.idText);

        Intent intent = getIntent();
        if (intent != null) {
            String resultText = intent.getStringExtra("barcode");
            idText.setText(resultText);
       }



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

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(requestCode == BARCODE_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                String id = data.getExtras().getString("ID");
                idText.setText(id);
            }
        }
    }

}
