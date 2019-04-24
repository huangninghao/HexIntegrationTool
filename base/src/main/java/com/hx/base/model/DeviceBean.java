package com.hx.base.model;

import java.io.Serializable;

public class DeviceBean implements Serializable {
    public String deviceNo;
    public String nameEn;
    public String nameZh;
    public String nameFr;
    public String imgUrl;
    public int sort;
    public String commMethod;//1光电 3 RF
    public String protocol;//1 dlms 2 21协议 3 645协议
    public int status;
    public String chip;//电表芯片  0=TDK \ 1=RN8213
}
