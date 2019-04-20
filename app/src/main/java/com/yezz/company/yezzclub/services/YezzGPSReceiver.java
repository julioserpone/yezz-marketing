package com.yezz.company.yezzclub.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class YezzGPSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!YezzGPS.isRunning) {
            Intent gpsIntent = new Intent(context, YezzGPS.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, gpsIntent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 10);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
