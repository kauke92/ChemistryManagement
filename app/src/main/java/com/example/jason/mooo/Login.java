package com.example.jason.mooo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jason.mooo.Business.Database;

public class Login extends AppCompatActivity {


    Button button_login;
    Button button_register;


    //LinearLayout loading_splash;
    loading_splash ls;
    private username_login mDataBaseHelper;
    TextView username_text, reset_link, password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mDataBaseHelper = new username_login(this);
        setContentView(R.layout.activity_login);

        Database data = new Database(this);


        if (data.getAuthInfo()) {
            Intent test_intent = new Intent(this, homeScreen.class);
            this.startActivity(test_intent);
        }

        username_text = (TextView) findViewById(R.id.username_text);
        password_text = (TextView) findViewById(R.id.password_text);
        ls = new loading_splash(new AlertDialog.Builder(Login.this), Login.this.getLayoutInflater());
        button_login = (Button) findViewById(R.id.button_login);
        //ls = (LinearLayout) findViewById(R.id.loading_splash_layout);

        button_login.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                //builder.setView(inflater.inflate(R.layout.loading_splash, null));
                                                // builder.setCancelable(false);
                                                // AlertDialog alert = builder.create();
                                                // builder.show();
                                                //ls.setSplashStatus(true);


                                                authenticate();

                                                /*
                                                The splash screen gets stuck if incorrect login details are entered.
                                                Theoretically, once authenticate finishes, it should return here and then the alert would cancel,
                                                regardless of whether or not the user entered correct login details.

                                                Tasks:
                                                1. Figure out where the program ends up after running authenticate.
                                                2. Fix the loading_splash bug.
                                                 */

                                                System.out.println("gets here");
                                                //alert.cancel();


                                            }
                                        }
        );


        button_register = (Button) findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent register_intent = new Intent(Login.this, Register.class);
                                                   Login.this.startActivity(register_intent);
                                               }
                                           }
        );

        reset_link = (TextView) findViewById(R.id.reset_link);
        reset_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reset_intent = new Intent(Login.this, Reset_Password.class);
                Login.this.startActivity(reset_intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void authenticate() {


        Database data = new Database(this);

        String username = username_text.getText().toString();
        String password = password_text.getText().toString();
// TODO Change back before commit

        data.authWithPassword(username, password);

        //data.authWithPassword("sblake@outlook.com", "password");

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
    public void onBackPressed() {
        // discuss in meeting, we can do stuff with this back button
    }
}