package com.heihuli.util;

import com.heihuli.base.CommonStringUtil;

/**
 * @author heihuli
 *
 * ID生成器
 */
public class IDGeneratorUtil {

    private static final String RANDOM_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * 随机生成Id, 可能存在重复
     *
     * @param length 随机数长度
     * @param prefix 前缀
     * @param separator 分隔符
     * @return
     */
    public static String nextId(int length, String prefix, String separator) {
        if (length == 0) {
            return "";
        }
        String id = CommonStringUtil.random(length, RANDOM_CHARS);
        String pre = "";
        String sep = "";
        if (CommonStringUtil.isNotBlank(prefix)) {
            pre = prefix;
        }
        if (CommonStringUtil.isNotBlank(separator)) {
            sep = separator;
        }
        return pre + sep + id;
    }

    /**
     * 随机生成Id, 可能存在重复<br>
     * 随机数长度 8
     *
     * @param prefix 前缀
     * @return
     */
    public static String nextId(String prefix) {
        return prefix + "-" + CommonStringUtil.random(8, RANDOM_CHARS);
    }

}
