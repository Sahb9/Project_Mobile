package com.android.myapplication.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.myapplication.Entity.SoundAlarm;
import com.android.myapplication.R;
import com.android.myapplication.utilities.Common;
import com.android.myapplication.utilities.MyIntent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Common.TAG_LOG, "onReceive: Cancel notification manager");

        Intent myIntent = MyIntent.getMyIntent();
        SoundAlarm soundAlarm = (SoundAlarm) myIntent.getSerializableExtra(Common.MUSIC);

        if(soundAlarm != null) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent myIntentReceiver = new Intent(context, AlarmReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, myIntentReceiver, 0);

            Intent intentService = new Intent(context, SoundAlarmService.class);

            alarmManager.cancel(pendingIntent);
            context.stopService(intentService);

            Log.d(Common.TAG_LOG, "onReceive: music");
        } else {
            Log.d(Common.TAG_LOG, "onReceive: not music");
        }
    }
}
