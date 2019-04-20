package com.yezz.company.yezzclub;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.services.YezzGPS;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Splash extends YezzMeta {
    private static final int delay=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.initComponent();

        YezzDB dbHelper=new YezzDB(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }

    protected void initComponent() {
        this.showSplash();
    }

    private void showSplash(){
        Timer time=new Timer();

        time.schedule(new TimerTask() {
            @Override
            public void run() {
                redirect();
            }
        },delay);
    }

    private void redirect(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }

        ShowMessageGps();

        if(!YezzGPS.isRunning) {
            Log.v("SERVICIO","iniciando gps");
            Intent intent = new Intent(getBaseContext(), YezzGPS.class);
            PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 10);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        if(this.isLogged()){
            this.startActivity(new Intent(Splash.this,Dashboard.class));

        }else{
            this.startActivity(new Intent(Splash.this,Login.class));
        }

        this.finish();
    }
}
