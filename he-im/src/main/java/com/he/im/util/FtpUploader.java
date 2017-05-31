package com.he.im.util;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FtpUploader {

    private static String ENCODING = "UTF-8";

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String url, int port, String username, String password, String path, String filename, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(url, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.changeWorkingDirectory(path);
            ftp.storeFile(filename, input);

            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    public static boolean FTPUpload(String ip, int port, String user, String pwd, String fileName, File srcFile, String remoteDir) {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;

        boolean ans = false;
        try {
            ftpClient.connect(ip, port);

            ftpClient.setControlEncoding(ENCODING);
            FTPClientConfig conf = new FTPClientConfig("UNIX");
            conf.setServerLanguageCode("zh");

            ftpClient.login(user, pwd);
            ftpClient.setBufferSize(1024);

            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return ans;
            }

//      File srcFile = new File(localFile);
            String _filename = new String(fileName.getBytes(ENCODING), "ISO-8859-1");
            String path = new String(remoteDir.getBytes(ENCODING), "ISO-8859-1");
            createDir(ftpClient, path);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.setFileType(2);
            ftpClient.enterLocalPassiveMode();

            fis = new FileInputStream(srcFile);
            if (ftpClient.storeFile(_filename, fis))
                ans = true;
            else {
                ans = false;
            }
            fis.close();
            ftpClient.logout();
        } catch (IOException e) {
            throw new RuntimeException("FTP", e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException("ftp", e);
            }
        }
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new RuntimeException("ftp", e);
        }

        return ans;
    }

    private static void createDir(FTPClient ftpClient, String dir) throws IOException {
        if ((dir == null) || (dir.length() == 0)) {
            return;
        }

        if (!isDirExist(ftpClient, dir)) {
            String path = dir.substring(0, dir.lastIndexOf("/"));
            createDir(ftpClient, path);
            ftpClient.makeDirectory(dir);
        }
    }

    private static boolean isDirExist(FTPClient ftpClient, String dir) {
        try {
            int retCode = ftpClient.cwd(dir);
            return FTPReply.isPositiveCompletion(retCode);
        } catch (Exception e) {
        }
        return false;
    }
}