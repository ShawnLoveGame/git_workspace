package com.he.im.service.msg;

import com.he.im.common.RedisConstant;
import com.he.im.conn.ImConnection;
import com.he.im.dao.OfHistoryMapper;
import com.he.im.model.ModelResult;
import com.he.im.model.profire.OfHistory;
import com.he.im.model.profire.OfHistoryExample;
import com.he.im.util.json.JsonUtils;
import com.he.im.util.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.packet.Message;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by he on 2017/4/20.
 */
@Service("messageService")
public class MessageService {

    private Log log = LogFactory.getLog(getClass());

    @Resource
    private ImConnection imConnection;
    @Value("${openfire_server}")
    private String im_host;

    @Resource private OfHistoryMapper ofHistoryMapper;

    @Resource private RedisUtils redisUtils;

    /**
     *
     * @param content
     * @param request
     * @return
     */
    public ModelResult sendMsg(String from, String content, String to,int type, HttpServletRequest request) {
        ModelResult result = new ModelResult(true);
        Map<String,String> map = new ConcurrentHashMap<String, String>();
        map.put("text", content);
        map.put("type", type+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("time", sdf.format(new Date()));
        imConnection.sendMsg(from, to , JsonUtils.toJson(map) , request);
        result.addAttribute("msg" , map);
        return result;
    }


    public ModelResult getMsg(String from,String to){
        //删除气泡提示
        redisUtils.del("C_USER_MSG_"+to+"/"+from);
    	List<String> list = imConnection.getMsg(from, to);
    	ModelResult result = new ModelResult(true);
        List<Map<String,Object>> bodyList = new ArrayList<Map<String,Object>>();
        if(list != null && list.size() > 0 ){
            for(String temp : list){
                Map<String,Object> map = JsonUtils.fromJson(temp , Map.class);
                bodyList.add(map);
            }
        }
    	result.addAttribute("msgList" , bodyList);
        return result;
    }


    /**
     * 查询聊天历史 会话
     * @param toAcount
     * @return
     */
    public ModelResult findHistoryMsg(String toAcount) {
        ModelResult result = new ModelResult(true);
        try {
            //查询当前登录用户的
            List<OfHistory> ofHistories = ofHistoryMapper.selectHistroyChatByUser(toAcount);
            result.addAttribute("historyChat" , ofHistories);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("query error" , e);
            result.setSuccess(false);
            result.setMessage("query error");
        }
        return result;
    }


    public ModelResult findHistoryMsgDetail(String from  , String toname) {
        ModelResult result = new ModelResult(true);
        try {
            //查询当前登录用户的
            redisUtils.del("C_USER_MSG_"+toname+"/"+from);

            OfHistoryExample historyExample  = new OfHistoryExample();
            historyExample.setNeedCount(true);
            OfHistoryExample.Criteria criteria = historyExample.createCriteria();
            List<String> unames = new ArrayList<String>();
            unames.add(from);
            unames.add(toname);
            criteria.andUsernameIn(unames);
            criteria.andTonameIn(unames);
            historyExample.setOrderByClause("creationDate desc");//最近100 条
            List<OfHistory> ofHistories = ofHistoryMapper.selectByExample(historyExample);
            Collections.sort(ofHistories, new Comparator<OfHistory>() {
                public int compare(OfHistory o1, OfHistory o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        return sdf.parse(o1.getCreationdate()).compareTo(sdf.parse(o2.getCreationdate()));
                    } catch (ParseException e) {

                    }
                    return 1;
                }
            });

            List<Map<String,String>> detailList = new ArrayList<Map<String, String>>();
            for(OfHistory history : ofHistories){
                String stanza = history.getStanza();
                Message message = JsonUtils.deserializeXML(stanza, Message.class);
                Map<String,String> map = JsonUtils.fromJson(message.getBody() , Map.class);
                if(map == null ){
                    map = new HashMap<String,String>();
                }
                map.put("from" , history.getToname());
                map.put("to" , history.getUsername());
                detailList.add(map);
            }
            result.addAttribute("detailList" , detailList);
            result.addAttribute("username" , toname);
            result.addAttribute("unread" , 0);
            result.setSuccess(true);

            //
            String uname = from+"/"+toname;
            redisUtils.set("C_USER_MSG_"+toname+"/"+from , 0);
            try { //发消息的人
                redisUtils.lpush(RedisConstant.MSG_USER_LIST_KEY_QUEUE.getPrefix(),  uname);
                redisUtils.publish(RedisConstant.MSG_USER_LIST_KEY_QUEUE.getPrefix(),RedisConstant.MSG_USER_LIST_KEY_CHANNEL.getPrefix(), "user");
            }catch (Exception e){
                log.error("发布订阅失败" ,e );
            }

        } catch (Exception e) {
            log.error("query error" , e);
            result.setSuccess(false);
            result.setMessage("query error");
        }
        return result;
    }

    public ModelResult getCurrentChat(String userName) {
        ModelResult result = new ModelResult(true);
		userName = userName.replaceAll("@" , "+40").replaceAll(" " , "-40");
        List<String> ofHistories = ofHistoryMapper.selectCurrentMsgUser(userName);

        List<Map<String , String>> tmpList = new ArrayList<Map<String , String>>();
        for(String str : ofHistories){
            Map<String , String>  map = new HashMap<String,String>();
            map.put("cname" , str);
            String key = redisUtils.get("C_USER_MSG_"+userName+"/"+str);
            if(StringUtils.isNotBlank(key)){
                map.put("unread" , key);
            }else{
                map.put("unread" , "0");
            }
            tmpList.add(map);

        }
        result.addAttribute("resultData" , tmpList);
        return result;
    }




}
