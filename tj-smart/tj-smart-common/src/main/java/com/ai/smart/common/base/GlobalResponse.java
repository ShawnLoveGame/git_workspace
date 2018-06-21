package com.ai.smart.common.base;


import com.ai.smart.common.enums.GolbalResponseCodeEnum;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GlobalResponse<T> {


    @ApiModelProperty(notes = "默认全部为true")
    protected boolean success = true;

    @ApiModelProperty(notes = "数据")
    private T data;
    @ApiModelProperty(notes = "不为空。等于200时表示业务成功，其他表示业务失败")
    private int statusCode = 200;
    @ApiModelProperty(notes = "错误信息，如果不为空，展示给用户")
    private String alertMsg;



    public GlobalResponse() {

    }

    public GlobalResponse(T data) {
        this.data = data;
        this.alertMsg="操作成功";
    }

    public GlobalResponse(T data,String alertMsg) {
        this.data = data;
        this.alertMsg=alertMsg;
    }

    public static <T> GlobalResponse<T> success(T data,String alertMsg) {
        return new GlobalResponse<>(data,alertMsg);
    }

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(data);
    }

    public static <T> GlobalResponse<T> fail(String alertMsg){
        GlobalResponse<T> resp = new GlobalResponse<T>();
        resp.setStatusCode(GolbalResponseCodeEnum.FAIL.getCode());
        resp.setSuccess(false);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg)? GolbalResponseCodeEnum.FAIL.getDesc() :alertMsg);
        return resp;
    }

    public static <T> GlobalResponse<T> fail(String alertMsg,int code){
        GlobalResponse<T> resp = new GlobalResponse<T>();
        resp.setStatusCode(code);
        resp.setSuccess(false);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg)? GolbalResponseCodeEnum.FAIL.getDesc() :alertMsg);
        return resp;
    }

    public static <T> GlobalResponse<T> exception(Throwable e,String alertMsg) {
        GlobalResponse<T> resp = new GlobalResponse<T>();
        resp.setStatusCode(GolbalResponseCodeEnum.EXCEPTION.getCode());
        resp.setSuccess(false);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg)? GolbalResponseCodeEnum.EXCEPTION.getDesc() :alertMsg);
        return resp;
    }

    public static <T> GlobalResponse<T> exception(Throwable e,String alertMsg,int statusCode) {
        GlobalResponse<T> resp = new GlobalResponse<T>();
        resp.setStatusCode(statusCode);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg)? GolbalResponseCodeEnum.EXCEPTION.getDesc() :alertMsg);
        return resp;
    }

}
