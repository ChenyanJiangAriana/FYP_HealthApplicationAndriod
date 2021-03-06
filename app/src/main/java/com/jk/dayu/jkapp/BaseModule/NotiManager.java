package com.jk.dayu.jkapp.BaseModule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jk.dayu.jkapp.R;

public class NotiManager {
    public static void addAlert(Context context,String date,String content,int id){
        Intent hangIntent = new Intent(context, MainActivity.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        Notification notification = mBuilder
                .setContentText(content)
                .setContentTitle("health reminder")
                .setSubText("time："+date)
                .setSmallIcon(R.mipmap.logo)
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示//The time when the notification is generated is displayed in the notification message
                .setContentIntent(hangPendingIntent)
                .build();
        Log.i("time", "addAlert: "+System.currentTimeMillis());
        android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id, mBuilder.build());
    }
}
