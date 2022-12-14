package com.heihuli.util;

import java.io.Closeable;

/**
 * 流处理工具类
 *
 * @author heihuli
 */
public class StreamUtil {

    /**
     * 关闭Closeable接口实现
     *
     * @param closes 可关闭的内容，允许批量
     */
    public static void closeAll(Closeable... closes) {
        if (null != closes && closes.length > 0) {
            for (Closeable c : closes) {
                if (null != c) {
                    try {
                        c.close();
                    } catch (Exception e) {
                        // ignore error
                    }
                }
            }
        }
    }
}
