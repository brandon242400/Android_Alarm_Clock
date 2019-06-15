package com.example.shufflealarmclock2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Brandon", "ALARM IS CALLED. RECEIVED BY AlarmReceiver!!!");
    }
}
