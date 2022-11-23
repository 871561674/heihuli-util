package com.heihuli.util;

import java.lang.reflect.Field;

/**
 * 检查工具类
 *
 * @author heihuli
 */
public class CommonCheckUtil {

    /**
     * 检查是否有空值
     *
     * @param objs 参数列表
     * @return true：有空值在参数列表中；false：没有空值在参数列表中
     */
    public static Boolean hasNullIn(Object... objs) {
        if (null == objs) {
            return true;
        }
        for (Object obj : objs) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * 目标数组中是否存在目标对象<br>
     * 使用equals()判断
     *
     * @param t 目标对象
     * @param list 目标数组
     * @return true：包含<br>
     *         false：不包含 | null == obj | null == objs | objs is empty Array<br>
     */
    public static <T> Boolean IsInArray(T t, T[] list) {
        if (null == t || null == list || list.length == 0) {
            return false;
        }
        for (T obj : list) {
            if (null != obj && obj.equals(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 类中是否有某个常量
     *
     * @param obj 需要检查的值
     * @param clazz 类，包括静态类
     * @return true：常量在类中有定义；false：常量在类中无定义
     */
    public static Boolean existConst(Object obj, Class<?> clazz) {
        // 反射静态类中的字段定义
        for (Field f : clazz.getDeclaredFields()) {
            try {
                if (obj.equals(f.get(null))) {
                    return true;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // Doing nothing
            }
        }
        return false;
    }
}
