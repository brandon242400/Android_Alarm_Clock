package com.example.shufflealarmclock2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHandler extends Service {


    private static final SaveData saveData = new SaveData();
    private AlarmManager alarmManager;


    @Override
    public void onCreate() {
        Log.d("Brandon", "Made it to NotificationHandler!");
        NotificationManagerCompat.from(getApplicationContext()).cancel(20);
        verifySnoozeTimes();
        // Snooze Types include "default" (notification quick-snooze button), "short snooze", "medium snooze", and "long snooze".
        // Using this value to determine how long to snooze for
        String snoozeType = saveData.getStr(SaveData.SNOOZE_CURRENTLY_SET_TO);
        getDuration(snoozeType);
        super.onCreate();
    }

    /**
     * Gives snooze times default values if they haven't yet been set by the user
     */
    private void verifySnoozeTimes() {
        if (saveData.getInt(SaveData.SNOOZE_TIME_DEFAULT) == 0)
            saveData.save(SaveData.SNOOZE_TIME_DEFAULT, 5);

        if (saveData.getInt(SaveData.SNOOZE_TIME_SMALL) == 0)
            saveData.save(SaveData.SNOOZE_TIME_SMALL, 5);

        if (saveData.getInt(SaveData.SNOOZE_TIME_MED) == 0)
            saveData.save(SaveData.SNOOZE_TIME_MED, 10);

        if (saveData.getInt(SaveData.SNOOZE_TIME_BIG) == 0)
            saveData.save(SaveData.SNOOZE_TIME_BIG, 15);
    }

    /**
     * Uses string extra from intent to figure out how long to snooze for
     */
    private void getDuration(String snoozeType) {
        String snoozeDuration;

        switch (snoozeType) {
            case "short snooze":
                snoozeDuration = SaveData.SNOOZE_TIME_SMALL;
                break;
            case "medium snooze":
                snoozeDuration = SaveData.SNOOZE_TIME_MED;
                break;
            case "long snooze":
                snoozeDuration = SaveData.SNOOZE_TIME_BIG;
                break;
            default:
                snoozeDuration = SaveData.SNOOZE_TIME_DEFAULT;
        }

        snooze(saveData.getInt(snoozeDuration));
    }

    /**
     * Calls the AlarmReceiver again after the specified duration to display another alarm notification
     */
    public void snooze(int duration) {
        // Converting seconds to milliseconds for AlarmManager method
        long snoozeTime = duration * 1000 * 60;
        Log.d("Brandon", "Snooze time would be set for " + duration + " minutes.");

        // Setting AlarmManager to call AlarmReceiver after the snooze duration to play the alarm notification again
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + 3000/*snoozeTime*/, pendingIntent), pendingIntent);

        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
