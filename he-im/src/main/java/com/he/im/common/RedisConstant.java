package com.he.im.common;

/**
 * 缓存 key
 * Created by he on 2017/5/26.
 */
public enum RedisConstant {


    UN_READ_MSG_NUM_KEY_QUEUE("UN_READ_MSG_NUM_KEY_QUEUE"),// 未读
    UN_READ_MSG_NUM_KEY_CHANNEL("UN_READ_MSG_NUM_KEY_CHANNEL") ,


    MSG_USER_LIST_KEY_QUEUE("MSG_USER_LIST_KEY_QUEUE"),//发消息用户 列表
    MSG_USER_LIST_KEY_CHANNEL("MSG_USER_LIST_KEY_CHANNEL") ,//

    USER_SEND_MSG_KEY_QUEUE("USER_SEND_MSG_KEY_QUEUE"),//接受的消息队列
    USER_SEND_MSG_KEY_CHANNEL("USER_SEND_MSG_KEY_CHANNEL") ,


    ;
    private String prefix;

    RedisConstant(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


}
