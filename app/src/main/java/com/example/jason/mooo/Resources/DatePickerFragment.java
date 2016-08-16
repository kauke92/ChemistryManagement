package com.example.jason.mooo.Resources;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 9/13/15.
 */
public class DatePickerFragment extends DialogFragment implements  DatePickerDialog.OnDateSetListener {
    final Calendar c = Calendar.getInstance();
    DateFormat format = DateFormat.getDateInstance();
    DateFormat intFormat = new SimpleDateFormat("yyMMdd");
    setViewItems item;

    public interface setViewItems
    {
        void setExpiryDate(Map<String, Integer> value);
        void setReceivedDate(Map<String, Integer> value);
        void setFromDate(Map<String, Integer> value);
        void setToDate(Map<String, Integer> value);
    }




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        item = (setViewItems)getActivity();

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dp = new DatePickerDialog(getActivity(), this, year, month, day);

        // Set max date to Today's date
        //dp.getDatePicker().setMaxDate(new Date().getTime());

        return dp;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(year,monthOfYear,dayOfMonth);
        String date = format.format(myCalendar.getTime());

        int dateNum = Integer.valueOf(intFormat.format(myCalendar.getTime()));

        Map<String, Integer> mappy = new HashMap<String, Integer>();

        mappy.put(date, dateNum);

        if(getTag().equalsIgnoreCase("ExpiryDate")) {
            if (item != null) {
                item.setExpiryDate(mappy);
            }
        }
        else if(getTag().equalsIgnoreCase("ReceivedDate"))
        {
            if (item != null) {
                item.setReceivedDate(mappy);
            }
        }
        else if(getTag().equalsIgnoreCase("FromDate"))
        {
            if (item != null) {
                item.setFromDate(mappy);
            }
        }
        else if(getTag().equalsIgnoreCase("ToDate"))
        {
            if (item != null) {
                item.setToDate(mappy);
            }
        }
    }


}
