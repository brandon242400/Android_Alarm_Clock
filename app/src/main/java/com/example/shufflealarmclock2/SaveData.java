package com.example.shufflealarmclock2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;


public class SaveData extends AppCompatActivity implements Serializable {


    // Constants used for data saving and retrieval
    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    protected final static String TIME_STRING_REFERENCE = "Main_Time(str)_Reference";
    protected final static String MAYBE_MINUTE_REFERENCE = "Possible_Minute_Reference";
    protected final static String MAYBE_TIME_STRING_REFERENCE = "Possible_Time_String_Reference";
    protected final static String MAYBE_HOUR_REFERENCE = "Possible_Hour_Reference";
    protected final static String ON_OFF_SWITCH_MAIN = "Main_OnOffSwitch_Bool";
    protected final static String SNOOZE_TIME_DEFAULT = "Alarm_Snooze_Default_Time";
    protected final static String SNOOZE_TIME_SMALL = "Alarm_Snooze_Small";
    protected final static String SNOOZE_TIME_MED = "Alarm_Snooze_Med";
    protected final static String SNOOZE_TIME_BIG = "Alarm_Snooze_Big";
    protected final static String SNOOZE_CURRENTLY_SET_TO = "Currently_Set_Snooze_Type";
    protected final static String NOTIFICATION_ID = "Notification_Identification_Number";
    // Other defined variables
    private SharedPreferences shared;
    private SharedPreferences.Editor edit;

    /**
     * Constructor. Initializes all variables defined above that aren't 'final'
     */
    @SuppressLint("CommitPrefEdits")
    SaveData(SharedPreferences shared) {
        this.shared = shared;
        edit = this.shared.edit();
    }

    @SuppressLint("CommitPrefEdits")
    SaveData() {
        MainActivity main = MainActivity.getInstance();
        this.shared = main.shared;
        this.edit = shared.edit();
    }

    /**
     * One of the overloaded save methods
     *
     * @param name
     * @param num
     */
    void save(String name, int num) {
        edit.putInt(name, num);
        edit.apply();
    }

    /**
     * One of the overloaded save methods
     *
     * @param name
     * @param num
     */
    void save(String name, String num) {
        edit.putString(name, num);
        edit.apply();
    }

    /**
     * One of the overloaded save methods
     *
     * @param name
     * @param num
     */
    void save(String name, boolean num) {
        edit.putBoolean(name, num);
        edit.apply();
    }

    /**
     * Returns the Integer saved under the name provided
     *
     * @param name
     * @return int saved under 'name'
     */
    int getInt(String name) {
        return shared.getInt(name, 0);
    }

    /**
     * Returns the String under the name provided
     *
     * @param name
     * @return String saved under 'name'
     */
    String getStr(String name) {
        return shared.getString(name, null);
    }

    /**
     * Returns the boolean value under the name provided if there is one.
     * Otherwise returns false.
     *
     * @param name
     * @return Boolean saved under 'name'
     */
    boolean getBool(String name) {
        return shared.getBoolean(name, false);
    }
}
