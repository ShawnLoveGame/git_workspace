package com.he.im.websocket;

import com.he.im.common.RedisConstant;
import com.he.im.util.json.JsonUtils;
import com.he.im.util.shared.RedisUtils;
import com.he.im.util.shared.pubsub.RedisPubSub;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by he on 2017/5/26.
 */
@Component
public class SendMsgRedisThread extends Thread{
    @Resource
    private RedisUtils redisUtils;
    private Log log = LogFactory.getLog(getClass());
    @PostConstruct
    public void run0() {
        start();
    }

    public void run() {
        //处理订阅的消息
        //发送对象
        redisUtils.subscribe(RedisConstant.USER_SEND_MSG_KEY_QUEUE.getPrefix(),
                new RedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        log.error("msg：" + message);
                       if("sendMsg".equals(message)){
                            Map<String,Object> meg = new HashMap<String,Object>();
                            while(meg != null ){
                                meg = JsonUtils.fromJson(redisUtils.rpop(RedisConstant.USER_SEND_MSG_KEY_QUEUE.getPrefix()) , Map.class);
                                if(meg != null ){
                                    log.error("eg" + JsonUtils.toJson(meg));
                                    String fromJid = (String) meg.get("from");
                                    String toJid = (String) meg.get("to");
                                    Map<String , Object> data = JsonUtils.fromJson(JsonUtils.toJson(meg.get("data")) , Map.class);

                                    MyWebSocketHandler.sendMsgToUser(fromJid , toJid , JsonUtils.toJson(data)  , 3);
                                }
                            }
                        }
                    }
                }, RedisConstant.USER_SEND_MSG_KEY_CHANNEL.getPrefix()
        );
    }

}
