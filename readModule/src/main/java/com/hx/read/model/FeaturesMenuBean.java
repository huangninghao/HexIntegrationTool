package com.hx.read.model;

import java.io.Serializable;

/**
 * @author by HEC271
 *         on 2018/2/6.
 *         功能菜单bean
 */

public class FeaturesMenuBean implements Serializable {
    private int resourceIdName;
    public int imgResId;
    private int sort;
    private Class<?> cls;
    public int getResourceIdName() {
        return resourceIdName;
    }

    public void setResourceIdName(int resourceIdName) {
        this.resourceIdName = resourceIdName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
