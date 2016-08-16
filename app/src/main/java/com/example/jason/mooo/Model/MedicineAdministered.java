package com.example.jason.mooo.Model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Stuart on 15/09/2015.
 */
public class MedicineAdministered {

    String cowID;
    String drugName;
    int treatmentDate;
    int quantity;
    int withholdingSlaughter;
    int withholdingMilk;
    int length;

    public MedicineAdministered() {

    }

    public MedicineAdministered(String cowID, Drug drug, int date, int quantity, int length) {

        Calendar cal = Calendar.getInstance();

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyMMdd");

        Date dater = null;
        try {

            dater = originalFormat.parse(Integer.toString(date));

            cal.setTime(dater);
            cal.add(Calendar.DATE,drug.getWithholdingMilk());
            withholdingMilk = Integer.valueOf(originalFormat.format(cal.getTime()));

            cal = Calendar.getInstance();
            cal.setTime(dater);
            cal.add(Calendar.DATE, drug.getWithholdingSlaughter());
            withholdingSlaughter = Integer.valueOf(originalFormat.format(cal.getTime()));

        } catch (Exception e) {
            Log.i("Purchase Check",e.getMessage());
        }

        drugName = drug.getName();
        this.cowID = cowID;
        this.treatmentDate = date;
        this.quantity = quantity;
        this.length = length;


    }

    public String toString() {
        return "Cow ID: " + cowID + " - Date: " + String.valueOf(treatmentDate) + " - Amount: " + String.valueOf(quantity);
    }

    public String getCowID() { return cowID; }

    public String getDrugName() { return drugName; }

    public int getTreatmentDate() { return treatmentDate; }

    public int getQuantity() { return quantity; }

    public int getWithholdingMilk() { return withholdingMilk; }

    public int getWithholdingSlaughter() { return withholdingSlaughter; }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

}
