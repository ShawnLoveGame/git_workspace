/*
 * BackOperatorMapper.java
 * Copyright(C) 20xx-20xx  海外U
 * All rights reserved.
 * -----------------------------------------------
 * 2016-12-05 Created
 */
package com.he.exam.dao.exam;

import com.he.exam.model.backoperator.BackOperator;
import com.he.exam.model.backoperator.dto.BackendOperatorDTO;
import com.he.exam.model.backoperator.vo.BackOperatorVO;

import java.util.List;

public interface BackOperatorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BackOperator record);

    int insertSelective(BackOperator record);

    BackOperatorVO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BackOperator record);

    int updateByPrimaryKey(BackOperator record);

    BackOperatorVO findBackendOperatorByName(String userName);

    void updateBackendOperatorAfterLogin(BackendOperatorDTO backendOperatorDTO);

    BackOperatorVO findBackendOperatorByDTO(BackendOperatorDTO backendOperatorDTO);

    int countFindBackUserWithPg(BackendOperatorDTO backendOperatorDTO);

    List<BackOperatorVO> findBackUserWithPg(BackendOperatorDTO backendOperatorDTO);

    List<BackOperatorVO> findBackendOperatorVOs(BackendOperatorDTO backendOperatorDTO);

    int deleteBackOperatorByDTO(BackendOperatorDTO backendOperatorDTO);
}