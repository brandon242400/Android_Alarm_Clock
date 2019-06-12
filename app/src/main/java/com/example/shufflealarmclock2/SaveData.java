package com.example.shufflealarmclock2;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


@SuppressLint("Registered")
public class SaveData extends AppCompatActivity {


    protected final static String AM_REFERENCE = "Main_AM_Variable";
    protected final static String MAYBE_HOUR_REFERENCE = "Possible_Hour_Reference";
    private SharedPreferences shared;
    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    private SharedPreferences.Editor edit;
    protected final static String TIME_STRING_REFERENCE = "Main_Time(str)_Reference";
    protected final static String MAYBE_MINUTE_REFERENCE = "Possible_Hour_Reference";

    /**
     * Constructor. Initializes all variables defined above that aren't 'final'
     */
    @SuppressLint("CommitPrefEdits")
    SaveData(SharedPreferences shared) {
        Log.d("Save", "SaveData Initialized");
        this.shared = shared;
        edit = this.shared.edit();
    }

    void changeSaveFileName(String name) {

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
        Log.i("Save", "Saved '" + num + "' under name '" + name + "'.");
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
        Log.i("Save", "Saved '" + num + "' under name '" + name + "'.");
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
        Log.i("Save", "Saved '" + num + "' under name '" + name + "'.");
    }

    /**
     * Returns the Integer saved under the name provided
     *
     * @param name
     * @return int saved under 'name'
     */
    int getInt(String name) {
        Log.i("Load", "Loading and returning variable saved under '" + name + "'.");
        return shared.getInt(name, 0);
    }

    /**
     * Returns the String under the name provided
     *
     * @param name
     * @return String saved under 'name'
     */
    String getStr(String name) {
        Log.i("Load", "Loading and returning variable saved under '" + name + "'.");
        return shared.getString(name, " ");
    }

    /**
     * Returns the boolean value under the name provided if there is one.
     * Otherwise returns false.
     *
     * @param name
     * @return Boolean saved under 'name'
     */
    boolean getBool(String name) {
        Log.i("Load", "Loading and returning variable saved under '" + name + "'.");
        return shared.getBoolean(name, false);
    }
}
