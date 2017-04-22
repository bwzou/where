package com.creation.where.util;

/**
 * Created by zbw on 2017/2/21.
 */

public class LocationUtils {
    private static double EARTH_RADIUS = 6378.137;   //直径

    /**
     * 将角度转换为弧度
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 将弧度转换为角度
     * @param radian
     * @return
     */
    static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return s;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     */
    public static double getDistanceByotherWay(double lat1, double lng1, double lat2, double lng2) {
        double theta=lng1-lng2;
        double dist=Math.acos(Math.sin(rad(lat1)) * Math.sin(rad(lat2))
                + Math.cos(rad(lat1)) * Math.cos(rad(lat2)) * Math.cos(rad(theta)));
        dist = rad2deg(dist);
        double miles = dist * 111.19;            // 111.19=60 * 1.1515 * 1.609344; 转化成公里
        return miles;
    }

}
