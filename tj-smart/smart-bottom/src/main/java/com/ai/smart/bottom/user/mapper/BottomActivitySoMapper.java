package com.ai.smart.bottom.user.mapper;

import com.ai.smart.bottom.user.model.BottomActivitySo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>BottomActivitySoMapper 类<br>
 * <b>日期：</b> 2018-06-07 17:30:46 <br>
 */
public interface BottomActivitySoMapper {

    /**
     * 主键查询数据
     */
    BottomActivitySo selectByPrimaryKey(Long id);

    /**
     * 新增数据
     */
    int insert(BottomActivitySo record);

    /**
     * 主键动态更新数据
     */
    int updateByPrimaryKeySelective(BottomActivitySo record);

    /**
     * 主键更新数据
     */
    int updateByPrimaryKey(BottomActivitySo record);

    List<BottomActivitySo> findByUserId(Long userId);

    List<BottomActivitySo> findByIv();

    List<BottomActivitySo> findActiviySoPageByUserId(Long userId);

}
