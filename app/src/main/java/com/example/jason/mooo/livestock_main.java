package com.example.jason.mooo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jason.mooo.Adapter.getCowsAdapter;
import com.example.jason.mooo.Business.Database;
import com.example.jason.mooo.Model.Cow;
import com.example.jason.mooo.Model.MedicineAdministered;

import java.util.ArrayList;
//TODO Remove all the commented lines of code for the progress bar

/*
  Activity where the cows of the specific owner is displayed and also where the user can navigate to administer drug
  to cow activity and also add a cow activity.

  One thing to note is that this activity is implementing the livestock_main_interface which is used in database
  such that the whole activity is not passed as a parameter to the database getCows function but instead just an abstraction
 */

public class livestock_main extends Activity implements livestock_main_Interface {

    Button button_add_new;
    Button search_livestock;
    Button bottom_home;
    Button bottom_medicine;
    Button bottom_livestock;
    Button top_profile;
    public final int ADD_COW = 1000;

    ListView cows;
    ArrayList<Cow> cowInfo;
    ArrayAdapter<Cow> myCows;

    ArrayList<Cow> cowInfo1;
    ArrayAdapter<Cow> auto_adapter;

    ArrayAdapter<MedicineAdministered> cowHistory;
    ArrayList<MedicineAdministered> myHistory;

    Database data;
    TextView top_username;
    ProgressBar bar;
    loading_splash ls;
    ProgressDialog dialog;
    getCowsAdapter adapter;
    String deleteId;

    /*
     This on create function initialises the listview and set an adapter to it. The function getcows in the database is called
     to get all the owner's cows.

     A progress dialog is also shown such that the database has time to retrieve the data and not let the user hang with an
     empty screen for some milliseconds.

     It also sets up a listener for when a cow id is selected in the list view. It will redirect the user to the administer activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.livestock_main);
        top_username = (TextView) findViewById(R.id.text_username);
        data = new Database((this));

        top_username.setText(data.getUsername());
        cows = (ListView) findViewById(R.id.cowList);

        top_profile = (Button) findViewById(R.id.button_profile);

        bottom_medicine = (Button) findViewById(R.id.button_livestockmain_bottom_chemical);
        bottom_home = (Button) findViewById(R.id.button_livestockmain_bottom_home);
        bottom_livestock = (Button) findViewById(R.id.button_livestockmain_bottom_livestock);


        cowInfo = new ArrayList<Cow>();
        myCows = new ArrayAdapter<Cow>(this, android.R.layout.simple_list_item_1, cowInfo);
        data.getCows(myCows, this);

        cowInfo1 = new ArrayList<Cow>();
        auto_adapter = new ArrayAdapter<Cow>(this, android.R.layout.simple_list_item_1, cowInfo1);
        data.getCows(auto_adapter, this);


        cows.setAdapter(myCows);



        bar = (ProgressBar) findViewById(R.id.myProgress);
        bar.setVisibility(View.GONE);
        bar.setMax(100);

        registerForContextMenu(cows);


        search_livestock = (Button) findViewById(R.id.button_search_livestock);
        button_add_new = (Button) findViewById(R.id.button_add_livestock);


        setUpListener();

    }

    /* attach the long press function **/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete_cows, menu);
        inflater.inflate(R.menu.menu_cow_history, menu);
    }

    /* perform delete function **/
   /* perform delete function **/
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        deleteId = cowInfo.get(info.position).toString();


        switch (item.getItemId()) {
            case R.id.delete_cows:
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), info.targetView);
                popupMenu.inflate(R.menu.menu_decision);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.confirm:

                                data.deleteCow(deleteId);
                                Toast.makeText(getBaseContext(), deleteId + " is deleted!", Toast.LENGTH_LONG).show();
                                myCows.notifyDataSetChanged();
                                return true;
                            case R.id.cancel:
                                return false;
                            default:
                                return false;
                        }
                    }

                });
                break;
            case R.id.history_cows:
                PopupMenu Menu = new PopupMenu(getApplicationContext(), info.targetView);
                Intent historyIntent = new Intent(livestock_main.this, Cow_History_Activity.class);
                historyIntent.putExtra("cowID", deleteId);
                startActivity(historyIntent);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return false;
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

    @Override
    public void setProgressbar() {

        dialog = new ProgressDialog(this, R.style.MyTheme);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait");
        dialog.show();

//        bar.setVisibility(View.VISIBLE);
//        bar.bringToFront();
    }

    @Override
    public void finishedDownload() {
//      bar.setVisibility(View.GONE);
        dialog.dismiss();
    }

    @Override
    public void updateProgress(int progress) {

        dialog.setProgress(progress);

    }

    private void setUpListener()

    {


        cows.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cow selectedCow = myCows.getItem(position);
                Intent administer = new Intent(livestock_main.this, template_pages.class);
                administer.putExtra("cowID", selectedCow.getId());
                startActivity(administer);
            }
        });

        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(livestock_main.this, main_puchase.class);
                livestock_main.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(livestock_main.this, livestock_main.class);
                livestock_main.this.startActivity(livestock_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(livestock_main.this, homeScreen.class);
                livestock_main.this.startActivity(home_main_intent);
            }
        });

        top_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent user_info_intent = new Intent(livestock_main.this, User_info.class);
                livestock_main.this.startActivity(user_info_intent);
            }
        });


        bottom_livestock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent livestock_main_intent = new Intent(livestock_main.this, livestock_main.class);
                livestock_main.this.startActivity(livestock_main_intent);
            }
        });


        bottom_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chemicals_main_intent = new Intent(livestock_main.this, main_puchase.class);
                livestock_main.this.startActivity(chemicals_main_intent);
            }
        });

        bottom_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_main_intent = new Intent(livestock_main.this, homeScreen.class);
                livestock_main.this.startActivity(home_main_intent);
            }
        });




        search_livestock.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(livestock_main.this);
                                                    LayoutInflater inflater = livestock_main.this.getLayoutInflater();

                                                    final AutoCompleteTextView auto_cows = new AutoCompleteTextView(livestock_main.this);
                                                    auto_cows.setId(R.id.search_cow_id);
                                                    auto_cows.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                                                                            @Override
                                                                                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                                                                                                boolean found = false;
                                                                                                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {


                                                                                                    for (Cow cow : cowInfo) {
                                                                                                        if (auto_cows.getText().toString().equalsIgnoreCase(cow.getId())) {
                                                                                                            found = true;
                                                                                                            Intent administer = new Intent(livestock_main.this, template_pages.class);
                                                                                                            administer.putExtra("cowID", cow.getId());
                                                                                                            startActivity(administer);

                                                                                                        }


                                                                                                    }

                                                                                                    if (!found) {
                                                                                                        Toast.makeText(livestock_main.this, "No cow found with that ID", Toast.LENGTH_LONG).show();
                                                                                                    }


                                                                                                }
                                                                                                return false;
                                                                                            }
                                                                                        }

                                                    );

                                                        auto_cows.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                                                                         {

                                                                                             @Override
                                                                                             public void onItemClick
                                                                                                     (AdapterView<?> parent, View view,
                                                                                                      int position, long id) {
                                                                                                 Cow selectedCow = auto_adapter.getItem(position);
                                                                                                 Intent administer = new Intent(livestock_main.this, template_pages.class);
                                                                                                 administer.putExtra("cowID", selectedCow.getId());
                                                                                                 startActivity(administer);
                                                                                             }
                                                                                         }

                                                        );
                                                        auto_cows.setAdapter(auto_adapter);
                                                        auto_cows.setInputType(InputType.TYPE_CLASS_TEXT);
                                                        auto_cows.setThreshold(1);
                                                        LinearLayout search_layout = new LinearLayout(livestock_main.this);
                                                        search_layout.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
                                                        search_layout.addView(auto_cows);


                                                        builder.setView(search_layout);
                                                        builder.setPositiveButton("Scan", new DialogInterface.OnClickListener()

                                                                {
                                                                    @Override
                                                                    public void onClick(DialogInterface
                                                                                                dialog, int id) {
                                                                        // sign in the user ...
                                                                    }
                                                                }

                                                        );
                                                        builder.setNegativeButton("Search", new DialogInterface.OnClickListener()

                                                                {
                                                                    @Override
                                                                    public void onClick(DialogInterface
                                                                                                dialog, int id) {

                                                                        for (Cow cow : cowInfo) {
                                                                            if (auto_cows.getText().toString().equalsIgnoreCase(cow.getId())) {

                                                                                Intent administer = new Intent(livestock_main.this, template_pages.class);
                                                                                administer.putExtra("cowID", cow.getId());
                                                                                startActivity(administer);
                                                                            } else {
                                                                                Toast.makeText(livestock_main.this, "No cow found with that ID", Toast.LENGTH_LONG).show();

                                                                            }
                                                                        }

                                                                    }
                                                                }

                                                        );

                                                        builder.show();

                                                    }
                                                }
        );



        button_add_new.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Intent add_new_intent = new Intent(livestock_main.this, add_new_livestock.class);
                                                  livestock_main.this.startActivity(add_new_intent);

                                              }
                                          }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_COW)
        {
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(this,"Cow administered successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
