/*This class creates a time picker in another window and notifies the system about
* user`s selection
 * File: TimePicker.java
 * Author: Serdiuk Andrii
 * */


package com.example.excelme;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class TimePicker extends DialogFragment  {

    private TimePickerDialog.OnTimeSetListener listener;

    public TimePicker(TimePickerDialog.OnTimeSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(activity, listener,
                hour, minute, DateFormat.is24HourFormat(activity));
    }
}
