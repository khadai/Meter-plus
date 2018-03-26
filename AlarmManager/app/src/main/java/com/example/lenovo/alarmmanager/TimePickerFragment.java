package com.example.lenovo.alarmmanager;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minule = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),(TimePickerDialog.OnTimeSetListener) getActivity(),hour,minule, DateFormat.is24HourFormat(getActivity()));
    }
}
