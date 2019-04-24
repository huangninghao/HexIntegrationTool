package com.hx.read.presenter.bangladesh.HXE310;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE310.PrepaymentContact;
import com.hx.read.utils.Constant;
import com.hx.read.utils.ObisUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.HexStringUtil;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class PrepaymentPresenter extends RxBasePresenterImpl<PrepaymentContact.View> implements PrepaymentContact.Presenter {
    private HexClientAPI clientAPI;
    private String meterNumber = "";

    public PrepaymentPresenter(PrepaymentContact.View view) {
        super(view);
        ParaConfig.Builder builder = new ParaConfig.Builder();
        builder.setCommMethod(HexDevice.METHOD_OPTICAL)
                .setComName(HexDevice.COMM_NAME_USB)
                .setDevice(HexDevice.KT50)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setDataFrameWaitTime(2000)
                .setDebugMode(true)
                .setSleepSendTime(100)
                .setDataBit(8)
                .setStopBit(1)
                .setVerify("N").build();
        clientAPI = builder.build();
    }

    @Override
    public TranXADRAssist getShowList() {
        TranXADRAssist result = new TranXADRAssist();
        result.obis = ObisUtil.PREPAYMENT;
        result.actionType = HexAction.ACTION_READ_BLOCK;
        List<TranXADRAssist.StructBean> structBeanList = new ArrayList<>();
        TranXADRAssist.StructBean item;

        /**
         * 电表余额
         */
        item = new TranXADRAssist.StructBean();
        item.name = "Available credit";
        item.dataType = HexDataFormat.DOUBLE_LONG;
        item.scale = -2;
        item.unit = "TK";
        item.visible = true;
        structBeanList.add(item);

        /**
         *  继电器状态 00 = 断开 01 = 闭合
         */
        item = new TranXADRAssist.StructBean();
        item.name = Constant.RELAY_STATUS;
        item.dataType = HexDataFormat.BOOL;
        item.visible = true;
        structBeanList.add(item);
        /**
         * 继电器操作原因U8 (11 xx)
         */
        item = new TranXADRAssist.StructBean();
        item.name = Constant.RELAY_OPERATION_REASON;
        item.dataType = HexDataFormat.UNSIGNED;
        item.visible = true;
        structBeanList.add(item);
        /**
         * 继电器模式 ENUM (16 xx),0～4，各模式定义见表计功能规范
         */
        item = new TranXADRAssist.StructBean();
        item.name = Constant.RELAY_MODE;
        item.dataType = HexDataFormat.ENUM;
        item.visible = true;
        structBeanList.add(item);
        /**
         * 电表工作模式 11 xx     (0--普通表模式，1--预付费充电模式，2--预付费充钱模式)
         */
        item = new TranXADRAssist.StructBean();
        item.name = Constant.WORKING_MODE;
        item.dataType = HexDataFormat.UNSIGNED;
        item.visible = true;
        structBeanList.add(item);
        /**
         *  电表状态U32    UTF8_STRING
         */
        item = new TranXADRAssist.StructBean();
        item.name = Constant.METER_STATUS;
        item.dataType = HexDataFormat.UTF8_STRING;
        item.visible = true;
        structBeanList.add(item);

        /**
         * 正向有功总电能量
         */
        item = new TranXADRAssist.StructBean();
        item.name = "Positive active total energy";
        item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        item.unit = "kWh";
        item.scale = -2;
        item.visible = true;
        structBeanList.add(item);

        result.structList = structBeanList;
        return result;
    }

    @Override
    public void read() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                readMeterNumber();
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final List<TranXADRAssist> dataList) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                List<TranXADRAssist> assists = dataChange(dataList);
                                getView().showData(assists);
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final TranXADRAssist data,int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                getView().showData(data);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String msg) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                getView().showToast(R.string.failed);
                            }
                        });
                    }
                });
                clientAPI.action(getShowList());
                clientAPI.action(getRecharge());
            }
        });
    }

    /**
     * 预付费中的独立项 总充值数额
     *
     * @return List<TranXADRAssist>
     */
    private List<TranXADRAssist> getRecharge() {
        List<TranXADRAssist> result = new ArrayList<>();
        TranXADRAssist item;
        /**
         * 总充值数额
         */
        item = new TranXADRAssist();
        item.name = "Total recharge amount";
        item.obis = ObisUtil.TOTAL_RECHARGE_AMOUNT;
        item.dataType = HexDataFormat.DOUBLE_LONG;
        item.scale = -2;
        item.unit = "TK";
        item.visible = true;
        item.actionType = HexAction.ACTION_READ;
        result.add(item);
        return result;
    }

    public void readMeterNumber() {
        HexLog.d("readMeterNumber", "=========开始=========");
        TranXADRAssist assist = new TranXADRAssist();
        assist.dataType = HexDataFormat.VISIBLE_STRING;
        assist.obis = ObisUtil.READ_METER_NUMBER;
        assist.actionType = HexAction.ACTION_READ;
        clientAPI.addListener(new IHexListener() {
            @Override
            public void onSuccess(TranXADRAssist data, int pos) {
                meterNumber = data.value;
            }
        });
        clientAPI.action(assist);
        HexLog.d("readMeterNumber", "=========正常结束" + meterNumber + "=========");
    }

    public boolean saveData(List<TranXADRAssist> obisListData, String type) {
        HexLog.d("saveData", "....");
        StringBuilder mStringBuilder = new StringBuilder();
        boolean result = true;
        try {
            File insDtaFile = new File(ReadApp.FILEPATH_RECORD + File.separator + meterNumber + ".csv");
            if (!insDtaFile.exists()) {
                result = insDtaFile.createNewFile();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            mStringBuilder.append(str);
            mStringBuilder.append(",");
            mStringBuilder.append(type);
            mStringBuilder.append("\n");
            for (TranXADRAssist data : obisListData) {
                mStringBuilder.append(data.name);
                mStringBuilder.append(",");
                mStringBuilder.append(data.value);
                mStringBuilder.append(data.unit);
                mStringBuilder.append("\n");
            }
            ByteArrayInputStream istr = new ByteArrayInputStream(mStringBuilder.toString().getBytes());
            FileUtil.inputStreamToFile(istr, insDtaFile);
        } catch (Exception e) {
            HexLog.e("保存瞬时量数据出错", e.getMessage());
            result = false;
        }
        return result;
    }

    /**
     * 预付费信息包 数据内容描述
     *
     * @param list 信息包
     * @return List<List       <       TranXADRAssist>>
     */
    private List<TranXADRAssist> dataChange(List<TranXADRAssist> list) {
        String[] item = null;
        for (TranXADRAssist tranXADRAssist : list) {
            for (TranXADRAssist.StructBean structBean : tranXADRAssist.structList) {
                switch (structBean.name) {
                    case Constant.RELAY_STATUS:
                        item = ReadApp.getInstance().getResources().getStringArray(R.array.relay_status);
                        structBean.value = item[Integer.parseInt(structBean.value, 16)];
                        break;
                    case Constant.RELAY_MODE:
                        item = ReadApp.getInstance().getResources().getStringArray(R.array.relay_mode);
                        structBean.value = item[Integer.parseInt(structBean.value, 16)];
                        break;
                    case Constant.RELAY_OPERATION_REASON:
                        item = ReadApp.getInstance().getResources().getStringArray(R.array.relay_operation_reason);
                        structBean.value = item[Integer.parseInt(structBean.value, 16)];
                        break;
                    case Constant.WORKING_MODE:
                        item = ReadApp.getInstance().getResources().getStringArray(R.array.work_mode);
                        structBean.value = item[Integer.parseInt(structBean.value, 16)];
                        break;
                    case Constant.METER_STATUS:
                        item = ReadApp.getInstance().getResources().getStringArray(R.array.meter_status);
                        String value = "";
                        //转化成二进制字符串后，根据置1的次数，确定存在多少种状态，根据各个1的位置确定为何种状态
                        String before = Integer.toBinaryString(Integer.parseInt(structBean.value));//api中自动有数据处理加了小数点
                        String after = before.replaceAll("1", "");//去掉所有1
                        int lenTimes = before.length() - after.length();//1出现的次数
                        if (lenTimes == 0) {
                            value = "normal";
                        } else {
                            for (int a = 0; a < lenTimes; a++) {
                                int n = HexStringUtil.padRight(before, 16, '0').indexOf("1");//第一次出现1的位置
                                before = before.replaceFirst("1", "0");//第一个1换成0
                                value = value + item[15 - n] + ";";
                            }
                        }
                        structBean.value = value;
                        break;
                }
            }
        }
        return list;
    }
}
