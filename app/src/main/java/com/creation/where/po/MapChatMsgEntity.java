package com.creation.where.po;

/**
 * Created by zbw on 2017/2/20.
 */

public class MapChatMsgEntity {
    private String name;

    private String date;

    private String text;

    private boolean isComMeg = true;     //是否使用表情资源

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

    public MapChatMsgEntity(String name, String date, String text, boolean isComMsg) {
        super();
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }

}
