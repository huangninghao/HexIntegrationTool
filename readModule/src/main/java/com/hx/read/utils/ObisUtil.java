package com.hx.read.utils;

/**
 * @author caibinglong
 *         date 2018/1/3.
 *         desc desc
 */

public class ObisUtil {
    public static final String METER_VERSION_OBIS = "1#0.0.96.1.146.255#2"; //
    /**
     * 读取表号 obis
     */
    public final static String READ_METER = "00010000600100FF0200";

    /**
     * 表 设置时间 读时间 写时间
     * Octs_string
     */
    public final static String READ_WRITE_TIME = "8#0.0.1.0.0.255#2";
    /**
     * ASCS 读表
     */
    public final static String READ_METER_NUMBER = "1#0.0.96.1.0.255#2";

    /**
     * 月冻结电能信息 HXE110-KP 也是用的同一个obis
     */
    public final static String HXT300_METER_MONTHLY_FREEZE_POWER_INFORMATION = "7#0.0.98.1.0.255#2";

    /**
     * 月冻结电能信息 HXE115-KP 也是用的同一个obis
     */
    public final static String METER_MONTHLY_FREEZE_POWER_INFORMATION = "7#0.0.98.1.0.255#2";
    /**
     * 预付费月冻结信息
     */
    public final static String METER_MONTH_FREEZE_PREPAID = "7#0.0.99.1.0.255#2";

    /**
     * 日冻结电能信息
     */
    public final static String HXT300_METER_DAILY_FREEZE_POWER_INFORMATION = "7#0.0.98.5.128.255#2";



    /**
     * HXE110-KP 标准事件
     */
    public final static String HXE110KP_STANDARD_EVENT_RECORD_INFORMATION = "7#0.0.99.98.0.255#2";
    /**
     * HXE110-KP 欺诈事件
     */
    public final static String HXE110KP_FRAUDULENT_EVENT_RECORD_INFORMATION = "7#0.0.99.98.1.255#2";
    /**
     * HXE110-KP 继电器事件
     */
    public final static String HXE110KP_RELAY_EVENT_RECORD_INFORMATION = "7#0.0.99.98.2.255#2";
    /**
     * HXE110-KP 电网事件
     */
    public final static String HXE110KP_POWER_GRID_EVENT_RECORD_INFORMATION = "7#0.0.99.98.4.255#2";

    /**
     * 预付费月冻结信息
     */
    public final static String HXE115KP_METER_MONTH_FREEZE_PREPAID = "7#0.0.99.1.0.255#2";
    /**
     * 预付费日冻结信息
     */
    public final static String METER_DAY_FREEZE_PREPAID = "7#0.0.99.2.0.255#2";

    /**
     * 预付费 日冻结电能信息 HXE115-KP 也是用的同一个obis
     */
    public final static String METER_DAILY_FREEZE_POWER_INFORMATION = "7#0.0.98.2.0.255#2";

    /**
     * A相电流
     * 数据类型 U16
     */
    public final static String A_PHASE_CURRENT = "3#1.0.31.7.0.255#2";

    /**
     * B相电流
     * 数据类型 U16
     */
    public final static String B_PHASE_CURRENT = "3#1.0.51.7.0.255#2";

    /**
     * C相电流
     * 数据类型 U16
     */
    public final static String C_PHASE_CURRENT = "3#1.0.71.7.0.255#2";

    /**
     * 零线电流
     */
    public final static String NEUTRAL_CURRENT = "3#1.0.91.7.0.255#2";

    /**
     * 电网频率
     */
    public final static String FREQUENCY = "3#1.0.14.7.0.255#2";

    /**
     * 电表余额
     */
    public final static String BALANCE = "3#1.0.140.129.0.255#2";

    /**
     *
     */
    public final static String TOTAL_RECHARGE_AMOUNT = "3#1.0.140.129.2.255#2";

    /**
     * A相无功功率
     */
    public final static String A_REACTIVE_POWER_PHASE = "3#1.0.23.7.0.255#2";

    /**
     * A相功率因数
     */
    public final static String A_POWER_FACTOR_PHASE = "3#1.0.33.7.0.255#2";

    /**
     * A相电压
     * 数据类型 U16
     * READ
     */
    public final static String A_PHASE_VOLTAGE = "3#1.0.32.7.0.255#2";
    /**
     * B相电压
     * 数据类型 U16
     * READ
     */
    public final static String B_PHASE_VOLTAGE = "3#1.0.52.7.0.255#2";

    /**
     * C相电压
     * 数据类型 U16
     * READ
     */
    public final static String C_PHASE_VOLTAGE = "3#1.0.72.7.0.255#2";

    /**
     * A相有功功率Active power in phase L1
     * 数据类型 U32
     * READ
     */
    public final static String A_PHASE_ACTIVE_POWER = "3#1.0.21.7.0.255#2";
    /**
     * B相有功功率
     * 数据类型 U32
     * READ
     */
    public final static String B_PHASE_ACTIVE_POWER = "3#1.0.41.7.0.255#2";

    /**
     * C相有功功率
     * 数据类型 U32
     * READ
     */
    public final static String C_PHASE_ACTIVE_POWER = "3#1.0.61.7.0.255#2";

    /**
     * 总正向有功功率
     * 数据类型 U16
     * READ
     */
    public final static String POSITIVE_ACTIVE_POWER = "3#1.0.1.7.0.255#2";

    /**
     * 总反向有功功率
     * 数据类型 U16
     * READ
     */
    public final static String REVERSE_ACTIVE_POWER = "3#1.0.2.7.0.255#2";
    /**
     * 总正向无功功率
     * 数据类型 U16
     * READ
     */
    public final static String POSITIVE_REACTIVE_POWER = "3#1.0.3.7.0.255#2";
    /**
     * 总反向无功功率
     * 数据类型 U16
     * READ
     */
    public final static String REVERSE_REACTIVE_POWER = "3#1.0.4.7.0.255#2";

    /**
     * 正向费率x有功电能
     * 3#1.0.1.8.x.255#2
     * 其中 x 是根据支持费率而变化
     * 费率1 x则为1
     * 费率2 x则为2
     * 数据类型U32
     */
    public final static String POSITIVE_ACTIVE_ENERGY_ALL = "3#1.0.1.8.0.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_ONE = "3#1.0.1.8.1.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_TWO = "3#1.0.1.8.2.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_THREE = "3#1.0.1.8.3.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_FOUR = "3#1.0.1.8.4.255#2";

    /**
     * 正向费率x无功电能
     * 3#1.0:3.8.x.255#2
     * 其中 x 是根据支持费率而变化
     * 费率1 x则为1
     * 费率2 x则为2
     * 数据类型U32
     */
    public final static String POSITIVE_REACTIVE_ENERGY_ONE = "3#1.0.3.8.1.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_TWO = "3#1.0.3.8.2.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_THREE = "3#1.0.3.8.3.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_FOUR = "3#1.0.3.8.4.255#2";

    /**
     * 反向费率x有功电能
     * 3#1.0.2.8.x.255#2
     * 其中 x 是根据支持费率而变化
     * 费率1 x则为1
     * 费率2 x则为2
     * 数据类型U32
     */
    public final static String REVERSE_ACTIVE_ENERGY_ALL = "3#1.0.2.8.0.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_ONE = "3#1.0.2.8.1.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_TWO = "3#1.0.2.8.2.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_THREE = "3#1.0.2.8.3.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_FOUR = "3#1.0.2.8.4.255#2";

    /**
     * 反向费率x无功电能
     * 3#1.0.2.8.x.255#2
     * 其中 x 是根据支持费率而变化
     * 费率1 x则为1
     * 费率2 x则为2
     * 数据类型U32
     */
    public final static String REVERSE_REACTIVE_ENERGY_ONE = "3#1.0.2.8.1.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_TWO = "3#1.0.2.8.2.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_THREE = "3#1.0.2.8.3.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_FOUR = "3#1.0.2.8.4.255#2";


    /**
     * 需量
     * 正向有功最大需量 8费率
     * 反向有功最大需量 8费率
     */

    public final static String POSITIVE_TOTAL_ACTIVE_DEMAND = "4#1.0.1.6.0.255#2";
    public final static String POSITIVE_ACTIVE_RATE1_MAXIMUM_DEMAND = "4#1.0.1.6.1.255#2";
    public final static String POSITIVE_ACTIVE_RATE2_MAXIMUM_DEMAND = "4#1.0.1.6.2.255#2";
    public final static String POSITIVE_ACTIVE_RATE3_MAXIMUM_DEMAND = "4#1.0.1.6.3.255#2";
    public final static String POSITIVE_ACTIVE_RATE4_MAXIMUM_DEMAND = "4#1.0.1.6.4.255#2";
    public final static String POSITIVE_ACTIVE_RATE5_MAXIMUM_DEMAND = "4#1.0.1.6.5.255#2";
    public final static String POSITIVE_ACTIVE_RATE6_MAXIMUM_DEMAND = "4#1.0.1.6.6.255#2";
    public final static String POSITIVE_ACTIVE_RATE7_MAXIMUM_DEMAND = "4#1.0.1.6.7.255#2";
    public final static String POSITIVE_ACTIVE_RATE8_MAXIMUM_DEMAND = "4#1.0.1.6.8.255#2";

    public final static String REVERSE_TOTAL_ACTIVE_DEMAND = "4#1.0.2.6.0.255#2";
    public final static String REVERSE_ACTIVE_RATE1_MAXIMUM_DEMAND = "4#1.0.2.6.1.255#2";
    public final static String REVERSE_ACTIVE_RATE2_MAXIMUM_DEMAND = "4#1.0.2.6.2.255#2";
    public final static String REVERSE_ACTIVE_RATE3_MAXIMUM_DEMAND = "4#1.0.2.6.3.255#2";
    public final static String REVERSE_ACTIVE_RATE4_MAXIMUM_DEMAND = "4#1.0.2.6.4.255#2";
    public final static String REVERSE_ACTIVE_RATE5_MAXIMUM_DEMAND = "4#1.0.2.6.5.255#2";
    public final static String REVERSE_ACTIVE_RATE6_MAXIMUM_DEMAND = "4#1.0.2.6.6.255#2";
    public final static String REVERSE_ACTIVE_RATE7_MAXIMUM_DEMAND = "4#1.0.2.6.7.255#2";
    public final static String REVERSE_ACTIVE_RATE8_MAXIMUM_DEMAND = "4#1.0.2.6.8.255#2";

    /**
     * 需量 时间 参数
     */
    public final static String DATETIME_POSITIVE_TOTAL_ACTIVE_DEMAND = "4#1.0.1.6.0.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE1_MAXIMUM_DEMAND = "4#1.0.1.6.1.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE2_MAXIMUM_DEMAND = "4#1.0.1.6.2.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE3_MAXIMUM_DEMAND = "4#1.0.1.6.3.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE4_MAXIMUM_DEMAND = "4#1.0.1.6.4.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE5_MAXIMUM_DEMAND = "4#1.0.1.6.5.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE6_MAXIMUM_DEMAND = "4#1.0.1.6.6.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE7_MAXIMUM_DEMAND = "4#1.0.1.6.7.255#5";
    public final static String DATETIME_POSITIVE_ACTIVE_RATE8_MAXIMUM_DEMAND = "4#1.0.1.6.8.255#5";

    public final static String DATETIME_REVERSE_TOTAL_ACTIVE_DEMAND = "4#1.0.2.6.0.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE1_MAXIMUM_DEMAND = "4#1.0.2.6.1.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE2_MAXIMUM_DEMAND = "4#1.0.2.6.2.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE3_MAXIMUM_DEMAND = "4#1.0.2.6.3.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE4_MAXIMUM_DEMAND = "4#1.0.2.6.4.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE5_MAXIMUM_DEMAND = "4#1.0.2.6.5.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE6_MAXIMUM_DEMAND = "4#1.0.2.6.6.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE7_MAXIMUM_DEMAND = "4#1.0.2.6.7.255#5";
    public final static String DATETIME_REVERSE_ACTIVE_RATE8_MAXIMUM_DEMAND = "4#1.0.2.6.8.255#5";

    /**
     * 电能量OBIS
     */
    public final static String POSITIVE_ACTIVE_ENERGY = "3#1.0.1.8.0.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE1 = "3#1.0.1.8.1.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE2 = "3#1.0.1.8.2.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE3 = "3#1.0.1.8.3.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE4 = "3#1.0.1.8.4.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE5 = "3#1.0.1.8.5.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE6 = "3#1.0.1.8.6.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE7 = "3#1.0.1.8.7.255#2";
    public final static String POSITIVE_ACTIVE_ENERGY_RATE8 = "3#1.0.1.8.8.255#2";
    public final static String REVERSE_ACTIVE_ENERGY = "3#1.0.2.8.0.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE1 = "3#1.0.2.8.1.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE2 = "3#1.0.2.8.2.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE3 = "3#1.0.2.8.3.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE4 = "3#1.0.2.8.4.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE5 = "3#1.0.2.8.5.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE6 = "3#1.0.2.8.6.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE7 = "3#1.0.2.8.7.255#2";
    public final static String REVERSE_ACTIVE_ENERGY_RATE8 = "3#1.0.2.8.8.255#2";
    public final static String CURRENT_MONTHLY_POSITIVE_ACTIVE_ENERGY = "3#1.0.1.9.0.255#2";

    /**
     * 预付费信息包 obis
     */
    public final static String PREPAYMENT = "1#1.0.134.129.5.255#2";


    //region description 吉尔吉北电项目obis

    public final static String HXE110_METER_DAY_FREEZE_OBIS = "7#1-0:99.2.0.255#3";//日冻结
    public final static String HXE110_METER_DAY_FREEZE = "7#1-0:99.2.0.255#2";
    public final static String HXE110_METER_MONTH_FREEZE_OBIS = "7#0-0:98.1.0.255#3";//月冻结
    public final static String HXE110_METER_MONTH_FREEZE = "7#0-0:98.1.0.255#2";
    //endregion description

    /**
     * AB相角
     */
    public final static String A_B_PHASE_ANGLE = "3#1.0.81.7.1.255#2";
    /**
     * AC相角
     */
    public final static String A_C_PHASE_ANGLE = "3#1.0.81.7.2.255#2";

    /**
     * B相无功功率
     */
    public final static String B_REACTIVE_POWER_PHASE = "3#1.0.43.7.0.255#2";
    /**
     * C相无功功率
     */
    public final static String C_REACTIVE_POWER_PHASE = "3#1.0.63.7.0.255#2";


    /**
     * B相功率因数
     */
    public final static String B_POWER_FACTOR_PHASE = "3#1.0.53.7.0.255#2";
    /**
     * C相功率因数
     */
    public final static String C_POWER_FACTOR_PHASE = "3#1.0.73.7.0.255#2";


    /**
     * 三相需量
     * 正向视在最大需量 8费率
     * 反向视在最大需量 8费率
     */

    public final static String POSITIVE_TOTAL_APPAREMT_DEMAND = "4#1.0.9.6.0.255#2";
    public final static String POSITIVE_APPAREMT_RATE1_MAXIMUM_DEMAND = "4#1.0.9.6.1.255#2";
    public final static String POSITIVE_APPAREMT_RATE2_MAXIMUM_DEMAND = "4#1.0.9.6.2.255#2";
    public final static String POSITIVE_APPAREMT_RATE3_MAXIMUM_DEMAND = "4#1.0.9.6.3.255#2";
    public final static String POSITIVE_APPAREMT_RATE4_MAXIMUM_DEMAND = "4#1.0.9.6.4.255#2";
    public final static String POSITIVE_APPAREMT_RATE5_MAXIMUM_DEMAND = "4#1.0.9.6.5.255#2";
    public final static String POSITIVE_APPAREMT_RATE6_MAXIMUM_DEMAND = "4#1.0.9.6.6.255#2";
    public final static String POSITIVE_APPAREMT_RATE7_MAXIMUM_DEMAND = "4#1.0.9.6.7.255#2";
    public final static String POSITIVE_APPAREMT_RATE8_MAXIMUM_DEMAND = "4#1.0.9.6.8.255#2";

    public final static String REVERSE_TOTAL_APPAREMT_DEMAND = "4#1.0.10.6.0.255#2";
    public final static String REVERSE_APPAREMT_RATE1_MAXIMUM_DEMAND = "4#1.0.10.6.1.255#2";
    public final static String REVERSE_APPAREMT_RATE2_MAXIMUM_DEMAND = "4#1.0.10.6.2.255#2";
    public final static String REVERSE_APPAREMT_RATE3_MAXIMUM_DEMAND = "4#1.0.10.6.3.255#2";
    public final static String REVERSE_APPAREMT_RATE4_MAXIMUM_DEMAND = "4#1.0.10.6.4.255#2";
    public final static String REVERSE_APPAREMT_RATE5_MAXIMUM_DEMAND = "4#1.0.10.6.5.255#2";
    public final static String REVERSE_APPAREMT_RATE6_MAXIMUM_DEMAND = "4#1.0.10.6.6.255#2";
    public final static String REVERSE_APPAREMT_RATE7_MAXIMUM_DEMAND = "4#1.0.10.6.7.255#2";
    public final static String REVERSE_APPAREMT_RATE8_MAXIMUM_DEMAND = "4#1.0.10.6.8.255#2";

    /**
     * 单相 需量 时间 参数
     */
    public final static String DATETIME_POSITIVE_TOTAL_APPAREMT_DEMAND = "4#1.0.9.6.0.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE1_MAXIMUM_DEMAND = "4#1.0.9.6.1.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE2_MAXIMUM_DEMAND = "4#1.0.9.6.2.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE3_MAXIMUM_DEMAND = "4#1.0.9.6.3.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE4_MAXIMUM_DEMAND = "4#1.0.9.6.4.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE5_MAXIMUM_DEMAND = "4#1.0.9.6.5.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE6_MAXIMUM_DEMAND = "4#1.0.9.6.6.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE7_MAXIMUM_DEMAND = "4#1.0.9.6.7.255#5";
    public final static String DATETIME_POSITIVE_APPAREMT_RATE8_MAXIMUM_DEMAND = "4#1.0.9.6.8.255#5";

    public final static String DATETIME_REVERSE_TOTAL_APPAREMT_DEMAND = "4#1.0.10.6.0.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE1_MAXIMUM_DEMAND = "4#1.0.10.6.1.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE2_MAXIMUM_DEMAND = "4#1.0.10.6.2.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE3_MAXIMUM_DEMAND = "4#1.0.10.6.3.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE4_MAXIMUM_DEMAND = "4#1.0.10.6.4.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE5_MAXIMUM_DEMAND = "4#1.0.10.6.5.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE6_MAXIMUM_DEMAND = "4#1.0.10.6.6.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE7_MAXIMUM_DEMAND = "4#1.0.10.6.7.255#5";
    public final static String DATETIME_REVERSE_APPAREMT_RATE8_MAXIMUM_DEMAND = "4#1.0.10.6.8.255#5";


    /**
     *  电能量OBIS 三相表独有
     */
    public final static String POSITIVE_REACTIVE_ENERGY = "3#1.0.3.8.0.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE1 = "3#1.0.3.8.1.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE2 = "3#1.0.3.8.2.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE3 = "3#1.0.3.8.3.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE4 = "3#1.0.3.8.4.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE5 = "3#1.0.3.8.5.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE6 = "3#1.0.3.8.6.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE7 = "3#1.0.3.8.7.255#2";
    public final static String POSITIVE_REACTIVE_ENERGY_RATE8 = "3#1.0.3.8.8.255#2";
    public final static String REVERSE_REACTIVE_ENERGY = "3#1.0.4.8.0.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE1 = "3#1.0.4.8.1.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE2 = "3#1.0.4.8.2.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE3 = "3#1.0.4.8.3.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE4 = "3#1.0.4.8.4.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE5 = "3#1.0.4.8.5.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE6 = "3#1.0.4.8.6.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE7 = "3#1.0.4.8.7.255#2";
    public final static String REVERSE_REACTIVE_ENERGY_RATE8 = "3#1.0.4.8.8.255#2";

    /**
     * 日/月冻结预付费信息
     */
    public final static String HXE310_KP_METER_DAILY_FREEZE_PREPAYMENT_INFORMATION = "7#0.0.99.2.0.255#2";
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_PREPAYMENT_INFORMATION = "7#0.0.99.1.0.255#2";

    /**
     * 日冻结电能信息 HXE110-KP 也是用的同一个obis
     */
    public final static String HXE310_METER_DAILY_FREEZE_POWER_INFORMATION = "7#0.0.98.2.0.255#2";
    /**
     * 月冻结特殊信息包
     */
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_SPECIAL_INFORMATION = "7#0.0.98.9.0.255#2";

    /**
     * 月冻结正向有功电量及N费率
     * 月冻结反向有功电量及N费率
     * 月冻结正向无功电量及N费率
     * 月冻结反向无功电量及N费率
     * 日冻结正向有功电量及N费率
     * 日冻结反向有功电量及N费率
     * 日冻结正向无功电量及N费率
     * 日冻结反向无功电量及N费率
     */
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_POSITIVE_ACTIVE_ENERGY = "7#0.0.98.3.128.255#2";
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_REVERSE_ACTIVE_ENERGY = "7#0.0.98.16.128.255#2";
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_POSITIVE_REACTIVE_ENERGY = "7#0.0.98.4.128.255#2";
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_REVERSE_REACTIVE_ENERGY = "7#0.0.98.17.128.255#2";
    public final static String HXE310_KP_METER_DAILY_FREEZE_POSITIVE_ACTIVE_ENERGY = "7#0.0.98.1.128.255#2";
    public final static String HXE310_KP_METER_DAILY_FREEZE_REVERSE_ACTIVE_ENERGY = "7#0.0.98.25.128.255#2";
    public final static String HXE310_KP_METER_DAILY_FREEZE_POSITIVE_REACTIVE_ENERGY = "7#0.0.98.2.128.255#2";
    public final static String HXE310_KP_METER_DAILY_FREEZE_REVERSE_REACTIVE_ENERGY = "7#0.0.98.26.128.255#2";

    /**
     * 月冻结用钱信息
     */
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_CONSUMPTION_INFORMATION = "7#0.0.98.70.128.255#2";

    /**
     * 月冻结PFC扣费金额
     */
    public final static String HXE310_KP_METER_MONTHLY_FREEZE_PFC_DEDUCTION_AMOUNT = "7#0.0.98.73.128.255#2";

    /**
     * A/B/C 反向有功功率
     */
    public final static String A_REVERSE_ACTIVE_POWER_PHASE = "3#1.0.22.7.0.255#2";
    public final static String B_REVERSE_ACTIVE_POWER_PHASE = "3#1.0.42.7.0.255#2";
    public final static String C_REVERSE_ACTIVE_POWER_PHASE = "3#1.0.62.7.0.255#2";

    /**
     * A/B/C 正向视在功率 Instantaneous apparent export power (VA)
     */
    public final static String A_APPARENT_IMPORT_POWER = "3#1.0.29.7.0.255#2";
    public final static String B_APPARENT_IMPORT_POWER = "3#1.0.49.7.0.255#2";
    public final static String C_APPARENT_IMPORT_POWER = "3#1.0.69.7.0.255#2";

    /**
     * 三相总功率因数
     */
    public final static String POWER_FACTOR = "3#1.0.13.7.0.255#2";

    /**
     * 三项电流矢量和 Three-phase current vector sum
     */
    public final static String CURRENT_VECTOR_SUM = "3#1.0.128.7.0.255#2";

    /**
     * 正反向视在总电能量 positive apparent total energy
     */
    public final static String POSITIVE_APPARENT_TOTAL_ENERGY = "3#1.0.9.8.0.255#2";
    public final static String REVERSE_APPARENT_TOTAL_ENERGY = "3#1.0.10.8.0.255#2";


    /**
     * 肯尼亚T300冻结
     */
    public final static String HXT300_METER_DAILY_FREEZE_1 = "7#0.0.98.2.128.255#2";//负荷用第二通道数据读取
    public final static String HXT300_METER_DAILY_FREEZE_2 = "7#0.0.98.1.128.255#2";//负荷用第一通道数据读取
    public final static String HXT300_METER_DAILY_FREEZE_3 = "7#0.0.98.5.128.255#2";//负荷用第三通道数据读取
    public final static String HXT300_METER_MONTHLY_FREEZE = "7#0.0.98.6.128.255#2";//月冻结电能信息


}
