package com.ai.smart.bottom.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginUser {

    @ApiModelProperty(notes = "id")
    private Long id;
    @ApiModelProperty(notes = "openId")
    private String openId;
    @ApiModelProperty(notes = "用户昵称")
    private String nickName;
    @ApiModelProperty(notes = "用户手机号码")
    private String serialNumber;
    @ApiModelProperty(notes = "用户头像")
    private String headImg;
    @ApiModelProperty(notes = "用户Token")
    private String token;


    public static LoginUser mork(String mock){
        return new LoginUser();
    }
}
