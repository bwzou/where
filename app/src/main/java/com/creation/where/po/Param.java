package com.creation.where.po;

/**
 * Created by zbw on 2017/3/12.
 */

public class Param {
    private String key;
    private Object value;

    public Param(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

}
