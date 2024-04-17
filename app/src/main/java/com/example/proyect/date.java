package com.example.proyect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class date {
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
