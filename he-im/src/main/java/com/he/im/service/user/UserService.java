package com.he.im.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.he.im.common.ConstantParam;
import com.he.im.conn.ImConnection;
import com.he.im.dao.OfUserMapper;
import com.he.im.exception.BusinessException;
import com.he.im.model.ModelResult;
import com.he.im.model.profire.OfUser;
import com.he.im.model.profire.OfUserDTO;
import com.he.im.model.profire.OfUserExample;
import com.he.im.model.profire.UserLoginVo;
import com.he.im.util.CookieUtils;
import com.he.im.util.codec.AESUtils;
import com.he.im.util.json.JsonUtils;
import com.he.im.util.openfire.Blowfish;
import com.he.im.util.shared.RedisUtils;

/**
 * Created by he on 2017/3/28.
 */
@Service("userService")
public class UserService {

    private Log log = LogFactory.getLog(getClass());

    @Resource
    private OfUserMapper ofUserMapper;

    @Resource
    private ImConnection imConnection;
    @Value("${cookie_domain}")
    private String cookie_domain;

    @Value("${passwordKey}")
    private String passwordKey;

    @Resource private RedisUtils redisUtils;
    
    private static String pwd="59da8bd04473ac6711d74cd91dbe903d";
    
    public ModelResult getImInfo(String userName) {
        ModelResult result = new ModelResult();
        // 根据用户查询IM信息
        OfUserExample userExample = new OfUserExample();
        OfUserExample.Criteria criteria1 = userExample.createCriteria();
        criteria1.andUsernameEqualTo(userName);
        List<OfUser> ofUsers = ofUserMapper.selectByExample(userExample);
        OfUser ofUser = null;
        if (ofUsers.size() == 0) {
            imConnection.regist(userName);
            List<OfUser> ofUsers2 = ofUserMapper.selectByExample(userExample);
            ofUser = ofUsers2.get(0);
        } else {
            ofUser = ofUsers.get(0);
        }
        result.addAttribute("imUser", ofUser);
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 注册一个商户账号信息，密码随机生成并返回给请求者
     * @param params
     * @return
     */
    public Map<String,Object> addOneSeller(TreeMap<String, String> params){
    	Map<String,Object> result = new HashMap<String,Object>();
    	result.put("status", false);
    	
    	String msgKeyName = "msg";
    	try {
			//验证入参合法性
    		if(params == null){
    			result.put(msgKeyName, "params error");
    			return result;
    		}

    		String signReq = params.get("sign");
    		if(StringUtils.isBlank(signReq)){
    			result.put(msgKeyName, "no sign");
    			return result;
    		}
    		
    		//根据pid获取对应的 key
    		String pidStr = params.get("pid");
    		if(StringUtils.isBlank(pidStr)){
    			result.put(msgKeyName, "no pid");
    			return result;
    		}
    		String classpathStr = UserService.class.getClassLoader().getResource("").getPath();
    		Properties properties = new Properties();
    		File file = new File(classpathStr+File.separator+"config"+File.separator+"dev"+File.separator+"pidKey.properties");
    		InputStream inStream = new FileInputStream(file);
    		properties.load(inStream);
    		String keyStr = properties.getProperty(pidStr);
    		if(StringUtils.isBlank(keyStr)){
    			result.put(msgKeyName, "no key");
    			return result;
    		}
    		params.put("key", keyStr);
    		
    		//字典排序所有参数，并计算签名，然后比对，验证合法性
    		Set<Entry<String,String>> entrySet = params.entrySet();
    		Iterator<Entry<String, String>> iterator = entrySet.iterator();
    		StringBuffer sb_tmp = new StringBuffer();
    		while(iterator.hasNext()){
    			Entry<String, String> next = iterator.next();
    			String key = next.getKey();
    			String value = next.getValue();
    			
    			if("sign".equalsIgnoreCase(key)){
    				continue;
    			}
    			
    			sb_tmp.append(key+"="+value);
				sb_tmp.append("&");
    		}
    		String paramsStrTmp = sb_tmp.toString().substring(0, sb_tmp.length()-1);
    		String sign_tmp = DigestUtils.md5Hex(paramsStrTmp);
    		if(!sign_tmp.equalsIgnoreCase(signReq)){
    			result.put(msgKeyName, "sign error");
    			return result;
    		}
    		
    		//判断用户是否存在
    		String shopnameStr = params.get("shopname");
    		OfUserExample userExample  = new OfUserExample();
	        OfUserExample.Criteria criteria = userExample.createCriteria();
	        criteria.andUsernameEqualTo(shopnameStr);
	        List<OfUser> ofUsers = ofUserMapper.selectByExample(userExample);
	        if(ofUsers.size()>0){
	        	result.put(msgKeyName, "this shopname is existed");
	        	return result;
	        }
    		
    		//生成初始密码
	        String passwordStr = RandomStringUtils.random(6, "abcdefghijklmnopqrstuvwxyz0123456789");
    		
    		//注册
	        
	        AbstractXMPPConnection connection = this.imConnection.getConnectionc(shopnameStr);
            AccountManager instance = AccountManager.getInstance(connection);
            instance.createAccount(shopnameStr, passwordStr);
    		
    		//返回初始密码等信息
            result.put("status", true);
            result.put(msgKeyName, "register ok");
            result.put("password", passwordStr);
    		
		} catch (Exception e) {
			if(log.isDebugEnabled()){
				log.debug(e.getMessage());
			}
			result.put(msgKeyName, e.getMessage());
			e.printStackTrace();
		}
    	return result;
    }
    
    /**
     * 登录接口   若用户不存在则 注册用户在登录
     * @param jid
     * @return
     */
    public ModelResult loginForApi(String jid) {
    	ModelResult result = new ModelResult();
    	try {  
	       // 判断用户是否存在
	        OfUserExample userExample  = new OfUserExample();
	        OfUserExample.Criteria criteria = userExample.createCriteria();
	        criteria.andUsernameEqualTo(jid);
	        List<OfUser> ofUsers = ofUserMapper.selectByExample(userExample);
	        if(ofUsers.size() == 0){
	            // 注册
	        	imConnection.zhuceForApi(jid);
	        }
	        boolean b = imConnection.login(jid,pwd);
	        result.setSuccess(b);
	        return result;
	    } catch (Exception e) {  
	    	result.setSuccess(false);
	    	return result;
	    }  
    }

    public ModelResult toLogin(String jid, String pwd , HttpServletResponse response , HttpServletRequest request) {
        ModelResult result = new ModelResult();
        //check user exist
        String ac = jid.replaceAll("@" , "+40").replaceAll(" ", "-40");
        OfUserExample userExample  = new OfUserExample();
        OfUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(ac);
        List<OfUser> ofUsers = ofUserMapper.selectByExample(userExample);
        if(ofUsers.size() == 0){
            result.setMessage("login error,The current user does not exist");
            result.setSuccess(false);
            return result;
        }
        //check pwd
        OfUser ofUser = ofUsers.get(0);
        Blowfish blowfish  = new Blowfish(passwordKey);
        String decryptString = blowfish.decryptString(ofUser.getEncryptedpassword());
        if(!pwd.equals(decryptString)){
            result.setMessage("login error,The current pwd error");
            result.setSuccess(false);
            return result;
        }
        Boolean login = imConnection.login(ac, pwd);
        result.setSuccess(login);
        if(result.isSuccess()){
            //cookie
            doCookie(ofUsers.get(0) , response , request);
        }
        return result;
    }


    public void doCookie(OfUser vo, HttpServletResponse response, HttpServletRequest request) {
        try {
            UserLoginVo luv = new UserLoginVo();
            luv.setUserId(vo.getId());
            luv.setUserName(vo.getUsername().replaceAll("\\+40","@").replaceAll("-40" , " "));
            luv.setLoginTime(System.currentTimeMillis());
            luv.setUuid(UUID.randomUUID().toString());
            request.setAttribute(ConstantParam.cookieLoginUser, luv);

            // 转化为字符串
            String userInfo = JsonUtils.toJson(luv);
            String path = "/";
            // 清除cookie
            clearCookie(ConstantParam.cookieLoginUser, cookie_domain, response, request);
            // userId  UserLoginVo
            CookieUtils.addCookie(response, ConstantParam.cookieLoginUser, AESUtils.encrypt(userInfo, ConstantParam.cookieLoginPassword), cookie_domain, null, true, path);
        } catch (Exception e) {
            log.error("loginCookie error:" ,e);
        }
    }


    public void clearCookie(String name, String domain, HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(name, request);
        String path = "/";
        CookieUtils.deleteCookie(cookie, response, domain, path);
    }

    public void changePwdData(OfUserDTO dto) throws SmackException.NotConnectedException, XMPPException.XMPPErrorException, SmackException.NoResponseException {
        if(StringUtils.isBlank(dto.getOldPwd()) || StringUtils.isBlank(dto.getConfirmPwd()) || StringUtils.isBlank(dto.getNewPwd())){
            BusinessException.throwMessage("please check you pwd data");
        }

        //checkpwd
        if(!dto.getNewPwd().equals(dto.getConfirmPwd())){
            BusinessException.throwMessage("please check your confirm_new_pwd");
        }
        //
        OfUser ofUser = ofUserMapper.selectByPrimaryKey(dto.getId());
        Blowfish blowfish = new Blowfish(passwordKey);
        String encryptString = blowfish.decryptString(ofUser.getEncryptedpassword());
        if(!dto.getOldPwd().equals(encryptString)){
            BusinessException.throwMessage("please check your old_pwd");
        }
        AbstractXMPPConnection connectionc = imConnection.getConnectionc(ofUser.getUsername());
        AccountManager accountManager = AccountManager.getInstance(connectionc);
        accountManager.changePassword(dto.getNewPwd());


        ofUser.setEncryptedpassword(blowfish.encryptString(dto.getNewPwd()));
        ofUserMapper.updateByPrimaryKeySelective(ofUser);

    }

    public static void main(String[] args) {
        String p = "111111";

        Blowfish blowfish = new Blowfish("24u9ofIW6E60NRa");
        System.out.println(blowfish.encryptString(p));
    }
}
