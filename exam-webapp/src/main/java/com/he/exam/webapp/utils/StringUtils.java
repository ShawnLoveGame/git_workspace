package com.he.exam.webapp.utils;


/**
 * 字符串工具类
 *
 * @author liujiangping
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * list string 判断是否为空
     * @param arr
     * @return
     */
    public static boolean isNotStringListBlank(String... arr){
        if (arr == null) {
            return false;
        }
        for (String string : arr) {
            if (isBlank(string) || "[]".equals(string)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否全部为空
     *
     * @param arr
     * @return
     */
    public static boolean isAllBlank(String... arr) {
        if (arr == null) {
            return true;
        }
        for (String string : arr) {
            if (isNotBlank(string)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 是否全部不为空
     *
     * @param arr
     * @return
     */
    public static boolean isAllNotBlank(String... arr) {
        if (arr == null) {
            return false;
        }
        for (String string : arr) {
            if (isBlank(string)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 是否包含一个为空
     *
     * @param arr
     * @return
     */
    public static boolean isOrBlank(String... arr) {
        if (arr == null) {
            return true;
        }
        for (String string : arr) {
            if (isBlank(string)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 是否包含一个不为空
     *
     * @param arr
     * @return
     */
    public static boolean isOrNotBlank(String... arr) {
        if (arr == null) {
            return false;
        }
        for (String string : arr) {
            if (isNotBlank(string)) {
                return true;
            }
        }
        return false;
    }
}
