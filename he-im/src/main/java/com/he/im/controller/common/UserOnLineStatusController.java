package com.he.im.controller.common;

import com.he.im.model.ModelResult;
import com.he.im.util.json.JsonUtils;
import com.he.im.util.net.SOAHttpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户在线状态
 * Created by he on 2017/3/28.
 */
@Controller
public class UserOnLineStatusController {

    private Log log = LogFactory.getLog(getClass());

    @RequestMapping("/im/user/checkIsOnline")
    @ResponseBody
    public ModelResult checkIsOnline(HttpServletRequest request){
        ModelResult result  = new ModelResult();
        String jid = request.getParameter("jid");
        String url = "http://47.90.77.140:9090/plugins/presence/status";
        Map<String,String> params = new HashMap<String,String>();
        params.put("jid" , jid);
        params.put("type" , "xml");
        String form = SOAHttpUtils.postWithForm(url, params);
        Map<String,Object> resultmap =  JsonUtils.fromJson(form , Map.class);
        log.error(JsonUtils.toJson(resultmap));
        result.addAttribute("resultData" , resultmap);
        return result;
    }
}
