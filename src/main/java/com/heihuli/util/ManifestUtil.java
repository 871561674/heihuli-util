package com.heihuli.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 获取当前应用自身的Manifest对象
 *
 * @author heihuli
 */
public class ManifestUtil {
    /**
     * 获取当前应用自身的Manifest对象
     *
     * @return Manifest对象
     */
    public static Manifest getManifest() {
        InputStream is = null;
        try {
            Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader()
                    .getResources(JarFile.MANIFEST_NAME);
            URL url = null;
            while (resEnum.hasMoreElements()) {
                try {
                    url = resEnum.nextElement();
                    if (url.toString().substring(url.toString().indexOf("!")).startsWith("!/META-INF/MANIFEST.MF")) {
                        break;
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
            is = url.openStream();
            if (is != null) {
                return new Manifest(is);
            }
        } catch (Exception e) {
            // ignore
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return null;
    }
}
