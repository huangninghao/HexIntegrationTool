package com.hx.read.model;

import java.io.Serializable;

public class ParameterItem implements Serializable {
    public String paramName;
    public boolean isSelect = false;
    public int id;
    public String obis;
    public String classid;
    public String attr;
    public ParameterItem(String paramName, boolean isSelect, int id, String obis, String classid, String attr) {
        this.paramName = paramName;
        this.isSelect = isSelect;
        this.id = id;
        this.obis =obis;
        this.classid = classid;
        this.attr = attr;
    }
}
