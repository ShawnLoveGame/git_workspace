package com.ai.smart.bottom.user.mapper;

import com.ai.smart.bottom.user.model.BottomUserQr;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>BottomUserQrMapper 类<br>
 * <b>日期：</b> 2018-06-06 13:04:27 <br>
 */
public interface BottomUserQrMapper {

    /**
     * 主键查询数据
     */
    BottomUserQr selectByPrimaryKey(Long id);

    /**
     * 新增数据
     */
    int insert(BottomUserQr record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(BottomUserQr record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(BottomUserQr record);

    BottomUserQr findByUserId(Long userId);

}
