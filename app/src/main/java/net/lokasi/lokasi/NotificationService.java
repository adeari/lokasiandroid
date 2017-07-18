package net.lokasi.lokasi;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by ade on 7/16/17.
 */

public class NotificationService extends Service {
    public static boolean isStarted = false;

    public NotificationService() {
        onCreate();
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int res = super.onStartCommand(intent, flags, startId);
        Intent notificationIntent = new Intent(this, MainActivityLokasi.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Lokasi jalan")
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();

        startForeground(startId, notification);
        if (isStarted){
            return Service.START_STICKY;
        } else {
            isStarted = true;
        }
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("RestartService"));
    }

    @Override
    public void onCreate() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            new HandleRunnable(getApplicationContext());
        } catch (NullPointerException nullPointerException) {
            nullPointerException.getMessage();
        }
    }
}
