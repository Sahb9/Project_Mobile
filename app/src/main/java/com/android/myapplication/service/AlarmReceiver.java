package com.android.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.myapplication.utilities.Common;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, SoundAlarmService.class);
        context.startService(myIntent);

        Log.d(Common.TAG_LOG, "onReceive: AlarmReceiver");
    }
}
