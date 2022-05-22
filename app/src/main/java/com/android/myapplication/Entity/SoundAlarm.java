package com.android.myapplication.Entity;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.Serializable;

public class SoundAlarm implements Serializable {
    private MediaPlayer mediaPlayer;
    private SoundAlarm soundAlarm;

    public SoundAlarm() {
        soundAlarm = this;
    }

    public SoundAlarm getSoundAlarm() {
        return soundAlarm;
    }

    public void playMusic(Context context, int resId) {
        this.mediaPlayer = MediaPlayer.create(context, resId);
        this.mediaPlayer.setLooping(true);

        this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    public void stopMusic() {
        this.mediaPlayer.stop();
    }
}
