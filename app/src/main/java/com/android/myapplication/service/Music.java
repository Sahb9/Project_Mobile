package com.android.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.myapplication.R;
import com.android.myapplication.utilities.Common;

public class Music extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("" + Common.TAG_LOG, "onStartCommand: in music class");

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.start();

        return START_NOT_STICKY;
    }
}
