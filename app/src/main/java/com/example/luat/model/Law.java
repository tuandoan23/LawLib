package com.example.luat.model;

public class Law {
    private String key;
    private String title;
    private String date_issued;
    private String date_updated;

    public Law() {
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

    public String getDate_issued() {
        return date_issued;
    }

    public void setDate_issued(String date_issued) {
        this.date_issued = date_issued;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public Law(String key, String title, String date_issued, String date_updated) {

        this.key = key;
        this.title = title;
        this.date_issued = date_issued;
        this.date_updated = date_updated;
    }
}
