package net.lokasi.lokasi;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivityLokasi extends AppCompatActivity {
    private Intent restartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actifity_lokasi);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sendBroadcast(new Intent("RestartService"));
        new HandleRunnable(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("RestartService"));
    }
}
