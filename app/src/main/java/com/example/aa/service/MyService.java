package com.example.aa.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MediaPlayer mediaPlayer =
                MediaPlayer.create(getApplicationContext(), R.raw.song);
        mediaPlayer.start();

        Intent nottiInt = new Intent(this,Media.class);

        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
