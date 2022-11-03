package com.heihuli.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 找出两个list中的不同元素，推荐使用方法1，map
 * @author heihuliliu
 */
public class TwoListDiffer {

    @Test
    public void test() {
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
        function1(list1, list2);
        // 方法2、3效率太低
//        function2(list1, list2);
//        function3(list1, list2);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");
    }

    // 方法1 用map,
    private void function1(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        // list1作为map的key插入，value为1，记录一次
        for (String s: list1) {
            map.put(s, 1);
        }
        // list2插入map时，判断是否存在，已存在说明有重复，value设置为2。不存在则value为1
        for (String s: list2) {
            if (map.get(s) != null) {
                map.put(s, 2);
            } else {
                map.put(s, 1);
                // 直接放入diff会涉及扩容，影响效率
//                diff.add(s);
            }
        }
        // 遍历map获取value为1的key就是不同的元素
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println(diff);
    }

    @Deprecated
    // 方法2 两次循环数组+contains方法
    private void function2(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        for (String s: list1) {
            if (!list2.contains(s)) {
                diff.add(s);
            }
        }
        for (String s: list2) {
            if (!list1.contains(s)) {
                diff.add(s);
            }
        }

        System.out.println(diff);
    }

    @Deprecated
    // 方法3 removeAll()+retainAll()
    private void function3(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<>();
        List<String> res = new ArrayList<>();
        // 先求两个list交集
        diff.addAll(list1);
        diff.retainAll(list2);
        // 再求两个list合集
        res.addAll(list1);
        res.addAll(list2);
        // 用合集去掉交集，就是两个不同元素
        res.removeAll(diff);
        System.out.println(res);
    }
}
