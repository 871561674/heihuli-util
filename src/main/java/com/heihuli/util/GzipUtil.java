package com.heihuli.util;

import com.heihuli.base.CommonStringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Gzip压缩工具类
 *
 * @author heihuli
 */
public class GzipUtil {

    public static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * byte[] --> byte[]<br>
     * 字节数组压缩,返回压缩后的字节数组
     *
     * @param data 源字节数组
     * @return 压缩后的字节数组
     * @throws IOException
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] result = bos.toByteArray();
        bos.close();
        return result;
    }

    /**
     * String --> byte[]<br>
     * 字符串压缩,返回压缩后的字节数组<br>
     * 默认编码类型 UTF-8
     *
     * @param data 源字符串
     * @param charsetName 编码类型 UTF-8、ISO-8859-1
     * @return 压缩后的字节数组
     * @throws IOException
     */
    public static byte[] compress(String data, String charsetName) throws IOException {
        if (CommonStringUtil.isBlank(charsetName)) {
            charsetName = DEFAULT_ENCODE;
        }
        return compress(data.getBytes(charsetName));
    }

    /**
     * byte[] --> StringEncode<br>
     * 字节数组压缩,返回base64加密字符串<br>
     * 默认编码类型 UTF-8
     *
     * @param date 源字节数组
     * @param charsetName 编码类型 UTF-8、ISO-8859-1
     * @return base64加密字符串
     * @throws IOException
     */
    public static String compressBase64Encode(byte[] date, String charsetName) throws IOException {
        if (CommonStringUtil.isBlank(charsetName)) {
            charsetName = DEFAULT_ENCODE;
        }
        byte[] compressData = compress(new String(date,charsetName), charsetName);
        return Base64.getEncoder().encodeToString(compressData);
    }

    /**
     * String --> StringEncode<br>
     * 字符串压缩,返回base64加密字符串<br>
     * 默认编码类型 UTF-8
     *
     * @param data 源字符串
     * @param charsetName 编码类型 UTF-8、ISO-8859-1
     * @return base64加密字符串
     * @throws IOException
     */
    public static String compressBase64Encode(String data, String charsetName) throws IOException {
        if (CommonStringUtil.isBlank(charsetName)) {
            charsetName = DEFAULT_ENCODE;
        }
        return new String(Base64.getEncoder().encode(compress(data, charsetName)));
    }

    /**
     * byte[] --> byte[]<br>
     * 字节数组解压,返回解压后字节数组
     *
     * @param date 压缩字节数组
     * @return 解压后字节数组
     * @throws IOException
     */
    public static byte[] decompress(byte[] date) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(date);
        GZIPInputStream gzip = new GZIPInputStream(bis);
        byte[] buf = new byte[256];
        int num = -1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((num = gzip.read(buf, 0, buf.length)) != -1) {
            bos.write(buf, 0, num);
        }
        gzip.close();
        bis.close();
        byte[] result = bos.toByteArray();
        bos.flush();
        bos.close();
        return result;
    }

    /**
     * byte[] --> String<br>
     * 字节数组解压,返回解压后的字符串<br>
     * 默认编码类型 UTF-8
     *
     * @param date 压缩字节数组
     * @param charsetName 编码类型 UTF-8、ISO-8859-1
     * @return 解压后的字符串
     * @throws IOException
     */
    public static String decompress(byte[] date, String charsetName) throws IOException {
        if (CommonStringUtil.isBlank(charsetName)) {
            charsetName = DEFAULT_ENCODE;
        }
        return new String(decompress(date), charsetName);
    }

    /**
     * StringEncode --> StringEncode<br>
     * base64字符串解压,返回解压base64字符串<br>
     * 默认编码类型 UTF-8
     *
     * @param data 压缩base64字符串
     * @param charsetName 编码类型 UTF-8、ISO-8859-1
     * @return 解压base64字符串
     * @throws IOException
     */
    public static String base64DecodeDecompress(String data, String charsetName) throws IOException {
        if (CommonStringUtil.isBlank(charsetName)) {
            charsetName = DEFAULT_ENCODE;
        }
        byte[] base64DecodeData = Base64.getDecoder().decode(data);
        return new String(decompress(base64DecodeData), charsetName);
    }

    /**
     * StringEncode --> byte[]<br>
     * base64字符串解压,返回源字节数组<br>
     *
     * @param data 压缩base64字符串
     * @return 源字节数组
     * @throws IOException
     */
    public static byte[] base64DecodeDecompress(String data){
        return Base64.getDecoder().decode(data);
    }

}
