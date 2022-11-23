package com.heihuli.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256摘要算法
 *
 * @author heihuli
 */
public class SHA256Util {

    /**
     * String --> byte[]
     * 计算SHA-256摘要
     * 
     * @param content 原文
     * @return 摘要
     * @throws NoSuchAlgorithmException 算法不存在时抛出
     * @throws UnsupportedEncodingException
     */
    public static byte[] getByte(String content) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digester = MessageDigest.getInstance("SHA-256");
        digester.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] hex = digester.digest();
        return hex;
    }

    /**
     * byte[] --> String
     *
     * @param bytes
     * @return
     */
    public static String getStr(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            // 0x代表16进制数,0xff表示的数二进制1111 1111 占一个字节,和其进行&操作的数,最低8位,不会发生变化
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    /**
     * String --> String
     *
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String encode(String content) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = getByte(content);
        return getStr(bytes);
    }
}
