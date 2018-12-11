package com.zzr.thrid.sftp;

import java.util.HashMap;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class SFTPTest {

    public SFTPChannel getSFTPChannel() {
        return new SFTPChannel();
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
            SFTPTest test = new SFTPTest();

            Map<String, String> sftpDetails = new HashMap<String, String>();
            // 设置主机ip，端口，用户名，密码
//            sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "172.25.47.80");
            sftpDetails.put(SFTPConstants.SFTP_REQ_HOST, "10.240.50.221");
            sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "lab-flow");
//            sftpDetails.put(SFTPConstants.SFTP_REQ_USERNAME, "root");
//            sftpDetails.put(SFTPConstants.SFTP_REQ_PASSWORD, "sftp@123");
            sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "20000");
//            sftpDetails.put(SFTPConstants.SFTP_REQ_PORT, "22");

            String src = "E:\\zzr\\zzr\\sample.txt"; // 本地文件名
            String dst = "/upload/"; // 目标文件名

            SFTPChannel channel = test.getSFTPChannel();
            ChannelSftp chSftp = channel.getChannel(sftpDetails, 60000);
//            chSftp.mkdir(dst);
//            mkdir(chSftp,dst);
//            System.out.println(3333);
            /**
             * 代码段1
             OutputStream out = chSftp.put(dst, ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
             byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
             int read;
             if (out != null) {
             System.out.println("Start to read input stream");
             InputStream is = new FileInputStream(src);
             do {
             read = is.read(buff, 0, buff.length);
             if (read > 0) {
             out.write(buff, 0, read);
             }
             out.flush();
             } while (read >= 0);
             System.out.println("input stream read done.");
             }
             **/


            chSftp.put(src, dst, ChannelSftp.OVERWRITE); // 代码段2
            // chSftp.put(new FileInputStream(src), dst, ChannelSftp.OVERWRITE); // 代码段3
            chSftp.quit();
            channel.closeChannel();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void mkdir(ChannelSftp chSftp,String path){
        try {
            chSftp.mkdir(path);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}