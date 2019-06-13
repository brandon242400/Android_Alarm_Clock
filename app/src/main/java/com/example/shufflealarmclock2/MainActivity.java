package com.example.shufflealarmclock2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    protected final static String AM_REFERENCE = "Main_AM_Variable";
    protected final static String SAVE_FILE = "Shuffle_Alarm_Clock_Save_File";
    SharedPreferences shared;
    Integer timeHour;
    Integer timeMinute;
    boolean AM;
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shared = getSharedPreferences(SAVE_FILE, Context.MODE_PRIVATE);
        Log.d("Brandon", "Creating Main");
        saveData = new SaveData(shared);
        getSaved();
        setSwitchCheck();
    }

    /**
     * Starts the activity to change the alarm status
     *
     * @param view
     */
    public void editAlarm(View view) {
        Log.d("Brandon", "Starting editAlarm Intent and Activity");
        Intent intent = new Intent(this, EditAlarm.class);
        intent.putExtra(HOUR_REFERENCE, timeHour);
        intent.putExtra(MINUTE_REFERENCE, timeMinute);
        intent.putExtra(AM_REFERENCE, AM);
        startActivity(intent);
    }

    /**
     * Starts the settings activity
     *
     * @param view
     */
    public void editSettings(View view) {
        Log.d("Brandon", "Starting editSettings Intent and Activity");
        Intent intent = new Intent(this, AlarmSettings.class);
        startActivity(intent);
    }

    /**
     * Method collects saved alarm data from saved file
     */
    private void getSaved() {
        Log.d("Brandon", "Beginning Main.getSaved()");
        TextView view = findViewById(R.id.second_layout_text);
        String temp = saveData.getStr(SaveData.TIME_STRING_REFERENCE);
        view.setText(temp);
        Log.d("Brandon", "Finished Main.getSaved()");
    }

    @Override
    protected void onResume() {
        getSaved();
        super.onResume();
    }

    /**
     * Saves the status of the switch
     *
     * @param view
     */
    public void onOff(View view) {
        Switch onOff = findViewById(R.id.main_switch_onOff);
        saveData.save(SaveData.ON_OFF_SWITCH_MAIN, onOff.isChecked());
        if (onOff.isChecked()) {
            onOff.setText("ON   ");
            onOff.setTextColor(getResources().getColor(R.color.MyGreen));
        } else {
            onOff.setText("OFF   ");
            onOff.setTextColor(getResources().getColor(R.color.Black));
        }
    }

    /**
     * Sets the switch to its last position on Activity creation
     */
    private void setSwitchCheck() {
        Switch onOff = findViewById(R.id.main_switch_onOff);
        boolean on = saveData.getBool(SaveData.ON_OFF_SWITCH_MAIN);
        onOff.setChecked(on);
        if (on) {
            onOff.setText("ON   ");
            onOff.setTextColor(getResources().getColor(R.color.MyGreen));
        } else {
            onOff.setText("OFF   ");
            onOff.setTextColor(getResources().getColor(R.color.Black));
        }
    }
}
