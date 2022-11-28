package com.heihuli.test;

import com.heihuli.util.RSAUtil;
import org.junit.Test;

/**
 * @author heihuli
 */
public class RSAUtilTest {

    /**
     * RSAUtil.genKeyPair
     * RSAUtil.encrypt
     * RSAUtil.decrypt
     *
     * @throws Exception
     */
    @Test
    public void test() throws Exception {

        RSAUtil.RsaKey keyPair = RSAUtil.genKeyPair("heihuli");
        String publicKey = keyPair.getPublicKey();
        System.out.println(publicKey);
        String privateKey = keyPair.getPrivateKey();
        System.out.println(privateKey);

        String encrypt = RSAUtil.encrypt("黑狐狸1号-heihuli", publicKey);
        System.out.println(encrypt);

        String decrypt = RSAUtil.decrypt(encrypt, privateKey);
        System.out.println(decrypt);
        // 黑狐狸1号-heihuli

    }
}
