package com.heihuli.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * List工具类
 *
 * @author heihuli
 */
public class CommonListUtil {

    /**
     * 提取实体列表的某个字段列表,所有异常都会导致当次循环跳过
     *
     * @param list 实体列表
     * @param getMethodName 获取字段的方法名
     * @return 实体字段列表
     */
    public static <T> List<String> listToFieldList(List<? extends T> list, String getMethodName) {
        // list为null时返回null
        if (null == list) {
            return null;
        }
        List<String> list1 = new ArrayList<>();
        // 遍历list
        for (T entity : list) {
            if (null != entity) {
                try {
                    Object value = entity.getClass().getMethod(getMethodName).invoke(entity);
                    if (null != value) {
                        list1.add(String.valueOf(value));
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    // 异常时跳过
                }
            }
        }
        // 返回字段列表
        return list1;
    }

    /**
     * 提取去重的实体列表的某个字段列表,所有异常都会导致当次循环跳过
     *
     * @param list 实体列表
     * @param getMethodName 获取字段的方法名
     * @return 实体字段列表
     */
    public static <T> List<String> listToUniqueFieldList(List<? extends T> list, String getMethodName) {
        // list为null时返回null
        if (null == list) {
            return null;
        }
        Set<String> set = new HashSet<>();
        // 遍历list
        for (T entity : list) {
            if (null != entity) {
                try {
                    Object idValue = entity.getClass().getMethod(getMethodName).invoke(entity);
                    if (null != idValue) {
                        set.add(String.valueOf(idValue));
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    // 异常时不处理
                }
            }
        }
        // 返回字段列表
        return new ArrayList<>(set);
    }
}
