package com.ai.smart.common.helper;

import com.ai.smart.common.helper.exception.BusinessException;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


/**
 * @Description : 断言工具类
 */
public class AssertHelper {

    private static final Logger log = LoggerFactory.getLogger(AssertHelper.class);

    public static void notNull(Object data, String message) {
        if (Objects.isNull(data)) {
            throw new BusinessException(message);
        }
    }

    public static void notBlank(String data, String message) {
        if (Strings.isNullOrEmpty(data)) {
            throw new BusinessException(message);
        }
    }

    public static void check(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object data, String message, Integer statusCode) {
        if (Objects.isNull(data)) {
            throw new BusinessException(message,statusCode);
        }
    }

    public static void notBlank(String data, String message, Integer statusCode) {
        if (Strings.isNullOrEmpty(data)) {
            throw new BusinessException(message,statusCode);
        }
    }

    public static void check(boolean expression, String message, Integer statusCode) {
        if (!expression) {
            throw new BusinessException(message,statusCode);
        }
    }
}
