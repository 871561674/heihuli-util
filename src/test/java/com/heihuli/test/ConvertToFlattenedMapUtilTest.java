package com.heihuli.test;

import com.heihuli.util.ConvertToFlattenedMapUtil;
import com.heihuli.util.CommonJacksonUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heihuli
 */
public class ConvertToFlattenedMapUtilTest {

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        List<Object> list = new ArrayList<>();
        list.add(123);
        list.add(true);
        list.add(null);
        list.add("");
        map3.put("subject", list);
        map2.put("study", map3);
        map.put("heihuli", map2);
        System.out.println(CommonJacksonUtil.OM.valueToTree(map).toString());
        // {"heihuli":{"study":{"subject":[123,true]}}}

        Map<String, Object> result = ConvertToFlattenedMapUtil.getFlattenedMap(map);
        System.out.println(result);
        // {heihuli.study.subject[0]=123, heihuli.study.subject[1]=true}
        System.out.println(CommonJacksonUtil.OM.valueToTree(result).toString());
        // {"heihuli.study.subject[0]":123,"heihuli.study.subject[1]":true}

    }
}
