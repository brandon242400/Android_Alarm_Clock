package com.example.shufflealarmclock2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class AlarmReceiver extends BroadcastReceiver {


    private final SaveData saveData = new SaveData();
    private Context context;
    private Intent intent;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Brandon", "AlarmReceiver.Java Called");

        this.context = context;
        this.intent = intent;

        runNotification();
    }

    private void runNotification() {

        // Creating RemoteViews to display in alarm notification
        RemoteViews remoteSmall = new RemoteViews(context.getPackageName(), R.layout.notification_collapsed);
        RemoteViews remoteLarge = new RemoteViews(context.getPackageName(), R.layout.notification_expanded);


        // Setting intents for notification click
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Creating NotificationCompat.Builder object
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NOTIFICATION 1")
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_stat_alarm_small)
                .setCustomContentView(remoteSmall)
                .setCustomBigContentView(remoteLarge)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setFullScreenIntent(pendingIntent, true);

        createNotificationChannel();

        // Binding Intent to snooze button on notification display
        Intent tempIntent = new Intent(context, NotificationHandler.class);
        saveData.save(SaveData.SNOOZE_CURRENTLY_SET_TO, "default");
        tempIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent buttonIntent = PendingIntent.getService(context, 0, tempIntent, 0);
        remoteSmall.setOnClickPendingIntent(R.id.notifi_collapsed_button_snooze, buttonIntent);

        // Creating NotificationManager to show the alarm notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(20, builder.build());
    }

    private void createNotificationChannel() {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Shuffle_Alarm_Notification_Channel";
            String description = "Notification channel for Shuffle Alarm Application";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("NOTIFICATION 1", name, importance);
            channel.setDescription(description);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
