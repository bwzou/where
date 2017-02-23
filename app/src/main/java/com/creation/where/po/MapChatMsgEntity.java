package com.creation.where.po;

/**
 * Created by zbw on 2017/2/20.
 */

public class MapChatMsgEntity {
    private String name;  //md5值

    private String date;  //时间

    private String text;  //内容

    private double latitude;   //地点

    private double longtitude;

    private double formUser;

    private double toUser;

    private boolean isComMeg = true;     //是否使用表情资源

    private boolean isRead = false;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getFormUser() {
        return formUser;
    }

    public void setFormUser(double formUser) {
        this.formUser = formUser;
    }

    public double getToUser() {
        return toUser;
    }

    public void setToUser(double toUser) {
        this.toUser = toUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
        isComMeg = isComMsg;
    }

    public MapChatMsgEntity() {
    }

    public MapChatMsgEntity(String name, String date, String text, double latitude, double formUser,
                            double longtitude, double toUser, boolean isComMeg,boolean isRead) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.latitude = latitude;
        this.formUser = formUser;
        this.longtitude = longtitude;
        this.toUser = toUser;
        this.isComMeg = isComMeg;
        this.isRead=isRead;
    }
}
