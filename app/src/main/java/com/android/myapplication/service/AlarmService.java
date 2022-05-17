package com.android.myapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.myapplication.Activity.MainActivity;
import com.android.myapplication.R;
import com.android.myapplication.utilities.Common;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("" + Common.TAG_LOG, "onStartCommand: Reminding");

        //Intent notifyIntent = new Intent(AlarmService.this, MainActivity.class);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(getApplicationContext(), "My notification");
        notificationCompat.setContentTitle("My notification");
        notificationCompat.setContentText("Hello");
        notificationCompat.setSmallIcon(R.drawable.ic_launcher_background);
        notificationCompat.setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, notificationCompat.build());

        return START_STICKY;
    }
}
