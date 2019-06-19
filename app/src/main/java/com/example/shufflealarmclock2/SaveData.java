package com.example.shufflealarmclock2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;


@SuppressLint("Registered")
public class SaveData extends AppCompatActivity implements Serializable {


    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    protected final static String TIME_STRING_REFERENCE = "Main_Time(str)_Reference";
    protected final static String MAYBE_MINUTE_REFERENCE = "Possible_Minute_Reference";
    protected final static String MAYBE_TIME_STRING_REFERENCE = "Possible_Time_String_Reference";
    protected final static String MAYBE_HOUR_REFERENCE = "Possible_Hour_Reference";
    protected final static String ON_OFF_SWITCH_MAIN = "Main_OnOffSwitch_Bool";
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
        return shared.getString(name, "12:00 p.m.");
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
