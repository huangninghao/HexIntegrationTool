package com.hx.set.model;

public class TimeItem {

    public String time;
    public String type;
    public boolean isAdd = false;
    public int id = 0;

    public TimeItem(String time, String type,int id) {
        this.time = time;
        this.type = type;
        this.id = id;
    }

    public TimeItem(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public TimeItem(String time, String type, boolean isAdd) {
        this.time = time;
        this.type = type;
        this.isAdd = isAdd;
    }

}
