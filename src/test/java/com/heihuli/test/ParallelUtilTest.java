package com.heihuli.test;

import com.heihuli.util.ParallelUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heihuli
 */
public class ParallelUtilTest {

    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger();
        List<String> list1 = new ArrayList<>();
        for (int j = 0; j < 10000; j++) {
            list1.add("a");
        }

        List<String> list2 = ParallelUtil.parallelTask(s -> {
            List<String> list = new ArrayList<>();
            list.add(s + i.getAndIncrement());
            return list;
        }, list1, "heihuli");
        System.out.println(list2);
        System.out.println(list2.size());
    }
}
