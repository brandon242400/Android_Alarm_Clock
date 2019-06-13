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


    private SaveData saveData;
    private EditAlarm edit;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("Brandon", "Creating TimePicker fragment thing.");
        return new TimePickerDialog(getActivity(), this, 12, 0, DateFormat.is24HourFormat(getActivity()));
    }

    TimePickerFragment(SaveData saveData, EditAlarm edit) {
        this.saveData = saveData;
        this.edit = edit;
    }

    /**
     * Puts information from timePicker into temporary save locations in case
     * user decides to use them.
     * After saving, it calls method in EditAlarm to display the new time under the current.
     *
     * @param timePicker
     * @param i          Hours of clock time (0-23)
     * @param i1         Minutes of clock time
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Log.d("Brandon", "Made it to onTimeSet. Hour = " + i + ", Minute = " + i1);
        saveData.save(SaveData.MAYBE_HOUR_REFERENCE, i);
        saveData.save(SaveData.MAYBE_MINUTE_REFERENCE, i1);
        String timeString;
        if (i > 12) {
            timeString = (i - 12) + ":";
            if (i1 < 10) {
                timeString += "0" + i1 + " p.m.";
            } else {
                timeString += i1 + " p.m.";
            }
        } else {
            timeString = i + ":";
            if (i1 < 10) {
                timeString += "0" + i1 + " a.m.";
            } else {
                timeString += i1 + " a.m.";
            }
        }
        saveData.save(SaveData.MAYBE_TIME_STRING_REFERENCE, timeString);
        Log.d("Brandon", "Saved all data as 'maybe'. Waiting for save.");
        edit.showNewTime();
    }
}
