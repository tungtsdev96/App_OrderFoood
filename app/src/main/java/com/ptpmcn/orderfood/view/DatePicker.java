package com.ptpmcn.orderfood.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.ptpmcn.orderfood.interfaces.DatePickerListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tungts on 12/2/2017.
 */

@SuppressLint("ValidFragment")
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    DatePickerListener listener;

    public DatePicker(DatePickerListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        month++;
        String date = ""+year+":";
        if (month<10){
            date+="0"+month;
        } else {
            date+= month;
        }
        if (day < 10){
            date+=":0"+day;
        } else {
            date+=":"+day;
        }
        listener.date(date);
    }
}
