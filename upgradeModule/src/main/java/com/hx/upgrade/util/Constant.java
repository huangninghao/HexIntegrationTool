package com.hx.upgrade.util;

import com.hexing.libhexbase.cache.StringCache;
import com.hx.upgrade.model.UpgradeFile;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static List<UpgradeFile> upgradeFiles = new ArrayList<>(); //选中的文件放在这里
    public final static String FIRMWARE_VERSION = "1#1.0.0.2.8.255#2"; //读取固件版本号
    public final static String CHECK_CODE = "1#0.0.96.2.130.255#2"; //校验码
    public final static String READ_PRE_FRAME = "18#0.0.44.0.0.255#2";//每帧数据长度 Step1
    public final static String EXECUTE_ACTIVATION_TIME = "001200002c0000ff04";// "18#0.0.44.0.0.255#4"; //设置激活时间
    public final static String READ_METER_NUMBER = "1#0.0.96.1.0.255#2";//读表号
    public final static String CLEAR = ":000000EF11";   //擦除数据
    public final static String ACTIVATION = "014532024546424428290372"; //激活升级
    public final static String EXECUTE_INIT = "001200002c0000ff01";//"18#0.0.44.0.0.255#1";//远程升级初始化激活
    public final static String WRITE_REMOTELY_UPGRADE_MODE = "18#0.0.44.0.0.255#5"; //升级模式开启 Step0
    //激活表计进入直接通讯式BootLoader状态 本地升级
    public final static String WRITE_LOCAL_METER_MODE = "000101001F806AFF0200";
    public final static String FIRSTFILEDATA = ":020000040000FA"; //文件首帧
    public final static String READ_UPGRADE_RESULT = "18#0.0.44.0.0.255#3";//检查是否有遗漏的文件 Step4
    public final static String EXECUTE_UPGRADE_PACKAGE_OVER = "001200002c0000ff03";//"18#0.0.44.0.0.255#3";校验待升级文件 Step5
    public final static String READ_VERIFICATION_MIRROR = "18#0.0.44.0.0.255#7"; //镜像信息 验证是否正确
    public final static String EXECUTE_SEND_DATA = "001200002c0000ff02";//"18#0.0.44.0.0.255#2"; //传输一个数据块 Step3

}
