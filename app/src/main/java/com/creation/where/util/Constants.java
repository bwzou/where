package com.creation.where.util;

import com.creation.where.po.User;

import java.net.HttpURLConnection;

/**
 * Created by zbw on 2017/3/12.
 */

public class Constants {
    public static final String HOST="http://192.168.1.100:8080/whereserver/";
    public static final String USR_LOGIN=HOST+"LoginController";
    public static final String USR_MESSAGE=HOST+"MessageController";
    public static final String USR_FOOTPRINT=HOST+"FootprintController";
    public static HttpURLConnection CONN=null;
    public static User user=null;

    //百度地图的比例尺1：M
    public static double[] Distance={5,10,20,50,100,200,500,1000,2000,5000,10000,20000,25000,50000,100000,
                                      200000,500000,1000000,2000000,5000000,10000000};
    //百度地图的放大级别
    public static float[] Level={21.0f,20.0f,19.0f,18.0f,17.0f,16.0f,15.0f,14.0f,13.0f,12.0f,11.0f,10.0f,
                                     9.0f,8.0f,7.0f,6.0f,5.0f,4.0f,3.0f,2.0f,1.0f};
}
