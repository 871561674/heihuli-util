package com.heihuli;

import com.heihuli.adapter.AbstractJsonAdapter;
import com.heihuli.domain.JacksonObj;
import com.heihuli.domain.MapObj;
import com.heihuli.domain.MapObj2;
import com.heihuli.util.HeihuliJacksonUtil;
import com.heihuli.util.HeihuliMapUtil;
import com.heihuli.util.TwoListDifferUtil;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author heihuli
 *
 * 测试类
 */
public class HeihuliTest {

    /**
     * TwoListDifferUtil.findDiff(List<T> l1, List<T> l2)
     * 返回两集合不同元素的list集合
     */
    @Test
    public void twoListDifferUtilTest() {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        // 插入100w数据
        for (int i = 0; i < 10000000; i++) {
            list1.add(i + "");
            list2.add(i + "");
        }

        list1.add("a");
        list2.add("b");

        System.out.println("start calculate");
        long start = System.currentTimeMillis();
        // 方法1效率极高
        TwoListDifferUtil.findDiff(list1, list2);
        // 方法2、3效率太低
//        function2(list1, list2);
//        function3(list1, list2);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }

    /**
     * HeihuliMapUtil.listHashMap(List<T> list, String getMethodName)
     * 列表转换为以某个字段为key的HashMap
     */
    @Test
    public void listHashMapTest() {
        List<MapObj> list = new ArrayList<>();
        list.add(new MapObj("1", "heihuli1"));
        list.add(new MapObj("2", "heihuli2"));
        list.add(new MapObj("3", "heihuli3"));
        // 空值忽略
        list.add(null);
        Map<String, MapObj> map = HeihuliMapUtil.listHashMap(list, "getName");
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(map));
        // {"heihuli2":{"id":"2","name":"heihuli2"},"heihuli3":{"id":"3","name":"heihuli3"},"heihuli1":{"id":"1","name":"heihuli1"}}
    }

    /**
     * HeihuliMapUtil.getMapValue(Boolean ignoreNullValue, Object... objs)
     * 将N个对象转换为一个Map，同名变量会被覆盖
     */
    @Test
    public void getMapValueTest() {
        MapObj o1 = new MapObj("1", "heihuli1");
        MapObj2 o2 = new MapObj2(2, "heihuli2", "13333333333");
        Map<String, Object> map = HeihuliMapUtil.getMapValue(false, o1, o2);
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(map));
        // {"name":"heihuli2","tel":"13333333333","id":2}
    }

    /**
     * HeihuliMapUtil.deepMergeAnyKeyType(Map<K, Object> map1, Map<K, Object> map2)
     * 深度合并Map，主要用于配置合并场景
     */
    @Test
    public void deepMergeAnyKeyTypeTest() {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();
        Map<String, Object> map4 = new HashMap<>();
        // 第一层验证
        map1.put("map", "heihuli-map1");
        map2.put("map", "heihuli-map2");

        map3.put("map", "heihuli-map3");
        map4.put("map", "heihuli-map4");
        map4.put("map2", "heihuli-map4");

        // 里层为map
        map1.put("subMap", map3);
        map2.put("subMap", map4);

        Map<String, Object> mergeMap = HeihuliMapUtil.deepMergeAnyKeyType(map1, map2);
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(mergeMap));
        // {"subMap":{"map2":"heihuli-map4","map":"heihuli-map3"},"map":"heihuli-map1"}
    }

    @Test
    public void omTest() throws IOException {
        JacksonObj o1 = new JacksonObj();
        o1.setId("1");
        o1.setName("heihuli");
        o1.setDate(new Date());
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(o1));
        // {"id":"1","name":"heihuli","date":"2022-11-05 15:26:24.958+0800"}

        String json = "{\n" +
                "  \"id\": null,\n" +
                "  \"name\": \"heihuli\",\n" +
                "  \"date\": \"2022-11-05 15:24:40.153+0800\"\n" +
                "}";
        JacksonObj o2 = HeihuliJacksonUtil.OM.readValue(json, JacksonObj.class);
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(o2));
        // {"id":null,"name":"heihuli","date":"2022-11-05 15:24:40.153+0800"}

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        FileOutputStream ot = new FileOutputStream("src/test/java/com/heihuli/file/json.txt");
        HeihuliJacksonUtil.convert(bytes, ot, new AbstractJsonAdapter() {});

//        HeihuliJacksonUtil.OM.writeValue(ot, json);
    }

}
