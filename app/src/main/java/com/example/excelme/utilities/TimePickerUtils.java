/*This class is used for retrieving data from timePicker in both old and new versions
 * File: TimePickerUtils.java
 * */

package com.example.excelme.utilities;

import android.os.Build;

import android.widget.TimePicker;

public class TimePickerUtils {

    public static int getTimePickerHour(TimePicker tp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return tp.getHour();
        } else {
            return tp.getCurrentHour();
        }
    }

    public static int getTimePickerMinute(TimePicker tp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return tp.getMinute();
        } else {
            return tp.getCurrentMinute();
        }
    }



}
