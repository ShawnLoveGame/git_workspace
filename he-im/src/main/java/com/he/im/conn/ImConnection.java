package com.he.im.conn;

import com.he.im.common.RedisConstant;
import com.he.im.util.json.JsonUtils;
import com.he.im.util.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.chatstates.ChatState;
import org.jivesoftware.smackx.chatstates.ChatStateListener;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by he on 2017/4/20.
 */
@Component
public class ImConnection {

    /**
     * openfire服务器address
     */
    @Value("${openfire_server}")
    private String server;
    @Value("${im_port}")
    private String port;
    @Value("${server_path}")
    private String serverPath ;

    private static Log log = LogFactory.getLog(ImConnection.class);

    //private static XMPPTCPConnection connection;
    
    @Resource
    private RedisUtils redisUtils;
    
    private static String pwd="59da8bd04473ac6711d74cd91dbe903d";
    
    private static Map<String,AbstractXMPPConnection> conMap = new ConcurrentHashMap<String,AbstractXMPPConnection>();
    
    public AbstractXMPPConnection getConnectionc(String account){
    	if(StringUtils.isNotBlank(account)){
    		AbstractXMPPConnection con = conMap.get(account);
        	if(con != null){
        		return con;
        	}
        	XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            //builder.setCompressionEnabled(false);//连接套将使用流压缩。
            builder.setDebuggerEnabled(false);
            builder.setSendPresence(true);

            builder.setHost(server);
            builder.setPort(Integer.valueOf(port));
            builder.setServiceName(server);
            builder.setUsernameAndPassword(account, pwd);
            //取消安全认证
            //spark源码LoginDialog.java
            AbstractXMPPConnection newcon = new XMPPTCPConnection(builder.build());
            try {
           	 newcon.connect();
                log.error("IM 连接状态1：" + newcon.isConnected());
                conMap.put(account, newcon);
                return newcon;
            } catch (Exception e) {
                log.error("IM 连接异常", e);
            }
    	}else{
    		XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            //builder.setCompressionEnabled(false);//连接套将使用流压缩。
            builder.setDebuggerEnabled(false);
            builder.setSendPresence(true);

            builder.setHost(server);
            builder.setPort(Integer.valueOf(port));
            builder.setServiceName(server);
            //取消安全认证
            //spark源码LoginDialog.java
            AbstractXMPPConnection newcon = new XMPPTCPConnection(builder.build());
            try {
           	 newcon.connect();
                log.error("IM 连接状态2：" + newcon.isConnected());
                conMap.put(account, newcon);
                return newcon;
            } catch (Exception e) {
                log.error("IM 连接异常", e);
            }
    	}
        return null;
    }

   /* public  AbstractXMPPConnection getConnection() {
        if (connection == null || !connection.isConnected()) {
            openConnection();
        }
        log.error("IM 状态:" + connection.isConnected());
        return connection;
    }*/

    /*打开连接*/
    /*public  void openConnection() {
        //设置连接配置
        XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
        builder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        //builder.setCompressionEnabled(false);//连接套将使用流压缩。
        builder.setDebuggerEnabled(false);
        builder.setSendPresence(true);
        builder.setHost(server);
        builder.setPort(Integer.valueOf(port));
        builder.setServiceName(server);
        //取消安全认证
        //spark源码LoginDialog.java
        connection = new XMPPTCPConnection(builder.build());
        try {
            connection.connect();
            log.error("IM 连接状态：" + connection.isConnected());
        } catch (Exception e) {
            log.error("IM 连接异常", e);
        }
    }*/

    /*关闭连接*/
    /*public void release() {
        if (connection != null) {
            connection.disconnect();
        }
    }*/



    /**
     * 注册
     *
     * @param account
     */
    public void regist(String account) {
        try {
            AbstractXMPPConnection connection = getConnectionc(account);
            AccountManager instance = AccountManager.getInstance(connection);
            instance.createAccount(account, "111111");
        } catch (Exception e) {
            log.error("创建用户失败", e);
        }
    }
    
    public Boolean login(String account, String password) {
        try {
            //避免关闭浏览器导致登录异常，登录时先退出
            this.logout(account);

            AbstractXMPPConnection connection = getConnectionc(account);
            connection.login(account, password);
            //修改用户状态
            updatePresence(account);
            //监听其他人发送的消息
            ChatManager chatManager  = ChatManager.getInstanceFor(connection);
            chatManager.addChatListener(new ChatManagerListener() {
                public void chatCreated(Chat chat, boolean createdLocally) {
                    chat.addMessageListener(new ChatStateListener() {
                        public void stateChanged(Chat chat, ChatState chatState) {

                        }
                        public void processMessage(Chat chat, Message message) {
                            if(message.getBody() != null ){
                                String fromJid = message.getFrom().split("@")[0];
                                String toJid = message.getTo().split("@")[0];
                                //
                                String s = redisUtils.get("CHAT_ROOM_MSG_" + toJid);
                                if(StringUtils.isBlank(s)){
                                    List<String>  c_list = new ArrayList<String>();
                                    c_list.add(fromJid);
                                    redisUtils.setex("CHAT_ROOM_MSG_" + toJid , 60*60*24, JsonUtils.toJson(c_list));
                                }else{
                                    List<String> o_list = JsonUtils.fromJson(s, List.class, String.class);
                                    if(!o_list.contains(fromJid)){
                                        o_list.add(fromJid);
                                    }
                                    redisUtils.set("CHAT_ROOM_MSG_" + toJid ,  JsonUtils.toJson(o_list));
                                }

                                String uname = fromJid+"/"+toJid;
                                redisUtils.rpush(uname, message.getBody());

                                log.error("监听-toJid-"+toJid +"--- fromJid" + fromJid );

                                redisUtils.incr("C_USER_MSG_"+toJid+"/"+fromJid);

                                //创建三个队列
                                try { //发消息的人
                                    redisUtils.lpush(RedisConstant.MSG_USER_LIST_KEY_QUEUE.getPrefix(),  uname);
                                    redisUtils.publish(RedisConstant.MSG_USER_LIST_KEY_QUEUE.getPrefix(),RedisConstant.MSG_USER_LIST_KEY_CHANNEL.getPrefix(), "user");
                                }catch (Exception e){
                                    log.error("发布订阅失败" ,e );
                                }
                                //消息内容
                                try {
                                    Map<String , Object> map = new HashMap<String,Object>();
                                    map.put("from" ,fromJid );
                                    map.put("to" , toJid);
                                    map.put("data" , message.getBody());
                                    redisUtils.lpush(RedisConstant.USER_SEND_MSG_KEY_QUEUE.getPrefix(),  JsonUtils.toJson(map));
                                    redisUtils.publish(RedisConstant.USER_SEND_MSG_KEY_QUEUE.getPrefix(),RedisConstant.USER_SEND_MSG_KEY_CHANNEL.getPrefix(), "sendMsg");
                                }catch (Exception e){
                                    log.error("发布订阅失败" ,e );
                                }
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            log.error("login exception", e);
            return false;
        }
        return true;
    }

    /**
     * 退出登录
     * @return
     */
    public boolean logout(String username) {
        try {
            AbstractXMPPConnection connectionc = getConnectionc(username);
            connectionc.disconnect();

            conMap.remove(username);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改在线状态
     */
    public void updatePresence(String account) {
        try {
            Presence presence = new Presence(Presence.Type.available);
            if(StringUtils.isNotBlank(account)){
                presence.setFrom(account);
            }
            presence.setStatus("Q~");
            getConnectionc(account).sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            log.error("修改用户在线状态");
        }
    }

    /**
     * 回复消息监听（）
     */
   /* public void initChat() {
        AbstractXMPPConnection connection = getConnectionc(null)
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addChatListener(new ChatManagerListener() {
           public void chatCreated(Chat chat, boolean createdLocally) {
               log.error("ccc====:" + JsonUtils.toJson(chat));
               chat.addMessageListener(new ChatMessageListener() {
                   public void processMessage(Chat chat1, Message message) {
                       if(message.getBody() != null ){
                           log.error("来源："+message.getFrom()+"  消息：" + message.getBody());
                    	   String fromJid = message.getFrom().split("@")[0];
                    	   String toJid = message.getTo().split("@")[0];
                    	   String uname = fromJid+"/"+toJid;
                		   redisUtils.rpush(uname, message.getBody());
                       }
                   }
               });
           }
       });
    }*/


    public void sendMsg(String from, String to, String msg, HttpServletRequest request) {
        //发信息核心代码
        try {
            ChatManager chatmanager = ChatManager.getInstanceFor(getConnectionc(from));
            Chat chat = chatmanager.createChat(to + "@" + server);//新建一个会话，指定收信人，不对收信人所回复的信息进行监听
            //因为在初始化的时候，已经对此连接收到信息都进行了监听，无须重复监听
            Message message = new Message();
            message.setBody(msg);
            message.setFrom(from+"@"+server);
            message.setTo(to+"@"+server);
            chat.sendMessage(message);//对此会话进行发送
        } catch (SmackException.NotConnectedException e) {
            log.error("发送消息失败", e);
        }
    }
    
    /**
     * 获取消息信息
     * @param from
     * @param to
     * @return
     */
    public List<String> getMsg(String from,String to){
    	List<String> list = redisUtils.lrange(from+"/"+to, 0, -1);
    	if(list.size() > 0){
    		redisUtils.ltrim(from+"/"+to, list.size(), -1);
    	}
    	return list;
    }
    
    //注销注册  聊天室对象  
    public void removeChat(String userName){  
    	redisUtils.del(userName);  
    }  
    
    //查看是否聊天室对象  已有  
    public  boolean isChatExist(String userName){  
        return redisUtils.exists(userName);  
    }
    
    
    public boolean zhuceForApi(String jid){
    	 try {
             AbstractXMPPConnection connection = getConnectionc(jid);
             AccountManager instance = AccountManager.getInstance(connection);
             instance.createAccount(jid, pwd);
             return true;
         } catch (Exception e) {
             log.error("创建用户失败", e);
         }
    	 return false;
    }
    
    

}
