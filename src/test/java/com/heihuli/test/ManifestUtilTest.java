package com.heihuli.test;

import com.heihuli.util.ManifestUtil;
import org.junit.Test;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author heihuli
 */
public class ManifestUtilTest {

    @Test
    public void test() {
        Manifest manifest = ManifestUtil.getManifest();
        Attributes attributes = manifest.getMainAttributes();
        System.out.println(attributes.entrySet());
    }
}
