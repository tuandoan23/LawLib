package com.example.luat.model;

public class LawContent {
    private String key;
    private String title;
    private String type;
    private String data;

    public LawContent() {
    }

    public LawContent(String key, String title, String type, String data) {
        this.key = key;
        this.title = title;
        this.type = type;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
