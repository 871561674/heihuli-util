package com.heihuli.util;

import org.springframework.util.StringUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author heihuliliu
 *
 * 树状map扁平化
 * 例如：正常map{heihuli={study={subject=science}}}
 * 将其转换为扁平map效果：{heihuli.study.subject=science}
 * properties文件一般使用扁平化配置，如heihuli.study.subject=science
 *
 * 可以结合FlattenedMapToYamlUtil使用将扁平化map转为yaml
 *
 */
public class ConvertToFlattenedMapUtil {

    public static final <T> Map<String, T> getFlattenedMap(Map<String, T> source) {
        Map<String, T> result = new TreeMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    private static <T> void buildFlattenedMap(Map<String, T> result, Map<String, T> source, String path) {
        for (Map.Entry<String, T> entry : source.entrySet()) {
            String key = String.valueOf(entry.getKey());
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                }
                else {
                    key = path + '.' + key;
                }
            }
            T value = entry.getValue();
            if (value instanceof String) {
                result.put(key, value);
            }
            else if (value instanceof Map) {
                Map<String, T> map = (Map<String, T>) value;
                buildFlattenedMap(result, map, key);
            }
            else if (value instanceof Collection) {
                Collection<T> collection = (Collection<T>) value;
                int count = 0;
                for (T object : collection) {
                    buildFlattenedMap(result,
                            Collections.singletonMap("[" + (count++) + "]", object), key);
                }
            }
            else {
                result.put(key, (value != null ? value : (T) ""));
            }
        }
    }
}

