package com.example.shufflealarmclock2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditAlarm extends AppCompatActivity {

    Integer hour;
    Integer minute;
    boolean AM;
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Brandon", "In editAlarm onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
        saveData = new SaveData(getSharedPreferences(MainActivity.SAVE_FILE, Context.MODE_PRIVATE));
        Log.d("Brandon", "Calling getSavedTime() method");
        getSavedTime();
    }

    private void getSavedTime() {                             // Finish
        Log.d("Brandon", "Beginning getSavedTime()");
        TextView view = findViewById(R.id.edit_alarm_time_display);
        String savedTime = saveData.getStr(SaveData.TIME_STRING_REFERENCE);
        view.setText(savedTime);
        Log.d("Brandon", "Finished getSavedTime()");
    }

    /**
     * Saves all the user entered data when they press the save button
     *
     * @param view
     */
    public void saveTime(View view) {
        // So far just saves the time. Will add more later.
        hour = saveData.getInt(SaveData.MAYBE_HOUR_REFERENCE);
        Log.d("Brandon", hour.toString());
        minute = saveData.getInt(SaveData.MAYBE_MINUTE_REFERENCE);
        String timeSTR, minuteSTR;
        if (minute < 10) {
            minuteSTR = "0" + minute.toString();
        } else {
            minuteSTR = minute.toString();
        }
        timeSTR = hour.toString() + ":" + minuteSTR;
        saveData.save(SaveData.HOUR_REFERENCE, hour);
        saveData.save(SaveData.MINUTE_REFERENCE, minute);
        saveData.save(SaveData.TIME_STRING_REFERENCE, timeSTR);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFrag = new TimePickerFragment();
        newFrag.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
