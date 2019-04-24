package com.hx.read.utils;

/**
 * Created by HEC271
 * on 2018/8/21.
 * 常量
 */

public class Constant {
    public final static int STANDARD_EVENTS = 0;//标准事件
    public final static int FRAUDULENT_EVENTS = 1;//欺诈事件
    public final static int RELAY_EVENT = 2;//继电器事件
    public final static int POWER_GRID_EVENT = 3;//电网事件

    public final static String RELAY_STATUS = "output state of relay";//继电器状态
    public final static String RELAY_OPERATION_REASON = "relay operate reason";//继电器操作原因
    public final static String RELAY_MODE = "control mode of relay";//继电器模式
    public final static String WORKING_MODE = "Meter Work mode";//电表工作模式
    public final static String METER_STATUS = "meter status";//电表状态U32

    public final static String METER_NUMBER = "MeterNumber";//表号 参数
}
