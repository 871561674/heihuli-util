package com.heihuli.test;

import com.heihuli.util.AesUtil;
import org.junit.Test;

/**
 * @author heihuli
 */
public class AesUtilTest {

    /**
     * AesUtil.encrypt
     * AesUtil.decrypt
     */
    @Test
    public void test() throws Exception {
        String data = "黑狐狸1号-heihuli";
        String seed = "test";
        String encrypt = AesUtil.encrypt(data, seed);
        System.out.println(encrypt);
        // tsLJ4xyZBQ6u7gm5CaG4S7kRrTz8FUV6ziLydaQ5rWA

        String decrypt = AesUtil.decrypt(encrypt, seed);
        System.out.println(decrypt);
        // 黑狐狸1号-heihuli

    }
}
