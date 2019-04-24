package com.hx.base;

public class BaseConstant {
    public static final String COUNTRY_SENEGAL = "Senegal";
    //通讯协议
    public static final String PROTOCOL_DLMS = "1"; //dlms协议
    public static final String PROTOCOL_21 = "2"; //21协议
    public static final String PROTOCOL_ZIGBEE_645 = "3";//645协议

    //通讯方式
    public static final String METHOD_OPTICAL = "1"; //光电
    public static final String METHOD_RF = "3";//rf
    public static final String METHOD_ZIGBEE = "2";//zigBee

    //主页菜单
    public static final String HOME_MENU_UPGRADE = "1";
    public static final String HOME_MENU_READ = "2";
    public static final String HOME_MENU_SETTING = "3";

    public static final String APP_READ = "READ";
    public static final String APP_SET = "SET";
    public static final String APP_UPGRADE = "UPGRADE";

    //主站1IP、Port和APN组设置
    public final static String APN_OBIS = "1#0-129:25.0.137.255#2";
    //短信中心号码
    public final static String SMS_OBIS = "1#0-0:96.1.213.255#2";

    //PDP用户名和密码组设置
    public final static String PDP_OBIS = "1#0-129:25.0.138.255#2";
}
