package com.heihuli.test;

import com.heihuli.domain.MapObj;
import com.heihuli.domain.MapObj2;
import com.heihuli.util.CommonJacksonUtil;
import com.heihuli.util.CommonMapUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heihuli
 */
public class CommonMapUtilTest {
    /**
     * CommonMapUtil.listHashMap(List<T> list, String getMethodName)
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
        Map<String, MapObj> map = CommonMapUtil.listHashMap(list, "getName");
        System.out.println(CommonJacksonUtil.OM.valueToTree(map));
        // {"heihuli2":{"id":"2","name":"heihuli2"},"heihuli3":{"id":"3","name":"heihuli3"},"heihuli1":{"id":"1","name":"heihuli1"}}
    }

    /**
     * CommonMapUtil.getMapValue(Boolean ignoreNullValue, Object... objs)
     * 将N个对象转换为一个Map，同名变量会被覆盖
     */
    @Test
    public void getMapValueTest() {
        MapObj o1 = new MapObj("1", "heihuli1");
        MapObj2 o2 = new MapObj2(2, "heihuli2", "13333333333");
        Map<String, Object> map = CommonMapUtil.getMapValue(false, o1, o2);
        System.out.println(CommonJacksonUtil.OM.valueToTree(map));
        // {"name":"heihuli2","tel":"13333333333","id":2}
    }

    /**
     * CommonMapUtil.deepMergeAnyKeyType(Map<K, Object> map1, Map<K, Object> map2)
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

        Map<String, Object> mergeMap = CommonMapUtil.deepMergeAnyKeyType(map1, map2);
        System.out.println(CommonJacksonUtil.OM.valueToTree(mergeMap));
        // {"subMap":{"map2":"heihuli-map4","map":"heihuli-map3"},"map":"heihuli-map1"}
    }

}
