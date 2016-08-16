package com.example.jason.mooo.Business;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.jason.mooo.Model.Cow;
import com.example.jason.mooo.Model.Drug;
import com.example.jason.mooo.Model.MedicineAdministered;
import com.example.jason.mooo.Model.Purchase;
import com.example.jason.mooo.Model.Stock;
import com.example.jason.mooo.RegisterView;
import com.example.jason.mooo.Resources.AlertButton;
import com.example.jason.mooo.TempPasswordAlert;
import com.example.jason.mooo.homeScreen;
import com.example.jason.mooo.livestock_main_Interface;
import com.example.jason.mooo.loading_splash;
import com.example.jason.mooo.reportGenerator;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


/**
 * Created by Stuart on 8/24/2015.
 */

public class Database {

    static final private String url = "https://mooove-it.firebaseio.com/";
    protected loading_splash ls;
    private Firebase rootRef;
    protected boolean hasAccount;
    String userID;
    Context context;
    Context localContext;

    // Constructor. Requires a page context
    public Database(Context context) {

        Firebase.setAndroidContext(context);
        this.context = context;

        this.localContext = context;

        rootRef = new Firebase(url);
        ls = new loading_splash(new AlertDialog.Builder(context), LayoutInflater.from(context));

        // Check if on login page, if not, then get userID
        if (rootRef.getAuth() != null) {
            userID = rootRef.getAuth().getUid();
        }
    }


    public String getEmail() {
        return rootRef.getAuth().getProviderData().get("email").toString();
    }

    public void changeEmail(final String oldEmail, final String newEmail) {
        //TODO Implement password here
        rootRef.changeEmail(oldEmail, "reset to this", newEmail, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                rootRef.child("users").child(userID).child("email").setValue(newEmail);
                Toast.makeText(context, "Successfully changed email to " + newEmail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                AlertButton box = new AlertButton(context, "An error occurred while changing password");
            }
        });

    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {

        if (rootRef.getAuth() != null) {

            String email = rootRef.getAuth().getProviderData().get("email").toString();
            String split = email.split("@")[0];

            return (Character.toUpperCase(split.charAt(0)) + split.substring(1));

        } else {
            return "Loading";
        }
    }

    public boolean getAuthInfo() {
        if (rootRef.getAuth() != null) {
            Log.i("Authentication Check", rootRef.getAuth().getProviderData().get("email").toString());
            return true;
        } else {
            Log.i("Authentication Check", "User is not logged in");
            return false;
        }
    }

    public void changePassword(String oldPassword, String newPassword) {

        rootRef.changePassword(userID, oldPassword, newPassword, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {

                Toast.makeText(context, "Password has been changed", Toast.LENGTH_SHORT).show();

                ls.cancel();

                Intent test_intent = new Intent(context, homeScreen.class);
                context.startActivity(test_intent);
            }

            @Override
            public void onError(FirebaseError firebaseError) {

                AlertButton box = new AlertButton(context, firebaseError.getMessage());

            }
        });

    }

    public void authWithPassword(final String username, final String password) {
        Log.i("Authentication Log", "test before splash");
        ls.start_splash();


        rootRef.authWithPassword(username, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {


                // Check if the user is logging in with a temporary password.
                if ((boolean) authData.getProviderData().get("isTemporaryPassword")) {
                    // Reset password to one of their choosing
                    TempPasswordAlert tempT = new TempPasswordAlert(context, password);
                    ls.cancel();
                    return;

                }

                Log.i("Authentication Log", userID + " has logged in!! YES");
                ls.cancel();

                Intent test_intent = new Intent(context, homeScreen.class);
                context.startActivity(test_intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.i("Authentication Log", firebaseError.getMessage());
                ls.cancel();
                AlertButton box = new AlertButton(context, "The specified email or password is invalid.");

            }
        });
    }

    public void addUser(final Map<String, String> map, final RegisterView view) {



        rootRef.createUser(map.get("email"), map.get("password"), new Firebase.ValueResultHandler<Map<String, Object>>() {

            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.i("Purchase Check", "Inserted good");
                map.remove("password");
                rootRef.child("users").child(result.get("uid").toString()).setValue(map);
                Toast.makeText(context, "You have created an account", Toast.LENGTH_SHORT).show();
                view.onComplete(true);


            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Log.i("Purchase Check", firebaseError.getMessage());
                Toast.makeText(context, "Account with this email address already exists", Toast.LENGTH_LONG).show();
                view.onComplete(false);
                //Toast.makeText(getApplicationContext(), "You have created an account.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setHasAccount(boolean flag){
        hasAccount = flag;
    }
    public boolean getHasAccount(){
        return hasAccount;
    }


    //TODO IMPLEMENT THE PROGRESS BAR ON RELEVANT FUNCTIONS
    /*Get the cows of the respective owner. A query object is initialised that search for the cows having a key attribute owner
      and a value of userID(which is the currently logged user).

      In on data changed, the array adapter and the interface that the livestock_main implements are passed to this function
      The array adapter will be populated as soon as the data arrives and will be notified.

      The interface is what takes care of showing and hiding the progress bar in that concrete class that implements that
      interface  and also is responsible for updating the progress bar.

    */
    public void getCows(final ArrayAdapter<Cow> listAdapter, final livestock_main_Interface main) {


        if (userID.isEmpty()) {
            return;
        }

        Query query = rootRef.child("cows").child(userID);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listAdapter.add(snapshot.getValue(Cow.class));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public void getCurrentStock(int start, int end) {
        if (userID.isEmpty()) {
            return;
        }

        final ArrayList<Stock> list = new ArrayList<Stock>();

        Query query = rootRef.child("drug_transaction").child(userID).orderByChild("purchaseDate").startAt(end).endAt(start);

        if (query == null) {
            return;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue(Stock.class));
                }
                reportGenerator hi = new reportGenerator(context);
                hi.execute(list);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public void getPurchasesHistory(int start, int end) {
        if (userID.isEmpty()) {
            return;
        }

        final ArrayList<Purchase> list = new ArrayList<Purchase>();

        Query query = rootRef.child("drug_transaction").child(userID).orderByChild("purchaseDate").startAt(end).endAt(start);

        if (query == null) {
            Log.i("PurchaseCheck", "Query is null");
            return;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue(Purchase.class));
                }
                Log.i("PurchaseCheck", "I'm hitting here!!!!");
                reportGenerator hi = new reportGenerator(context);
                hi.execute(list);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getPurchases(final ArrayAdapter<Purchase> listAdapter, int start, int end) {

        if (userID.isEmpty()) {
            return;
        }

        Query query = rootRef.child("drug_transaction").child(userID).startAt(end).endAt(start);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listAdapter.add(snapshot.getValue(Purchase.class));
                }

                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


    //Populates the cows node with the cow model object. On success, a dialog box is popped up
    public void addCow(final String cowID) {

        if (cowID.isEmpty()) {
            Toast.makeText(context, "Cannot add a cow with no ID", Toast.LENGTH_LONG).show();
            return;
        }

        Cow cow = new Cow(cowID, userID);

        rootRef.child("cows").child(userID).child(cowID).setValue(cow, new Firebase.CompletionListener() {

            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Toast.makeText(context, "Error in adding cow. Please try again", Toast.LENGTH_LONG).show();
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    Toast.makeText(context, "Cow has been successfully added", Toast.LENGTH_LONG).show();
                }
            }
        });

        return;
    }

    public void getCowTreatmentHistory(int start, int end) {

        final ArrayList<MedicineAdministered> list = new ArrayList<>();
        Query query = rootRef.child("cow_treatment").child(userID).orderByChild("treatmentDate").startAt(end).endAt(start);

        if (query == null) {
            return;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    list.add(snapshot.getValue(MedicineAdministered.class));
                }

                reportGenerator hi = new reportGenerator(context);
                hi.execute(list);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getCowHistory(final ArrayAdapter<MedicineAdministered> listAdapter, String cowID) {

        Query query = rootRef.child("cow_treatment").child(userID).orderByChild("cowID").startAt(cowID).endAt(cowID);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listAdapter.add(snapshot.getValue(MedicineAdministered.class));
                    Log.i("Purchase Check", snapshot.getValue(MedicineAdministered.class).getCowID());
                }

                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void addDrug(final String drugName, int withholdingMilk, int withholdingSlaughter) {

        if (drugName.isEmpty() || userID.isEmpty()) {
            AlertButton box = new AlertButton(context, "Something went wrong");
            return;
        }
        Firebase insertDrug = rootRef.child("drug").child(userID);

        final Drug drug = new Drug(drugName, withholdingMilk, withholdingSlaughter);

        insertDrug.push().setValue(drug, new Firebase.CompletionListener() {

            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    AlertButton box = new AlertButton(context, firebaseError.getMessage());
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    Toast.makeText(context, "Successfully added in the drug " + drug.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;
    }

    public void getDrugs(final ArrayAdapter<Drug> drugs) {

        Firebase myDrugs = rootRef.child("drug").child(userID);

        myDrugs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Drug drug = snapshot.getValue(Drug.class);
                    drugs.add(drug);
                }
                drugs.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void resetPassword(final String email) {

        if (email.isEmpty()) {
            return;
        }

        if (!isNetworkAvaliable(localContext)) {
            AlertButton box = new AlertButton(context, "Offline");
            return;
        }

        rootRef.resetPassword(email, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                AlertButton box = new AlertButton(context, "If the email exists, a temporary password and instruction on how to proceed have been sent to you");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                AlertButton box = new AlertButton(context, "If the email exists, a temporary password and instruction on how to proceed have been sent to you");

            }
        });
    }


    public void administerDrug(final Purchase purchase, final String cowID, final int date, final int quantity, final int period) {

        rootRef.child("drug_transaction").child(userID).child(purchase.getId()).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if (currentData.child("amountLeft").getValue(Integer.class) < quantity) {
                    Log.i("Purchase Check", "It get stopped");
                    return Transaction.abort();
                } else {
                    // update stuff here

                    Query query = rootRef.child("drug").child(userID).orderByChild("name").equalTo(purchase.getDrugName()).limitToFirst(1);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            Drug drug = null;
                            for (DataSnapshot data : snapshot.getChildren()) {

                                drug = data.getValue(Drug.class);
                                insertAdminister(cowID, drug, date, quantity, period, purchase);
                                deduceAmount(purchase, quantity);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            AlertButton box = new AlertButton(context, "An unknown error occured");

                        }
                    });
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (!committed) {
                    Toast.makeText(context, "Insufficient amount in batch to administer", Toast.LENGTH_LONG).show();
                }
            }
        });

        return;
    }

    private void deduceAmount(final Purchase purchase, final int amount) {
        rootRef.child("drug_transaction").child(userID).orderByChild("drugName").equalTo(purchase.getDrugName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot batch : snapshot.getChildren()) {
                    String curBatch = batch.child(("batch")).getValue(String.class);

                    if(curBatch.equals(purchase.getBatch())) {


                        int newAmount = (batch.child("amountLeft").getValue(Integer.class) - amount);
                        rootRef.child("drug_transaction").child(userID).child(batch.getKey()).child("amountLeft").setValue(newAmount);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i("Purchase Check", firebaseError.getMessage());
                Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void insertAdminister(final String cowID, final Drug drug, final int date, final int quantity, final int period, Purchase purchase) {

        Firebase insertAdminister = rootRef.child("cow_treatment").child(userID);

        MedicineAdministered record = new MedicineAdministered(cowID, drug, date, quantity, period);

        insertAdminister.push().setValue(record, date, new Firebase.CompletionListener() {

            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {

                    Toast.makeText(context, "An error occurred. Please try again", Toast.LENGTH_LONG).show();
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    Toast.makeText(context, "Successfully administered medicine to " + cowID, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteCow(final String cowID) {
        if (cowID.isEmpty()) {
            return;
        }

        rootRef.child("cows").child(userID).child(cowID).setValue(null, new Firebase.CompletionListener() {

            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    AlertButton box = new AlertButton(context, firebaseError.getMessage());

                    System.out.println("Something went wrong" + firebaseError.getMessage());
                } else {
                    System.out.println("Cow " + cowID + " has been deleted");
                    Toast.makeText(context, "Successfully deleted " + cowID, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;

    }


    public void deleteMedicine(final String medicine) {
        if (medicine.isEmpty()) {
            return;
        }

        Query query = rootRef.child("drug").child(userID).orderByChild("name").startAt(medicine).endAt(medicine);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    item.getRef().removeValue();
                    Log.i("Purchase Check", item.getKey());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


    public void addPurchase(Drug drug, String purchasePlace, int expiryDate, int receivedDate, String signedBy, String batch, int amount) {

        if (purchasePlace.isEmpty() || signedBy.isEmpty() || userID.isEmpty()) {
            AlertButton box = new AlertButton(context, "Something went wrong");
            return;
        }

        Firebase insertPurchase = rootRef.child("drug_transaction").child(userID);

        final Purchase purchase = new Purchase(drug.getName(), purchasePlace, expiryDate, receivedDate, signedBy, batch, amount);

        Log.i("Purchase Check", String.valueOf(purchase.getAmount()));

        insertPurchase.push().setValue(purchase, new Firebase.CompletionListener() {


            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    AlertButton box = new AlertButton(context, firebaseError.getMessage());
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    firebase.child("id").setValue(firebase.getKey());

                    Log.i("Purchase Check", firebase.getKey());
                    Toast.makeText(context, "Purchase succesffully added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

    public void adminsterList(final ArrayAdapter<Purchase> listAdapter) {

        if (userID.isEmpty()) {
            return;
        }

        // Get today's date for comparison with expiry dates
        Calendar myCalendar = Calendar.getInstance();
        DateFormat intFormat = new SimpleDateFormat("yyMMdd");
        int today = Integer.valueOf(intFormat.format(myCalendar.getTime()));

        Firebase purchaseList = rootRef.child("drug_transaction").child((userID));
        Query query = purchaseList.orderByChild("expireDate").startAt(today);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long size = dataSnapshot.getChildrenCount();
                listAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listAdapter.add(snapshot.getValue(Purchase.class));

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void logout() {
        rootRef.unauth();
    }


    public void getAllPurchases(final ArrayAdapter<Purchase> listAdapter) {

        if (userID.isEmpty()) {
            return;
        }

        Query query = rootRef.child("drug_transaction").child(userID);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    listAdapter.add(snapshot.getValue(Purchase.class));
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}