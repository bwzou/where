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
    public static HttpURLConnection CONN=null;

    public static User user=null;   //全局变量
}
