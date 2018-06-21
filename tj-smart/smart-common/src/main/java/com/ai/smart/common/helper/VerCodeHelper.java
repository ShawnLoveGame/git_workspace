package com.ai.smart.common.helper;

import java.util.Random;

public class VerCodeHelper {

    private static String randString = "0123456789";//随机产生只有数字的字符串 private String

    private static int stringNum = 4;// 随机产生字符数量

    private static Random random = new Random();

    /**
     * 获取随机的字符
     */
    public static String getRandomString() {
        StringBuffer ss = new StringBuffer();
        for(int i=0;i<stringNum;i++){
            int num = random.nextInt(randString.length());
            ss.append(String.valueOf(randString.charAt(num)));
        }
        return ss.toString();
    }
}
