package com.ptpmcn.orderfood.utils;

import android.util.Log;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tungts on 11/25/2017.
 */

public class TimeDistanceUtils {

    public static String changePatternDate(String s){
        SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyy:MM:dd");
        SimpleDateFormat simpleDate2= new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = simpleDate1.parse(s);
            return simpleDate2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertToLong(String s){
        String dateTime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        try {
            Date date = simpleDateFormat.parse(s);
            dateTime = String.valueOf(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String getDateEarliestPossibleDeliveryTime(){
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (true){
            date = sdf.format(new Date().getTime());
        }
        return date;
    }

    public static boolean checkDate(String s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
        try {
            Date date = simpleDateFormat.parse(s);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean checkTime(String s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm:ss");
        try {
            long time = simpleDateFormat.parse(s).getTime();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String convertDatePattern(String date){
        String date2 = null;
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        try {
            Date d = format1.parse(date); Log.e("tungts",date + " ");
            date2 = format2.format(d);Log.e("tungts",date2 + " ");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    public static boolean checkDateWithCurrentDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
        try {
            Date date = simpleDateFormat.parse(s);
            if (date.before(Calendar.getInstance().getTime())){
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getEndTime(String start_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            Date start = simpleDateFormat.parse(start_time);
            Date end = new Date(start.getTime()+5000*60*60);
            return simpleDate.format(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStartTime(String start_time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        try {
            Date start = simpleDateFormat.parse(start_time);
            return simpleDate.format(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
