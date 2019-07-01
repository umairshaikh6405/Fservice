package com.example.aa.service;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class Media extends AppCompatActivity {

    private Button  playbtn;
    private MediaPlayer mPlayer;
    private SeekBar songPrgs;
    private static int oTime =0, sTime =0, eTime =0;
    private Handler hdlr = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


       // getMediaFileList();
        playbtn = (Button)findViewById(R.id.play);
        mPlayer = MediaPlayer.create(this, R.raw.song);
        songPrgs = (SeekBar)findViewById(R.id.sBar);
        songPrgs.setClickable(false);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mPlayer.isPlaying()) {
                    Toast.makeText(Media.this, "Playing Audio", Toast.LENGTH_SHORT).show();
                    mPlayer.start();
                    eTime = mPlayer.getDuration();
                    sTime = mPlayer.getCurrentPosition();
                    if (oTime == 0) {
                        songPrgs.setMax(eTime);
                        oTime = 1;
                    }
//                songTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(eTime),
//                        TimeUnit.MILLISECONDS.toSeconds(eTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(eTime))) );
//                startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
//                        TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS. toMinutes(sTime))) );
                    songPrgs.setProgress(sTime);
                    hdlr.postDelayed(UpdateSongTime, 100);

                }else {
                    mPlayer.pause();
                    Toast.makeText(getApplicationContext(),"Pausing Audio", Toast.LENGTH_SHORT).show();
                    hdlr.removeCallbacks(UpdateSongTime);
                }




            }
        });




songPrgs.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        if (b){
            mPlayer.seekTo(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
    }


    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            sTime = mPlayer.getCurrentPosition();
            Log.i("-----", "start time: "+String.format("%d , %d ", TimeUnit.MILLISECONDS.toMinutes(sTime),
                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );
            songPrgs.setProgress(sTime);
            hdlr.postDelayed(this, 100);
        }
    };

    protected void getMediaFileList(){


        ContentResolver contentResolver = getContentResolver();


        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


        Cursor cursor = contentResolver.query(
                uri, // Uri
                null, // Projection
                null, // Selection
                null, // Selection args
                null // Sor order
        );

        if (cursor == null) {
            Log.i("-----", "Query failed, handle error: ");

        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.i("-----", "Nno music found on the sd card. ");

        } else {
            int location = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int id = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            // Loop through the musics
            do {

               // Uri thisTitle = cursor.getPosition();

                Log.i("-----", "Nno music found on the sd card. "+cursor.getString(location));
                Log.i("-----", "Nno music found on the sd card. "+cursor.getString(id));

                // Process current music here
            } while (cursor.moveToNext());
        }
    }


    public void buttonClicked(View v) {
        Button button = (Button) v;
        Intent service = new Intent(Media.this, ForegroundService.class);
        if (!ForegroundService.IS_SERVICE_RUNNING) {
            service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = true;
            button.setText("Stop Service");
        } else {
            service.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = false;
            button.setText("Start Service");

        }
        startService(service);

    }
}


