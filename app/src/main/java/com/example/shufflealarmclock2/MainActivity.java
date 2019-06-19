package com.example.shufflealarmclock2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.io.Serializable;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Serializable {


    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    protected final static String AM_REFERENCE = "Main_AM_Variable";
    protected final static String SAVE_FILE = "Shuffle_Alarm_Clock_Save_File";
    SharedPreferences shared;
    AlarmManager alarmMngr;
    Intent alarmIntent;
    PendingIntent pendingIntent;
    Integer timeHour;
    Integer timeMinute;
    boolean AM;
    SaveData saveData;
    SaveData settingSaveData;

    // OVERVIEW OF METHODS
    /*  onCreate()
        editAlarm() - EditAlarm.java intent
        editSettings() - AlarmSettings.java intent
        getSaved()
        onResume()
        onStart()
        onOff()
        setSwitchCheck()
        setTimeUntilAlarm()
        */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        shared = getSharedPreferences(SAVE_FILE, Context.MODE_PRIVATE);
        saveData = new SaveData(shared);
        SharedPreferences settingsShared = getSharedPreferences(AlarmSettings.SETTINGS_SAVE_FILE, Context.MODE_PRIVATE);
        settingSaveData = new SaveData(settingsShared);
        alarmMngr = (AlarmManager) getSystemService(ALARM_SERVICE);
        getSaved();
        setSwitchCheck();
    }

    /**
     * Starts the activity to change the alarm status
     *
     * @param view
     */
    public void editAlarm(View view) {
        Intent intent = new Intent(this, EditAlarm.class);
        startActivity(intent);
    }

    /**
     * Starts the settings activity
     *
     * @param view
     */
    public void editSettings(View view) {
        Intent intent = new Intent(this, AlarmSettings.class);
        startActivity(intent);
    }

    /**
     * Method collects saved alarm data from saved file
     */
    private void getSaved() {
        TextView view = findViewById(R.id.second_layout_text);
        String temp = saveData.getStr(SaveData.TIME_STRING_REFERENCE);
        view.setText(temp);
        this.timeHour = saveData.getInt(SaveData.HOUR_REFERENCE);
        this.timeMinute = saveData.getInt(SaveData.MINUTE_REFERENCE);
        setTimeUntilAlarm();
    }

    /**
     * Added getSaved() to update display when activity is resumed and also making sure the
     */
    @Override
    protected void onResume() {
        getSaved();
        // Deleting last alarm an reapplying it to adhere to any possible changed settings
        alarmMngr.cancel(pendingIntent);
        Log.d("Brandon", "Deleting any possible remaining intent to reset with new settings.");
        // Resetting alarm
        SwitchCompat onOff = findViewById(R.id.main_switch_onOff);
        setAlarm(onOff);
        // Continuing to normal onResume() functions
        super.onResume();
    }

    /**
     * Saves the status of the switch
     *
     * @param view
     */
    public void onOff(View view) {
        SwitchCompat onOff = findViewById(R.id.main_switch_onOff);
        // Setting/Disabling alarm depending on switch position
        setAlarm(onOff);
        saveData.save(SaveData.ON_OFF_SWITCH_MAIN, onOff.isChecked());
        // Setting text and switch color
        if (onOff.isChecked()) {
            onOff.setText(getString(R.string.home_toggle_on));
            onOff.setTextColor(getResources().getColor(R.color.MyGreen));
            int thumbColor = Color.argb(255, 100, 200, 100);
            int trackColor = Color.argb(150, 100, 175, 100);
            onOff.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            onOff.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
        }
        else {
            onOff.setText(getString(R.string.home_toggle_off));
            onOff.setTextColor(getResources().getColor(R.color.Black));
            int thumbColor = Color.argb(255, 80, 80, 80);
            int trackColor = Color.argb(150, 130, 130, 130);
            onOff.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            onOff.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
        }
        setTimeUntilAlarm();
    }

    /**
     * Sets the switch to its last position on Activity creation
     */
    private void setSwitchCheck() {
        SwitchCompat onOff = findViewById(R.id.main_switch_onOff);
        boolean on = saveData.getBool(SaveData.ON_OFF_SWITCH_MAIN);
        onOff.setChecked(on);
        if (on) {
            onOff.setText(getString(R.string.home_toggle_on));
            onOff.setTextColor(getResources().getColor(R.color.MyGreen));

        }
        else {
            onOff.setText(getString(R.string.home_toggle_off));
            onOff.setTextColor(getResources().getColor(R.color.Black));
        }
    }

    public void setAlarm(SwitchCompat onOff) {
        if (onOff.isChecked()) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, saveData.getInt(SaveData.HOUR_REFERENCE));
            cal.set(Calendar.MINUTE, saveData.getInt(SaveData.MINUTE_REFERENCE));
            if (settingSaveData.getBool(AlarmSettings.DEVICE_WAKEUP_SWITCH)) {
                alarmMngr.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                Log.d("Brandon", "Set alarm to wake up");
            }
            else {
                alarmMngr.setExact(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
                Log.d("Brandon", "Set alarm to NOT wake up");
            }
        }
        else {
            alarmMngr.cancel(pendingIntent);
        }
    }

    /**
     * Displays the "Time until next alarm" message
     */
    private void setTimeUntilAlarm() {
        TextView view = findViewById(R.id.main_timeTillAlarm);
        if (!saveData.getBool(SaveData.ON_OFF_SWITCH_MAIN)) {
            view.setText(R.string.alarm_off_text);
        }
        else {
            Calendar cal = Calendar.getInstance();
            int currentHour = cal.getTime().getHours();
            int currentMinute = cal.getTime().getMinutes();
            int hoursLeft, minutesLeft;
            if (timeHour >= currentHour) {
                hoursLeft = timeHour - currentHour;
            }
            else {
                hoursLeft = 24 + (timeHour - currentHour);
            }
            minutesLeft = timeMinute - currentMinute;
            if (minutesLeft < 0) {
                minutesLeft += 60;
                hoursLeft--;
            }
            String message = getResources().getString(R.string.time_left) + "\n";
            message += hoursLeft + " ";
            if (hoursLeft == 1) {
                message += getResources().getString(R.string.hour);
            }
            else {
                message += getResources().getString(R.string.hours);
            }
            message += " and " + minutesLeft + " ";
            if (minutesLeft == 1) {
                message += getResources().getString(R.string.minute);
            }
            else {
                message += getResources().getString(R.string.minutes);
            }
            view.setText(message);
        }
    }
}
