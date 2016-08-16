package com.example.jason.mooo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mooo.Business.Database;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity  implements RegisterView{

    EditText password_text, firstname_text, surname_text, password2_text, email_text;
    TextView top_username;
    Button button_register;
    username_login user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //bn = getIntent().getExtras();
        user = new username_login(this);
        //user = (username_login) bn.getSerializable("DataBase");
        button_register = (Button) findViewById(R.id.button_register);
        password_text = (EditText) findViewById(R.id.password_text);
        password2_text = (EditText) findViewById(R.id.password2_text);
        firstname_text = (EditText) findViewById(R.id.firstname_text);
        surname_text = (EditText) findViewById(R.id.surname_text);
        email_text = (EditText) findViewById(R.id.email);

        Database data = new Database(this);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String p_word = password_text.getText().toString();
               String p_word2 = password2_text.getText().toString();
                String first_name = firstname_text.getText().toString();
                String surname = surname_text.getText().toString();
                String email = email_text.getText().toString();
               //print out p_word, p_word2
                if (p_word.equals(p_word2) == false){
                    password_text.setError("Passwords don't match.");

                    Toast.makeText(Register.this, "Passwords don't match.", Toast.LENGTH_LONG).show();


                }

                else if (p_word.length() < 8){
                    Toast.makeText(Register.this, "Password is too short. 8 characters are required.", Toast.LENGTH_LONG).show();
                    password_text.setError("Password is too short. 8 characters are required.");


                }

                else {
                    if (first_name.length() <= 0 ) {
                        //Create a message for missing details

                        Toast.makeText(Register.this, "Please enter your firstname.", Toast.LENGTH_LONG).show();
                        firstname_text.setError("Please enter your firstname.");

                    }
                    else if( surname.length() <= 0){
                        Toast.makeText(Register.this, "Please enter your surname.", Toast.LENGTH_LONG).show();
                        surname_text.setError("Please enter your surname.");
                    }
                    else if( email.length() <= 0){
                        Toast.makeText(Register.this, "Please enter your Email.", Toast.LENGTH_LONG).show();
                        email_text.setError("Please enter your Email.");
                    }

                    else {

                        Database data = new Database(Register.this);
                        Map<String,String> user = new HashMap<String, String>();

                        user.put("email",email);
                        user.put("firstname",first_name);
                        user.put("lastname", surname);
                        user.put("password", p_word);
                        data.addUser(user,Register.this);



                    }
                }
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    /*

    public void addUser(String firstname, String lastname, String email, String username, String password){

        ContentValues values = new ContentValues();
        values.put(username_login.COL_FIRST_NAME, firstname);
        values.put(username_login.COL_SURNAME, lastname);
        values.put(username_login.COL_EMAIL, email);
        values.put(username_login.COL_USERNAME, username);
        values.put(username_login.COL_PASSWORD, password);
        user.insert(username_login.TABLE_USERS, values);

        
    }
    */
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
    public void onComplete(boolean success) {
        if(success) {
            finish();
        }
        else
        {
            Log.i("Purchase check","bad");
        }
    }
}
