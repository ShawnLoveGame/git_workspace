package com.ai.smart.third.vo;

import lombok.Data;

@Data
public class MainProChgRespVo {
    private String RETFLAG;//返回码 0:用户当月办理过主体产品变更业务 -1:用户当月未办理过主体产品变更业务
    private String RECDATE;//主体产品转换办理时间 yyyy-mm-dd hh24:mi:ss
    private String STARTDATE;//主体产品转换生效时间 yyyy-mm-dd hh24:mi:ss
    private String OLDMAINPRODID;//老主体产品编码
    private String OLDMAINPRODNAME;//老主体产品名称
    private String NEWMAINPRODID;//新主体产品编码
    private String NEWMAINPRODNAME;//新主体产品名称
}
