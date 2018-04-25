package com.ptpmcn.orderfood.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.ptpmcn.orderfood.interfaces.TimePickerListener;

import java.util.Calendar;

/**
 * Created by tungts on 12/2/2017.
 */

@SuppressLint("ValidFragment")
public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    TimePickerListener listener;

    @SuppressLint("ValidFragment")
    public TimePicker(TimePickerListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int hourOfDay, int minute){
        String time = "";
        if (hourOfDay < 10){
            time+="0"+hourOfDay;
        } else {
            time += hourOfDay;
        }
        if (minute < 10){
            time+=":0"+minute;
        } else {
            time+= ":" + minute;
        }
        if (listener != null){
            listener.time(time+":00");
        }
    }

}
