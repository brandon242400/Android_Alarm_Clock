package com.example.shufflealarmclock2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class AlarmSettings extends AppCompatActivity {


    // All static final String save file names will be the same as the switches IDs'
    public static final String SETTINGS_SAVE_FILE = "Shuffle_Alarm_Clock_Settings_Save_File";
    public static final String DEVICE_WAKEUP_SWITCH = "setting_switch_wakeup";
    // All setting components declared here in order as shown in AlarmSettings.xml
    private SwitchCompat wakeupSwitch;
    // Other variables declared below this point
    private SaveData saveData;
    private ArrayList<SwitchCompat> switchList;
    private ArrayList<String> switchNames;

    //                OVERVIEW OF METHODS
    // onCreate()
    // initializeSwitchLists()
    // initializeSwitches()
    // onOffSettings(Switch, saveName)
    // setSwitchSettings(Switch, saveName)
    // saveSettingChanges()
    // discardSettingChanges()
    // Plus methods for each switch being clicked.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);
        saveData = new SaveData(getSharedPreferences(SETTINGS_SAVE_FILE, Context.MODE_PRIVATE));
        switchList = new ArrayList<>();
        switchNames = new ArrayList<>();
        initializeSwitchLists();
        initializeSwitches();
    }

    private void initializeSwitchLists() {
        switchList.add((SwitchCompat) findViewById(R.id.settings_switch_wakeup));
        switchNames.add("setting_switch_wakeup");
    }

    private void initializeSwitches() {
        if (switchList.size() != switchNames.size()) {
            throw new Resources.NotFoundException("Switch objects and save names don't match");
        }
        int count = 0;
        while (count < switchList.size()) {
            setSwitchSettings(switchList.get(count), switchNames.get(count));
            count++;
        }
    }

    public void onOffSettings(SwitchCompat onOff, String saveName) {
        saveData.save(saveName, onOff.isChecked());
        if (onOff.isChecked()) {
            // Setting color of switch
            int thumbColor = Color.argb(255, 100, 200, 100);
            int trackColor = Color.argb(150, 100, 175, 100);
            onOff.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            onOff.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
        } else {
            int thumbColor = Color.argb(255, 80, 80, 80);
            int trackColor = Color.argb(150, 130, 130, 130);
            onOff.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            onOff.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
        }
    }

    private void setSwitchSettings(SwitchCompat onOff, String saveName) {
        boolean on = saveData.getBool(saveName);
        onOff.setChecked(on);
        if (on) {
            int thumbColor = Color.argb(255, 100, 200, 100);
            int trackColor = Color.argb(150, 100, 175, 100);
            onOff.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            onOff.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
        } else {
            int thumbColor = Color.argb(255, 80, 80, 80);
            int trackColor = Color.argb(150, 130, 130, 130);
            onOff.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.MULTIPLY);
            onOff.getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
        }
    }

    public void saveSettingChanges(View view) {

    }

    public void discardSettingChanges(View view) {

    }

    // All Switch methods below this point ___________________________________________________________________________________________________________

    /**
     * onClick method for "DEVICE WAKE UP" switch option
     */
    public void wakeupClick(View view) {
        onOffSettings((SwitchCompat) findViewById(R.id.settings_switch_wakeup), DEVICE_WAKEUP_SWITCH);
    }
}
