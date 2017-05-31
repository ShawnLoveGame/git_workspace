package com.he.im.framework.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ArchivesLogAspect {
    private final Logger logger = Logger.getLogger(this.getClass());

    private HttpServletRequest request = null;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;


    @Before("@annotation(com.he.im.framework.aop.ArchivesLog)")
    public void beforeExce(JoinPoint joinPoint) {
        info(joinPoint);
    }

    /**
     *
     * @Description: 处理切点，记录日志
     * @param joinPoint
     */
    public  void info(JoinPoint joinPoint) {
        request = getHttpServletRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = targetClass.getMethods();
        String operationName = "";
        String operationType = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs!=null&&clazzs.length == arguments.length&&method.getAnnotation(ArchivesLog.class)!=null) {
                    operationName = method.getAnnotation(ArchivesLog.class).operationName();
                    operationType = method.getAnnotation(ArchivesLog.class).operationType();
                    break;
                }
            }
        }

//        user = (BackOperatorVO)request.getAttribute("backendOperatorVO");
//        //记录日志
//        BackOperateLog backLog = new BackOperateLog();
//        backLog.setOperationType(operationType);
//        backLog.setOperationName(operationName);
//        backLog.setOperateId(user.getId());
//        backLog.setOperateName(user.getRealName());
//        backLog.setCreateTime(new Date());
//        backLog.setInputParans(JsonUtils.toJson(showParams(request)));
//
//        LogThread thread = new LogThread(backLog , backOperateLogMapper);
//        taskExecutor.execute(thread);
    }
    /**
     * @Description: 获取request
     * @param
     * @return HttpServletRequest
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    /**
     *
     * @Title：around
     * @Description: 环绕触发
     * @param joinPoint
     * @return Object
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        return null;
    }

    /**
     *
     * @Title：printOptLog
     * @date 2016年11月23日 下午5:10
     */
    /*private void printOptLog() {
        Gson gson = new Gson(); // 需要用到google的gson解析包
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);
        logger.info("user :" +user.getName()+ " start_time: " +  startTime +" end_time: "+endTime);
    }  */
    private Map showParams(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        return map;
    }

}
