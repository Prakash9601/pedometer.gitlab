package com.example.pedometer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;

import java.io.File;

public class MyService extends Service {

    MediaPlayer mediaPlayer;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer=MediaPlayer.create(this,R.raw.songs);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}