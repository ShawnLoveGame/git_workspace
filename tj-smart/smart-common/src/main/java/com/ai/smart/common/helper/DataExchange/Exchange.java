package com.ai.smart.common.helper.DataExchange;

import java.text.DecimalFormat;

public class Exchange {

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "M";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "G";

        }
        return fileSizeString;
    }

    public  String formetFileSize2(long fileS) {

        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
