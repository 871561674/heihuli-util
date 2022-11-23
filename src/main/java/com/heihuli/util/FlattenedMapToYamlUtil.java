package com.heihuli.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扁平化map转yaml
 * 结合ConvertToFlattenedMapUtil使用
 *
 * @author heihuliliu
 */
public class FlattenedMapToYamlUtil {

    private static final Pattern PATTERN = Pattern.compile("(.+)\\[(\\d+)]$");

    private static StringBuilder YAML = new StringBuilder();

    public static <T>  String handle(Map<String, T> map) {
        try {
            List<String> lists = new ArrayList<>();
            map.entrySet().stream().forEach(entry -> lists.add(entry.getKey() + "=" + entry.getValue()));
            Node root = new Node();
            for (String list : lists) {
                handleLine(root, list);
            }
            for (Map.Entry<String, Node> entry : root.map.entrySet()) {
                printYaml(entry.getKey(), entry.getValue(), 0);
            }
            return YAML.toString();
        }finally {
            YAML.delete(0, YAML.length());
        }
    }

    private static void printYaml(String name, Node node, int num) {
        if (node.value != null) {//普通值
            if (name != null) {
                print(num, name + ": " + node.value);
            } else {
                print(num, "- " + node.value);
            }
        } else if (node.map != null) {// map
            if (name != null) {
                print(num, name + ":");
                for (Map.Entry<String, Node> entry : node.map.entrySet()) {
                    printYaml(entry.getKey(), entry.getValue(), num + 2);
                }
            } else {
                // 注意第一次
                boolean first = true;
                for (Map.Entry<String, Node> entry : node.map.entrySet()) {
                    if (first) {
                        first = false;
                        printYaml("- " + entry.getKey(), entry.getValue(), num);
                    } else {
                        printYaml(entry.getKey(), entry.getValue(), num + 2);
                    }
                }
            }
        } else {//数组情况
            print(num, name + ":");
            for (Node chid : node.list) {
                printYaml(null, chid, num + 2);
            }
        }
    }

    private static void print(int num, String msg) {
        for (int i = 0; i < num; i++) {
            YAML.append(' ');
        }
        YAML.append(msg + "\n");
    }

    private static void handleLine(Node root, String line) {
        int index = line.indexOf('=');
        if (index == -1) return;
        String[] names = line.substring(0, index).split("\\.");
        next(root, names, 0, line.substring(index + 1));
    }

    private static void next(Node node, String[] names, int index, String value) {
        if (index < names.length) {
            next(node.getOrAdd(names[index]), names, index + 1, value);
        } else {
            node.value = value;
        }
    }

    static class Node {

        private Map<String, Node> map;
        private List<Node> list;
        private String value;

        public Node getOrAdd(String name) {
            Matcher matcher = PATTERN.matcher(name);
            if (matcher.find()) {
                Node node = handleMap(matcher.group(1));
                return node.handleList(Integer.parseInt(matcher.group(2)));
            } else {
                return handleMap(name);
            }
        }

        private Node handleList(int index) {
            if (list == null) list = new ArrayList<>();
            while (list.size() <= index) {
                list.add(new Node());
            }
            return list.get(index);
        }

        private Node handleMap(String name) {
            if (map == null) map = new TreeMap<>();
            if (!map.containsKey(name)) {
                map.put(name, new Node());
            }
            return map.get(name);
        }

    }
}
