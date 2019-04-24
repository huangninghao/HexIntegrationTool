package com.hx.read.model;

public class FunctionItem {
    public int imgId;
    public int itemTitle;

    public FunctionItem(int imgId, int itemTitle) {
        this.imgId = imgId;
        this.itemTitle = itemTitle;
    }

    public FunctionItem(int itemTitle) {
        this.itemTitle = itemTitle;
    }

}
