/*This class is responsible for setting current day
 * File: Day.java
 * Author: Serdiuk Andrii
 * */


package com.example.excelme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Day {

    //private static Locale locale = Locale.getDefault();
    private static DateFormat formatter = new SimpleDateFormat("EEEE,\nMMMM, d", Locale.US);

    /**Currently returning date in US format in English
     */
    public static String getCurrentDateString() {
        return formatter.format(new Date());
    }


}