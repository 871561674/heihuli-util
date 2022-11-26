package com.heihuli.test;

import com.heihuli.util.ZipUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author heihuli
 */
public class ZipUtilTest {

    @Test
    public void test() throws Exception {
//        String zip = ZipUtil.zip("src/test/java/com/heihuli/file/IncludeChinese.txt");
        List<String> list = new ArrayList<>();
        list.add("src/test/java/com/heihuli/file/IncludeChinese.txt");
        list.add("src/test/java/com/heihuli/file/json.txt");
        list.add("src/test/java/com/heihuli/file/heihuli");
        ZipUtil.zip(list, "src/test/java/com/heihuli/file/heihuli.zip");
    }
}
