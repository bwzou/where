package com.creation.where.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zbw on 2017/2/22.
 */

public class MapChatMsg implements Serializable{
    private  static final long serialVersionUID=1L;   //必须序列化

    public String chatwith;       //聊天对象
    public List<MapChatMsgEntity> mapMessage=new ArrayList<>();

    public String getChatwith() {
        return chatwith;
    }

    public void setChatwith(String chatwith) {
        this.chatwith = chatwith;
    }

    public List<MapChatMsgEntity> getMapMessage() {
        return mapMessage;
    }

    public void setMapMessage(List<MapChatMsgEntity> mapMessage) {
        this.mapMessage = mapMessage;
    }
}
