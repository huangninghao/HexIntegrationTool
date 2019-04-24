package com.hx.read.presenter.bangladesh.HXE310;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE310.InstantaneousContact;
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
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class InstantaneousPresenter extends RxBasePresenterImpl<InstantaneousContact.View> implements InstantaneousContact.Presenter {
    private HexClientAPI clientAPI;
    private String meterNumber = "";

    public InstantaneousPresenter(InstantaneousContact.View view) {
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
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> obisList = new ArrayList<>();
        TranXADRAssist obisPara;

        /**
         * A 相 电压
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Voltage in phase L1 ";
        obisPara.obis = ObisUtil.A_PHASE_VOLTAGE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "V";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 电压
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Voltage in phase L2";
        obisPara.obis = ObisUtil.B_PHASE_VOLTAGE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "V";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 电压
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Voltage in phase L3";
        obisPara.obis = ObisUtil.C_PHASE_VOLTAGE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "V";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 电流
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Current in phase L1";
        obisPara.obis = ObisUtil.A_PHASE_CURRENT;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "A";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 电流
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Current in phase L2";
        obisPara.obis = ObisUtil.B_PHASE_CURRENT;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "A";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 电流
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Current in phase L3";
        obisPara.obis = ObisUtil.C_PHASE_CURRENT;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "A";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(+) in phase L1";
        obisPara.obis = ObisUtil.A_PHASE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * B 相 有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(+) in phase L2";
        obisPara.obis = ObisUtil.B_PHASE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * C 相 有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(+) in phase L3";
        obisPara.obis = ObisUtil.C_PHASE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 无功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive power(+) in phase L1";
        obisPara.obis = ObisUtil.A_REACTIVE_POWER_PHASE;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "var";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 无功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive power(+) in phase L2";
        obisPara.obis = ObisUtil.B_REACTIVE_POWER_PHASE;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "var";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * C 相 无功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive power(+) in phase L3";
        obisPara.obis = ObisUtil.C_REACTIVE_POWER_PHASE;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "var";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 功率因数
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Power factor in phase L1";
        obisPara.obis = ObisUtil.A_POWER_FACTOR_PHASE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * B 相 功率因数
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Power factor in phase L2";
        obisPara.obis = ObisUtil.B_POWER_FACTOR_PHASE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * C 相 功率因数
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Power factor in phase L3";
        obisPara.obis = ObisUtil.C_POWER_FACTOR_PHASE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 电网频率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Frequency";
        obisPara.obis = ObisUtil.FREQUENCY;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "Hz";
        obisPara.scale = -2;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * AB相角
         *
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "L1 L2 phase angle";
        obisPara.obis = ObisUtil.A_B_PHASE_ANGLE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "°";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * AC相角
         *
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "L1 L3 phase angle";
        obisPara.obis = ObisUtil.A_C_PHASE_ANGLE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "°";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

//        /**
//         * 电表固件版本号
//         */
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Meter Version";
//        obisPara.obis = ObisUtil.METER_VERSION_OBIS;
//        obisPara.dataType = HexDataFormat.VISIBLE_STRING;
//        obisPara.visible = true;
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
        return obisList;
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
                    public void onSuccess(TranXADRAssist data, int pos) {
                        super.onSuccess(data, pos);
                        if (data.aResult) {
                            noticeResult(data, true);
                        } else {
                            noticeResult(data, false);
                        }
                    }

                    @Override
                    public void onFailure(final String msg) {
                        super.onFailure(msg);
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                getView().showToast(msg);
                            }
                        });
                    }
                });
                clientAPI.action(getShowList());
            }
        });
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
     * 通知结果
     *
     * @param object object
     */

    private void noticeResult(final Object object, final boolean isSuccess) {
        HexThreadManager.runTaskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (getView() != null) {
                    getView().hideLoading();
                }
                if (getView() != null) {
                    if (isSuccess) {
                        getView().showData((TranXADRAssist) object);
                        HexLog.e("打印数据", JSON.toJSONString(((TranXADRAssist) object).structList));
                    } else {
                        getView().showToast(R.string.read_failed);
                    }
                }
            }
        });
    }
}
