package com.ai.smart.bottom.user.mapper;

import com.ai.smart.bottom.user.model.BottomMaUser;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>BottomMaUserMapper 类<br>
 * <b>日期：</b> 2018-05-04 15:12:16 <br>
 */
public interface BottomMaUserMapper {

    /**
     * 主键查询数据
     */
    BottomMaUser selectByPrimaryKey(Long id);


    BottomMaUser selectByOpenId(String openId);

    /**
     * 新增数据
     */
    int insert(BottomMaUser record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(BottomMaUser record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(BottomMaUser record);

    BottomMaUser findBySeaialNum(BottomMaUser record);

    int clearSerialNum(BottomMaUser record);

    List<BottomMaUser> batchFindByIds(List<Long> list);

}
