package com.heihuli.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author heihuli
 *
 * MD5工具类
 */
public class MD5Util {

    /**
     * 使用md5加密 文件
     *
     * @param file 加密的文件
     * @param length 加密的长度 最大长度32
     * @return
     */
    public static String getMd5(File file, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[1024 * 1024 << 1];
            FileInputStream fileInputStream = new FileInputStream(file);
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fileInputStream.close();

            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;//相当于  i = i & 0xff;
                //i转成16进制后，小于16就要补0
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            if (length > 32) {
                return buf.toString();
            }
            return buf.toString().substring(0, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 使用md5加密 文件流
     *
     * @param fileInputStream 加密的文件流
     * @param length 加密的长度 最大长度32
     * @return
     */
    public static String getMd5(FileInputStream fileInputStream, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] buffer = new byte[1024 * 1024 << 1];
            int len;
            while ((len = fileInputStream.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fileInputStream.close();
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            if (length > 32) {
                return buf.toString();
            }
            return buf.toString().substring(0, length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 使用md5加密 文本
     *
     * @param plainText 加密的内容
     * @param length 加密的长度 最大长度32
     * @return
     */
    public static String getMd5(String plainText, int length) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            if (length > 32) {
                return buf.toString();
            }
            return buf.toString().substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

