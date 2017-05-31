package com.he.im.util;


import java.util.Random;

/**
 * 字符串工具类
 *
 * @author liujiangping
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


    public static final char[] BASE_CHARS = {'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
            'r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z'};

    private static Random random = new Random();

    /**
     * 生成制定位数的随机 数字/字母 码
     * @param length
     * @return
     */
    public static String genRandamString(int length) {

        int charLength = BASE_CHARS.length;
        StringBuffer buffer = new StringBuffer();

        int charIndex;
        for (int i=0; i<length; i++ ) {
            charIndex = random.nextInt(charLength);
            buffer.append(BASE_CHARS[charIndex]);
        }

        return String.valueOf(buffer);
    }


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
