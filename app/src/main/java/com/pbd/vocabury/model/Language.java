package com.pbd.vocabury.model;

public class Language {

    private int id;
    private String name;
    private String code;

    public Language() {
    }

    public Language(String name) {
        this.name = name;
    }

    public Language(String name, String code) {
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
