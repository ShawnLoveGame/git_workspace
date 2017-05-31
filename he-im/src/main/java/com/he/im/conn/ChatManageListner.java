package com.he.im.conn;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 聊天监听
 * Created by he on 2017/4/21.
 */
@Component
public class ChatManageListner {

    @Resource private ImConnection imConnection;

//    @PostConstruct
//    public void initChatListner(){
//        imConnection.initChat();
//    }


}
