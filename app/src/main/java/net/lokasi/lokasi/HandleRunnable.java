package net.lokasi.lokasi;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.util.Date;

/**
 * Created by ade on 7/16/17.
 */

public class HandleRunnable {
    private EmailTracker emailTracker;
    private Handler handler;
    private int timeDelay = 13000;
    private Runnable runnable;
    private MainActivityLokasi mainActivityLokasi;
    private long timeBefore, timeNow;

    public HandleRunnable(Context context) {
        timeNow = new Date().getTime();
        timeBefore = timeNow;
        emailTracker = new EmailTracker(new GPSTracker(context));
        emailTracker.senEmaiLocation();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                timeNow = new Date().getTime();
                boolean canSend = false;
                if (timeBefore == 0) {
                    canSend = true;
                    timeBefore = timeNow;
                } else if (((timeNow - timeBefore) / (60000)) % 60 >= 10) {
                    canSend = true;
                    timeBefore = timeNow;
                }

                if (canSend) {
                    emailTracker.senEmaiLocation();
                }

                handler.postDelayed(this, timeDelay);
            }
        };
        handler.postDelayed(runnable, timeDelay);
    }
}
