package com.hx.base.model;

import java.io.Serializable;

/**
 * Created by HEN022
 * on 2018/6/19.
 */

public class GPRSBean implements Serializable {
    private String apn;
    private String ip;
    private String port;
    private String sms;
    private String pdpName;
    private String pdpPassword;
    private String collectorIp;
    private String collectorPort;

    public String getCollectorIp() {
        return collectorIp;
    }

    public void setCollectorIp(String collectorIp) {
        this.collectorIp = collectorIp;
    }

    public String getCollectorPort() {
        return collectorPort;
    }

    public void setCollectorPort(String collectorPort) {
        this.collectorPort = collectorPort;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getPdpName() {
        return pdpName;
    }

    public void setPdpName(String pdpName) {
        this.pdpName = pdpName;
    }

    public String getPdpPassword() {
        return pdpPassword;
    }

    public void setPdpPassword(String pdpPassword) {
        this.pdpPassword = pdpPassword;
    }
}
