package com.example.shufflealarmclock2;

import android.content.Intent;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alarm);
    }

    private String getSavedTime() {
        TextView view = findViewById(R.id.second_layout_text);
        String savedTime;
        return "03:03";
    }

    public void saveTime(View view) {
        hour = saveData.getInt(SaveData.MAYBE_HOUR_REFERENCE);
        minute = saveData.getInt(SaveData.MAYBE_MINUTE_REFERENCE);
        saveData.save(SaveData.HOUR_REFERENCE, hour);
        saveData.save(SaveData.MINUTE_REFERENCE, minute);
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
