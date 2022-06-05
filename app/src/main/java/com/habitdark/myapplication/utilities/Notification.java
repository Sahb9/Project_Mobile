package com.habitdark.myapplication.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.habitdark.myapplication.R;

public class Notification extends ContextWrapper {
    private final String CHANNEL_ID = "ChannelID";
    private final String CHANNEL_NAME = "ChannelName";

    private NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification(Context base) {
        super(base);

        createChannel();
    }

    public NotificationManager getNotificationManager() {
        if (this.notificationManager == null) {
            this.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return this.notificationManager;
    }

    public NotificationCompat.Builder getNotificationBuilder() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new NotificationCompat.Builder(getApplicationContext(), this.CHANNEL_ID)
                .setContentTitle("Alarm!!!")
                .setContentText(Common.CONTENT_NOTIFY)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setSound(uri)
                .setAutoCancel(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel notificationChannel =
                new NotificationChannel(this.CHANNEL_ID, this.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

        getNotificationManager().createNotificationChannel(notificationChannel);
    }
}
