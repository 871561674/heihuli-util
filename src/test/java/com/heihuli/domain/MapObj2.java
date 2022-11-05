package com.heihuli.domain;

/**
 * @author heihuli
 */
public class MapObj2 {
    private int id;
    private String name;
    private String tel;

    public MapObj2() {
    }

    public MapObj2(int id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
