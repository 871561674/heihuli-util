package com.heihuli.domain;

/**
 * @author heihuli
 */
public class MapObj {
    private String id;
    private String name;

    public MapObj() {
    }

    public MapObj(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}