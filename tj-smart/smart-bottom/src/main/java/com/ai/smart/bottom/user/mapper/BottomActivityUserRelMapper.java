package com.ai.smart.bottom.user.mapper;

import com.ai.smart.bottom.user.model.BottomActivityUserRel;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>BottomActivityUserRelMapper 类<br>
 * <b>日期：</b> 2018-06-07 12:40:34 <br>
 */
public interface BottomActivityUserRelMapper {

    /**
     * 主键查询数据
     */
    BottomActivityUserRel selectByPrimaryKey(Long id);

    /**
     * 新增数据
     */
    int insert(BottomActivityUserRel record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(BottomActivityUserRel record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(BottomActivityUserRel record);

    List<BottomActivityUserRel> findByUserIdAndParentId(BottomActivityUserRel record);

}
