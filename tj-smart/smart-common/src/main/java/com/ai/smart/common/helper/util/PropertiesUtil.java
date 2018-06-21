package com.ai.smart.common.helper.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {


    public static String getValue(String url,String key){
        Properties prop =  new  Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(url);
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key).trim();
    }
}
