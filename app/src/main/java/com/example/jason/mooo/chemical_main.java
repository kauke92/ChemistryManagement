package com.example.jason.mooo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.Cow;
import com.example.jason.mooo.Model.Drug;

import java.util.ArrayList;

public class chemical_main extends AppCompatActivity {

    Button button_add_new;
    Button bottom_livestock;
    Button bottom_chemical;
    Button bottom_home;
    Button top_profile;
    TextView top_username;
    ListView chemicals;
    private ArrayAdapter<Drug> drugs;
    private ArrayList<Drug> allDrugs;
    Database data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemical_main);
        top_username = (TextView) findViewById(R.id.text_username);
        data = new Database(this);

        top_username.setText(data.getUsername());
        top_profile = (Button) findViewById(R.id.button_profile);
        bottom_chemical = (Button) findViewById(R.id.button_medicine_bottom_medicine);
        bottom_home = (Button) findViewById(R.id.button_home_bottom_medicine);
        bottom_livestock = (Button) findViewById(R.id.button_livestock_bottom_medicine);

        chemicals = (ListView)findViewById(R.id.chemicals);
        allDrugs = new ArrayList<Drug>();
        drugs = new ArrayAdapter<Drug>(this,android.R.layout.simple_list_item_1,allDrugs);
        chemicals.setAdapter(drugs);
        data.getDrugs(drugs);

        registerForContextMenu(chemicals);
        button_add_new = (Button) findViewById(R.id.button_add_chemical);
        button_add_new.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Intent add_new_intent = new Intent(chemical_main.this, add_new_chemical.class);
                                                  chemical_main.this.startActivity(add_new_intent);
                                              }
                                          }
        );

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(chemical_main.this, User_info.class);
                chemical_main.this.startActivity(user_info_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(chemical_main.this, livestock_main.class);
                chemical_main.this.startActivity(livestock_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(chemical_main.this, homeScreen.class);
                chemical_main.this.startActivity(home_main_intent);
            }
        });


        bottom_chemical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(chemical_main.this, main_puchase.class);
                chemical_main.this.startActivity(chemicals_main_intent);
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_template_pages, menu);
        return true;
    }

    /* attach the long press function **/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_medicine, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final String deleteId = allDrugs.get(info.position).toString();
        switch (item.getItemId()) {
            case R.id.delete_medicine:
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), info.targetView);
                popupMenu.inflate(R.menu.menu_decision);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.confirm:

                                data.deleteMedicine(deleteId);
                                Toast.makeText(getBaseContext(), deleteId + " has been deleted.", Toast.LENGTH_LONG).show();
                                drugs.notifyDataSetChanged();
                                Intent chemical_intent = new Intent(chemical_main.this, chemical_main.class);
                                startActivity(chemical_intent);
                                return true;
                            case R.id.cancel:
                                return false;
                            default:
                                return false;
                        }
                    }
                });

            default:
                return super.onContextItemSelected(item);
        }
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
