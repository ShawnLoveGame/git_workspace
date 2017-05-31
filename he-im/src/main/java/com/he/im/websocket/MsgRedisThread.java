package com.he.im.websocket;

import com.he.im.common.RedisConstant;
import com.he.im.util.shared.RedisUtils;
import com.he.im.util.shared.pubsub.RedisPubSub;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by he on 2017/5/26.
 */
@Component
public class MsgRedisThread extends Thread{
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
        redisUtils.subscribe(RedisConstant.MSG_USER_LIST_KEY_QUEUE.getPrefix(),
                new RedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        log.error("发送消息的人：" + message);
                        if ("user".equals(message)) {
                            String formjid = "";
                            while(formjid != null ){
                                formjid = redisUtils.rpop(RedisConstant.MSG_USER_LIST_KEY_QUEUE.getPrefix());
                                if(StringUtils.isNotBlank(formjid)){
                                    log.error("formjid:"+formjid);
                                    //发送到前台
                                    String from = formjid.split("/")[0];
                                    String jid = formjid.split("/")[1];
                                    String key = redisUtils.get("C_USER_MSG_"+jid+"/"+from);
                                    MyWebSocketHandler.sendMsgToUser(from , jid , key  , 1);
                                }
                            }
                        }
                    }
                }, RedisConstant.MSG_USER_LIST_KEY_CHANNEL.getPrefix()
        );
    }
}
