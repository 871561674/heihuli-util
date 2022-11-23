package com.heihuli.domain;

import java.util.List;

/**
 * @author heihuli
 */
public class ListObj {

    public static final String RED = "red";

    private int id;

    private String name;

    private List<ListObj> list;

    public ListObj() {
    }

    public ListObj(int id, String name, List<ListObj> list) {
        this.id = id;
        this.name = name;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListObj> getList() {
        return list;
    }

    public void setList(List<ListObj> list) {
        this.list = list;
    }

    public static class Type {
        public static final String BLACK = "black";
    }
}
