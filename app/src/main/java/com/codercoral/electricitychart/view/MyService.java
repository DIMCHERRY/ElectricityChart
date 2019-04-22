package com.codercoral.electricitychart.view;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.codercoral.electricitychart.R;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.westworld);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void showToast() {
            Log.e("Service", "showToast");
        }

        public void showList() {
            Log.e("Service", "showList");
        }

        MyService getService(){
            return MyService.this;
        }

    }
}
