package com.android.myapplication.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.myapplication.utilities.Common;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("" + Common.TAG_LOG, "onReceive: OK");

        Intent myIntent = new Intent(context, AlarmService.class);
        context.startService(myIntent);
    }
}
