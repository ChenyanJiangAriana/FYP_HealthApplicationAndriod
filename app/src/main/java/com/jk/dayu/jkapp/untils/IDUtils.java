package com.jk.dayu.jkapp.untils;

import java.util.Random;

/**
 * 各种id生成策略
 */
public class IDUtils {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);

        return str;
    }

    /**
     * 用户id生成
     */
    public static long getNewId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(999);
        //如果不足两位前面补0
        String str = millis + String.format("%03d", end2);
        long id = new Long(str);
        return id;
    }
    public static String getNewStrId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(999);
        //如果不足两位前面补0
        String str = millis + String.format("%03d", end2);
        return str;
    }

    public static void main(String[] args) {
        for(int i=0;i< 100;i++) {
            System.out.println(getNewId());
        }
    }
}
