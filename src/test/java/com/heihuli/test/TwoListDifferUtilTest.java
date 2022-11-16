package com.heihuli.test;

import com.heihuli.util.TwoListDifferUtil;
import org.junit.Test;

import java.util.*;

/**
 * @author heihuli
 *
 * 测试类
 */
public class TwoListDifferUtilTest {

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

}
