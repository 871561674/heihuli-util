package com.heihuli.test;

import com.heihuli.util.MD5Util;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author heihuli
 */
public class MD5UtilTest {

    /**
     * MD5Util.getMd5
     */
    @Test
    public void test() throws FileNotFoundException {
        String text = "alkjsidohviohaosifoiashdfoihoaisdhoiahsdoivhioasdhfoisahfoiahsdoihviuahsiufhewiufhoishf";
        System.out.println(MD5Util.getMd5(text, 6));
        // 5460da

        File file = new File("src/test/java/com/heihuli/file/IncludeChinese.txt");
        System.out.println(MD5Util.getMd5(file, 32));
        // 94afc0267f4ddea2f7c9c78159086354

        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println(MD5Util.getMd5(fileInputStream, 32));
        // 94afc0267f4ddea2f7c9c78159086354
    }
}
