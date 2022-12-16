package com.heihuli.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Aes加密工具类
 *
 * @author heihuli
 */
public class AesUtil {

    /**
     * AES加密，ECB加密模式，PKCS5Padding填充
     */
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 密钥模式
     */
    private static final String AES = "AES";

    private static final String SHA_256 = "SHA-256";

    /**
     * Aes加密
     *
     * @param data
     * @param seed
     * @return
     */
    public static String encrypt(String data, String seed) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(seed));
        byte[] bytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Aes解密
     *
     * @param data
     * @param seed
     * @return
     */
    public static String decrypt(String data, String seed) throws Exception {
        byte[] decodeData = Base64.getDecoder().decode(data);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(seed));
        return new String(cipher.doFinal(decodeData), StandardCharsets.UTF_8);
    }

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey(String seed) throws NoSuchAlgorithmException {
        byte[] key = seed.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance(SHA_256);
        key = sha.digest(key);
        // key必须为16位
        key = Arrays.copyOf(key, 16);
        return new SecretKeySpec(key, AES);
    }

}
