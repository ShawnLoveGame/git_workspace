package com.he.exam.webapp.exception;

import com.he.exam.webapp.model.Result;

/**
 * 业务异常处理
 */
public class BusinessException extends BaseException {

    /**
     * <pre>
     * 
     * </pre>
     */
    private static final long serialVersionUID = -4987001305340759909L;

    private BusinessException() {
        super();
    }

    private BusinessException(String errCode) {
        super(errCode);
    }

    private BusinessException(String errCode, String... params) {
        super(errCode, params); // 此处
    }

    private BusinessException(String errCode, String message) {
        super(errCode, message);
    }

    /**
     *
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     *
     */
    public static void throwMessage(String errCode, String... params) {
        throw new BusinessException(String.valueOf(Result.INPUT_AUTO), errCode);
    }

    /**
     * 
     * <pre>
     * 抛出业务逻辑异常信息
     * </pre>
     *
     */
     public static void throwMessageWithCode(String errCode, String message) {
         throw new BusinessException(errCode, message);
     }
}
