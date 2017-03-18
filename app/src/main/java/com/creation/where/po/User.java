package com.creation.where.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by zbw on 2017/3/12.
 */

public class User implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String nickname;
    private String password;
    private String where_name;
    private int gender;
    //    private String avatar;    //头像暂时不用
    private String phone_number;
    private String area;
    //    private String two_dimension_code;   //二维码暂时也不用
    private String signature;
    private String reminder;
    private Timestamp remind_time_from;
    private Timestamp remind_time_to;

    public int getId() {
        return id;
    }
    public String getWhere_name() {
        return where_name;
    }
    public void setWhere_name(String where_name) {
        this.where_name = where_name;
    }
    public int getGender() {
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public String getReminder() {
        return reminder;
    }
    public void setReminder(String reminder) {
        this.reminder = reminder;
    }
    public Timestamp getRemind_time_from() {
        return remind_time_from;
    }
    public void setRemind_time_from(Timestamp remind_time_from) {
        this.remind_time_from = remind_time_from;
    }
    public Timestamp getRemind_time_to() {
        return remind_time_to;
    }
    public void setRemind_time_to(Timestamp remind_time_to) {
        this.remind_time_to = remind_time_to;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
