package com.heihuli.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heihuli
 *
 * 找出两个list中的不同元素
 */
public class TwoListDifferUtil {

    /**
     *
     * @return 返回两集合不同元素的list集合，两个集合都为空时，返回null
     */
    public static <T> List<T> findDiff(List<T> l1, List<T> l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        if (l1 == null && l2 != null) {
            return l2;
        }
        if (l2 == null && l1 != null) {
            return l1;
        }
        List<T> diff = new ArrayList<>();
        Map<T, Integer> map = new HashMap<>();
        // l1作为map的key插入，value为1，记录一次
        for (T s: l1) {
            map.put(s, 1);
        }
        // l2插入map时，判断是否存在，已存在说明有重复，value设置为2。不存在则value为1
        for (T s: l2) {
            if (map.get(s) != null) {
                map.put(s, 2);
            } else {
                map.put(s, 1);
                // 直接放入diff会涉及扩容，影响效率
//                diff.add(s);
            }
        }
        // 遍历map获取value为1的key就是不同的元素
        for (Map.Entry<T, Integer> entry: map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println(diff);
        return diff;
    }

    // 方法3 removeAll()+retainAll()
    @Deprecated
    public static <T> List<T> function3(List<T> l1, List<T> l2) {
        List<T> diff = new ArrayList<>();
        List<T> res = new ArrayList<>();
        // 先求两个list交集
        diff.addAll(l1);
        diff.retainAll(l2);
        // 再求两个list合集
        res.addAll(l1);
        res.addAll(l2);
        // 用合集去掉交集，就是两个不同元素
        res.removeAll(diff);
        System.out.println(res);
        return res;
    }

    // 方法2 两次循环数组+contains方法
    @Deprecated
    public static <T> List<T> function2(List<T> l1, List<T> l2) {
        List<T> diff = new ArrayList<>();
        for (T s: l1) {
            if (!l2.contains(s)) {
                diff.add(s);
            }
        }
        for (T s: l2) {
            if (!l1.contains(s)) {
                diff.add(s);
            }
        }
        System.out.println(diff);
        return diff;
    }
}
