package com.example.shufflealarmclock2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class AlarmActive extends AppCompatActivity {

    private AlarmManager alarmManager;
    private SaveData saveData;
    private Intent intent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_active);

        // Setting global variables needed to set a scheduled broadcast
        intent = new Intent(this, AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Setting 'saveData' to retrieve stored data
        SharedPreferences shared = getDefaultSharedPreferences(this);
        saveData = new SaveData(shared);
    }
}
