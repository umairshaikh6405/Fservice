package com.example.forground;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.support.v4.app.NotificationCompat.*;
import static com.example.forground.App.CHANNEL_ID;


public class ExampleService extends Service {
    int a = 0;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.i("--------", intent.getAction());
        if (intent.getAction().equals("StartService")){


        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);


            Intent previousIntent = new Intent(this, ExampleService.class);
            previousIntent.setAction("service");
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);


        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "yhis";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        Notification notification = new Notification.Builder(this)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setChannelId(CHANNEL_ID)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous,"service",ppreviousIntent)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Log.i("-----", "run: "+a++);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();



       startForeground(notifyID, notification);

        }else if (intent.getAction().equals("service")){
            Log.i("--------", "onStartCommand: done");
        }
        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel chan = new NotificationChannel(CHANNEL_ID, "ewrwer", importance);
        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE) ;
        service.createNotificationChannel(chan);
        return channelId;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
