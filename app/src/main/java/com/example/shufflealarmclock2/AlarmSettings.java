package com.example.shufflealarmclock2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class AlarmSettings extends AppCompatActivity {


    // All static final String save file names will be the same as the switches IDs'
    public static final String SETTINGS_SAVE_FILE = "Shuffle_Alarm_Clock_Settings_Save_File";
    public static final String DEVICE_WAKEUP_SWITCH = "setting_switch_wakeup";
    public static final String NOTIFICATION_SWITCH = "setting_switch_notifications";
    // All setting components declared here in order as shown in AlarmSettings.xml
    private SwitchCompat wakeupSwitch;
    private SwitchCompat notificationSwitch;
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

    /**
     * Puts IDs of switches and their correlated names into lists
     */
    private void initializeSwitchLists() {
        switchList.add((SwitchCompat) findViewById(R.id.settings_switch_wakeup));
        switchList.add((SwitchCompat) findViewById(R.id.settings_switch_notifications));
        switchNames.add(DEVICE_WAKEUP_SWITCH);
        switchNames.add(NOTIFICATION_SWITCH);
    }

    /** Goes through Switch lists to have them display the user saved data                          */
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

    /** Changes color of switch. Doesn't save anything                                              */
    public void onOffSettings(SwitchCompat onOff) {
        // Setting color of switch
        if (onOff.isChecked()) {
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

    /** Sets switch position and colors on start                                                    */
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

    /** Saves all modifications made by the user                                                    */
    public void saveSettingChanges(View view) {
        int count = 0;
        // Saving Switch values in loop with previously initialized ArrayLists
        while (count < switchList.size()) {
            saveData.save(switchNames.get(count), switchList.get(count).isChecked());
            count++;
        }
        showToastMessage("Settings saved!");
    }

    /** Discards all modifications made by the user                                                 */
    public void discardSettingChanges(View view) {
        initializeSwitches();
        showToastMessage("All settings reverted");
    }

    /** Displays message at bottom of the screen to provide user with confirmation                  */
    private void showToastMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 350);
        toast.show();
    }

    // All Switch methods below this point____________________________________________________________________________________________________________
    // _______________________________________________________________________________________________________________________________________________

    /**
     * for SwitchCompat "settings_switch_wakeup"
     */
    public void wakeupClick(View view) {
        onOffSettings((SwitchCompat) findViewById(R.id.settings_switch_wakeup));
    }

    /** for SwitchCompat "settings_switch_notifications"                                            */
    public void notificationClick(View view) {
        onOffSettings((SwitchCompat) findViewById(R.id.settings_switch_notifications));
    }
}
