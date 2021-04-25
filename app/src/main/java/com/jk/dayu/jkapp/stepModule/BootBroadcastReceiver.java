package com.jk.dayu.jkapp.stepModule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("conf", Context.MODE_PRIVATE);
        boolean temp=sharedPreferences.getBoolean("switch_on", false);
        if (!temp)
            return;
        Intent serviceIntent = new Intent(context, StepActivity.class);
        serviceIntent.putExtra("restart",intent.getAction());
        context.startService(serviceIntent);
    }
}
