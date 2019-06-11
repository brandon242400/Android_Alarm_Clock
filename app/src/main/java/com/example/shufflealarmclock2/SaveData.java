package com.example.shufflealarmclock2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class SaveData extends AppCompatActivity {


    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    protected final static String AM_REFERENCE = "Main_AM_Variable";
    protected final static String TIME_STRING_REFERENCE = "Main_Time(str)_Reference";
    protected final static String MAYBE_HOUR_REFERENCE = "Possible_Hour_Reference";
    protected final static String MAYBE_MINUTE_REFERENCE = "Possible_Hour_Reference";
    String fileName = MainActivity.SAVE_FILE;
    SharedPreferences shared = getSharedPreferences(fileName, Context.MODE_PRIVATE);
    SharedPreferences.Editor edit = shared.edit();


    SaveData() {
    }

    public void save(String name, int num) {
        edit.putInt(name, num);
        edit.apply();
        Log.i("Save", "Saved '" + num + "' under name '" + name + "'.");
    }

    public void save(String name, String num) {
        edit.putString(name, num);
        edit.apply();
        Log.i("Save", "Saved '" + num + "' under name '" + name + "'.");
    }

    public void save(String name, boolean num) {
        edit.putBoolean(name, num);
        edit.apply();
        Log.i("Save", "Saved '" + num + "' under name '" + name + "'.");
    }

    public int getInt(String name) {
        Log.i("Load", "Loading and returning variable saved under '" + name + "'.");
        return shared.getInt(name, 0);
    }

    public String getStr(String name) {
        Log.i("Load", "Loading and returning variable saved under '" + name + "'.");
        return shared.getString(name, " ");
    }

    public boolean getBool(String name) {
        Log.i("Load", "Loading and returning variable saved under '" + name + "'.");
        return shared.getBoolean(name, false);
    }
}
