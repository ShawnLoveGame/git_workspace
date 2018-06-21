package com.ai.smart.third.confiscation.model;
import lombok.Data;
import java.util.Date;
/*
*用户实体
*/
@Data
public class User {

    private Long id;//用户ID
    private String nick_name;//用户昵称
    private String open_id;//openid
    private String head_img;//头像地址
    private Date datetime;//创建（绑定）时间
    private String serial_num;//绑定的电话号码
    private String language;//语言
    private String city;//城市
    private String province;//省份
    private String country;//国家
    private String unionId;//unionId


}
