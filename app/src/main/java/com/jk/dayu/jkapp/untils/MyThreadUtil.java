package com.jk.dayu.jkapp.untils;

import android.util.Log;
import com.jk.dayu.jkapp.service.HttpUntil;

public class MyThreadUtil{
    String url;
    String strJson;
    static String result;
    public String myThread(final String url, final String strJson){
        this.url = url;
        this.strJson = strJson;
        Thread thread=new Thread(runnable);
        thread.start();
        try {
            //阻塞主线程，直到子线程拿到结果
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("请求异常","请求:"+url+"异常:"+e.getMessage());
        }
        return result;
    }
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            try{
                result = HttpUntil.postUntil(url, strJson);
            }catch (Exception e){
                e.printStackTrace();
                Log.e("请求异常","请求:"+url+"异常:"+e.getMessage());
            }
        }
    };
}
