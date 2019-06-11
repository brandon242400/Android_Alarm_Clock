package com.example.shufflealarmclock2;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {


    private SaveData save;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("Brandon", "Creating TimePicker fragment thing.");
        return new TimePickerDialog(getActivity(), this, 12, 0, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Log.d("Brandon", "Made it to onTimeSet.");
        save.save(SaveData.MAYBE_HOUR_REFERENCE, i);
        save.save(SaveData.MAYBE_MINUTE_REFERENCE, i1);
        Log.d("Brandon", "Saved all data as 'maybe'. Waiting for save.");
    }
}
