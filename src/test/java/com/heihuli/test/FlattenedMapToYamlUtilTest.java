package com.heihuli.test;

import com.heihuli.util.FlattenedMapToYamlUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author heihuli
 */
public class FlattenedMapToYamlUtilTest {
    @Test
    public void test() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("heihuli.study.subject[0]", 123);
        map.put("heihuli.study.subject[1]", true);
        map.put("heihuli.study.subject[2]", "'aaad'");
        System.out.println(map);
        // {heihuli.study.subject[2]='aaad', heihuli.study.subject[0]=123, heihuli.study.subject[1]=true}
        String s = FlattenedMapToYamlUtil.handle(map);
        System.out.println(s);
        // heihuli:
        //  study:
        //    subject:
        //      - 123
        //      - true
        //      - 'aaad'
    }
}
