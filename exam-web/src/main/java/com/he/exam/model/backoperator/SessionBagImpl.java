package com.he.exam.model.backoperator;

import com.he.exam.model.backoperator.vo.BackOperatorVO;

/**
 * 
 * Class Name : 存放登录信息
 * 
 * Description : 这里记述class说明<br>
 * 
 * @see
 *
 */
public class SessionBagImpl {

    private BackOperatorVO backOperatorVO;

    public BackOperatorVO getBackOperatorVO() {
        return backOperatorVO;
    }

    public void setBackOperatorVO(BackOperatorVO backOperatorVO) {
        this.backOperatorVO = backOperatorVO;
    }
}
