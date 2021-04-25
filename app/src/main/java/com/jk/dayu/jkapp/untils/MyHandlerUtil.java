package com.jk.dayu.jkapp.untils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyHandlerUtil {
    public static void myHandler(final Context context, Bundle data){
        Message msg = new Message();
        msg.setData(data);
        @SuppressLint("HandlerLeak")
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String message = data.getString("message");
                Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
