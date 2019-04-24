package com.hx.upgrade.model;

import java.io.File;
import java.io.Serializable;
/**
 * @author by HEN022
 * on 2018/11/19.
 * 文件
 */
public class UpgradeFile implements Serializable {
    private File file; //文件内容
    private int sort; //排序
    private boolean select = false;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
