package com.heihuli.test;

import com.heihuli.util.SHA256Util;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author heihuli
 */
public class SHA256UtilTest {

    @Test
    public void test() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(SHA256Util.encode("heihuli"));
        // f7c5047e3a9e60b9928fd26b66023d1d21cea98be0050f484ed5be1530457eb6
    }

}
