package com.example.shufflealarmclock2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    protected final static String HOUR_REFERENCE = "Main_Hour_Reference";
    protected final static String MINUTE_REFERENCE = "Main_Minute_Reference";
    protected final static String AM_REFERENCE = "Main_AM_Variable";
    protected final static String SAVE_FILE = "Shuffle_Alarm_Clock_Save_File";
    Integer timeHour;
    Integer timeMinute;
    boolean AM;
    SaveData saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSaved();
    }

    /**
     * Starts the activity to change the alarm status
     *
     * @param view
     */
    public void editAlarm(View view) {
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
        Intent intent = new Intent(this, AlarmSettings.class);
        startActivity(intent);
    }

    /**
     * Method collects saved alarm data from saved file
     */
    private void getSaved() {
        timeHour = saveData.getInt(SaveData.HOUR_REFERENCE);
        timeMinute = saveData.getInt(SaveData.MINUTE_REFERENCE);
        AM = saveData.getBool(SaveData.AM_REFERENCE);
        String minuteText = timeMinute.toString();
        if (timeMinute < 10) {
            minuteText = "0" + minuteText;
        }
        String am_pm;
        if (AM) {
            am_pm = "a.m.";
        } else {
            am_pm = "p.m.";
        }
        TextView view = findViewById(R.id.second_layout_text);
        String temp = timeHour.toString() + ":" + minuteText;
        view.setText(temp);
    }


}
