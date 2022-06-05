package com.habitdark.myapplication.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.habitdark.myapplication.Entity.SoundAlarm;
import com.habitdark.myapplication.R;
import com.habitdark.myapplication.utilities.Common;
import com.habitdark.myapplication.utilities.MyIntent;

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
