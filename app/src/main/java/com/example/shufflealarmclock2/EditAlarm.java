package com.example.shufflealarmclock2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditAlarm extends AppCompatActivity {

    Integer hour;
    Integer minute;
    boolean AM;
    TextView timeView;
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Brandon", "In editAlarm onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        saveData = new SaveData(getSharedPreferences(MainActivity.SAVE_FILE, Context.MODE_PRIVATE));
        timeView = findViewById(R.id.edit_alarm_time_display);
        getSavedTime();
    }

    private void getSavedTime() {                             // Finish
        Log.d("Brandon", "Beginning getSavedTime()");
        String savedTime = saveData.getStr(SaveData.TIME_STRING_REFERENCE);
        timeView.setText(savedTime);
    }

    /**
     * Saves all the user entered data when they press the save button
     *
     * @param view
     */
    public void saveTime(View view) {
        // So far just saves the time. Will add more later.
        hour = saveData.getInt(SaveData.MAYBE_HOUR_REFERENCE);
        minute = saveData.getInt(SaveData.MAYBE_MINUTE_REFERENCE);
        String timeSTR, minuteSTR;
        if (minute < 10) {
            minuteSTR = "0" + minute.toString();
        } else {
            minuteSTR = minute.toString();
        }
        if (hour > 12) {
            hour -= 12;
            minuteSTR += " p.m.";
        } else {
            minuteSTR += " a.m.";
        }
        timeSTR = hour.toString() + ":" + minuteSTR;
        saveData.save(SaveData.HOUR_REFERENCE, hour);
        saveData.save(SaveData.MINUTE_REFERENCE, minute);
        saveData.save(SaveData.TIME_STRING_REFERENCE, timeSTR);
        getSavedTime();
        saveToast("Alarm Settings Saved :D");
        TextView newTime = findViewById(R.id.edit_alarm_time_display_new);
        newTime.setVisibility(View.INVISIBLE);
    }

    private void saveToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 350);
        toast.show();
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFrag = new TimePickerFragment(saveData, this);
        newFrag.show(getSupportFragmentManager(), "timePicker");
    }

    public void showNewTime() {
        TextView view = findViewById(R.id.edit_alarm_time_display_new);
        String current = "New time: " + saveData.getStr(SaveData.MAYBE_TIME_STRING_REFERENCE);
        view.setText(current);
        view.setVisibility(View.VISIBLE);
    }

    public void discardChanges(View view) {
        Intent intent = new Intent(this, EditAlarm.class);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        saveToast("Changes Discarded");
    }
}
