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

public class User_info extends AppCompatActivity {

    Button change_password;
    Button change_email;
    Button logout;
    Button bottom_livestock;
    Button bottom_medicine;
    Button bottom_home;
    TextView top_username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        change_email = (Button)findViewById(R.id.button_change_email);
        change_password = (Button)findViewById(R.id.button_change_password);
        logout = (Button)findViewById(R.id.button_logout);
        top_username = (TextView) findViewById(R.id.text_username);
        Database data = new Database(this);
        top_username.setText(data.getUsername());
        bottom_livestock = (Button) findViewById(R.id.button_userinfo_bottom_livestock);
        bottom_medicine = (Button) findViewById(R.id.button_userinfo_bottom_medicine);
        bottom_home = (Button) findViewById(R.id.button_userinfo_bottom_home);

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(User_info.this, livestock_main.class);
                User_info.this.startActivity(livestock_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_intent = new Intent(User_info.this, homeScreen.class);
                User_info.this.startActivity(home_intent);
            }
        });

        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(User_info.this, main_puchase.class);
                User_info.this.startActivity(chemicals_main_intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Database data = new Database(User_info.this);
                data.logout();

                Intent logout_intent = new Intent(User_info.this, Login.class);
                User_info.this.startActivity(logout_intent);
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent change_password_intent = new Intent(User_info.this, change_password.class);
                User_info.this.startActivity(change_password_intent);
            }
        });


        change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent change_email_intent = new Intent(User_info.this, change_email.class);
                User_info.this.startActivity(change_email_intent);
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
