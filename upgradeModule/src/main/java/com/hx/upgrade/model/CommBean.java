package com.hx.upgrade.model;

import java.io.Serializable;

/**
 * @author by HEN022
 * on 2018/11/19.
 * 通讯配置
 */
public class CommBean implements Serializable {
    private int upgradeType; //升级方式 0 = 本地；1 = 远程
    private int commAgr; //通讯协议2=645协议 1 = 21协议；0 = DLMS协议
    private int commType; //通讯方式 0 = 光电通讯； 1 = RF通讯
    private int meterMCU = 0; //芯片类型 0 = TDK 1 = RN8213 （用于处理升级文件）

    public CommBean() {
    }

    public int getMeterMCU() {
        return meterMCU;
    }

    public void setMeterMCU(int meterMCU) {
        this.meterMCU = meterMCU;
    }

    public int getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(int upgradeType) {
        this.upgradeType = upgradeType;
    }

    public int getCommAgr() {
        return commAgr;
    }

    public void setCommAgr(int commAgr) {
        this.commAgr = commAgr;
    }

    public int getCommType() {
        return commType;
    }

    public void setCommType(int commType) {
        this.commType = commType;
    }
}
