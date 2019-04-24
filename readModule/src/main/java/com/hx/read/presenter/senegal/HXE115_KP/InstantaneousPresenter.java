package com.hx.read.presenter.senegal.HXE115_KP;


import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.senegal.HXE115_KP.InstantaneousContact;
import com.hx.read.utils.ObisUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
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
    public void getShowList() {
        getView().showData(initData());
    }

    private TranXADRAssist initData() {
        TranXADRAssist item = new TranXADRAssist();
        //0003 1, 0, 12, 7, 15, 255 02 瞬时量数据包
        item.obis = "3#1-0:12.7.15.255#2";
        item.dataType = HexDataFormat.STRUCTURE;
        item.actionType = HexAction.ACTION_READ;
        item.structList = new ArrayList<>();

        TranXADRAssist.StructBean Voltage = new TranXADRAssist.StructBean();
        Voltage.name = ReadApp.getInstance().getString(R.string.read_instantaneous_voltage);
        Voltage.dataType = HexDataFormat.LONG_UNSIGNED;
        Voltage.scale = -1;
        Voltage.unit = "V";

        TranXADRAssist.StructBean Frequency = new TranXADRAssist.StructBean();
        Frequency.name = ReadApp.getInstance().getString(R.string.read_instantaneous_frequency);
        Frequency.dataType = HexDataFormat.LONG_UNSIGNED;
        Frequency.scale = -2;
        Frequency.unit = "Hz";

        TranXADRAssist.StructBean ActivePowerPositive = new TranXADRAssist.StructBean();
        ActivePowerPositive.name = ReadApp.getInstance().getString(R.string.read_instantaneous_active_plus);
        ActivePowerPositive.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        ActivePowerPositive.scale = -1;
        ActivePowerPositive.unit = "W";

        TranXADRAssist.StructBean ActivePowerNegative = new TranXADRAssist.StructBean();
        ActivePowerNegative.name = ReadApp.getInstance().getString(R.string.read_instantaneous_active_reverse);
        ActivePowerNegative.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        ActivePowerNegative.scale = -3;
        ActivePowerNegative.visible = false;
        ActivePowerNegative.unit = "kWh";

        TranXADRAssist.StructBean ReactivePowerPositive = new TranXADRAssist.StructBean();
        ReactivePowerPositive.name = ReadApp.getInstance().getString(R.string.read_instantaneous_reactive_plus);
        ReactivePowerPositive.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        ReactivePowerPositive.scale = -3;
        ReactivePowerPositive.visible = false;
        ReactivePowerPositive.unit = "kWh";

        TranXADRAssist.StructBean ReactivePowerNegative = new TranXADRAssist.StructBean();
        ReactivePowerNegative.name = ReadApp.getInstance().getString(R.string.read_instantaneous_reactive_reverse);
        ReactivePowerNegative.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        ReactivePowerNegative.scale = -3;
        ReactivePowerNegative.visible = false;
        ReactivePowerNegative.unit = "kWh";

        TranXADRAssist.StructBean ApparentPower = new TranXADRAssist.StructBean();
        ApparentPower.name = ReadApp.getInstance().getString(R.string.read_instantaneous_apparent_power);
        ApparentPower.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        ApparentPower.scale = 0;
        ApparentPower.visible = false;
        ApparentPower.unit = "VA";

        TranXADRAssist.StructBean Current = new TranXADRAssist.StructBean();
        Current.name = ReadApp.getInstance().getString(R.string.read_instantaneous_current);
        Current.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        Current.scale = -3;
        Current.unit = "A";

        TranXADRAssist.StructBean PowerFactor = new TranXADRAssist.StructBean();
        PowerFactor.name = ReadApp.getInstance().getString(R.string.read_instantaneous_power_factor);
        PowerFactor.scale = -3;
        PowerFactor.dataType = HexDataFormat.LONG_UNSIGNED;

        item.structList.add(Voltage);
        item.structList.add(Frequency);
        item.structList.add(ActivePowerPositive);
        item.structList.add(ActivePowerNegative);
        item.structList.add(ReactivePowerPositive);
        item.structList.add(ReactivePowerNegative);
        item.structList.add(ApparentPower);
        item.structList.add(Current);
        item.structList.add(PowerFactor);
        return item;
    }

    @Override
    public void read() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                initData();
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
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(initData());
            }
        });
    }

    @Override
    public boolean exportInstantaneousData(TranXADRAssist data) {
        if (meterNumber.length() > 0) {
            boolean isOk = saveInstantaneousData(data, meterNumber);
            if (isOk) {
                ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_success));
            } else {
                ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_failed));
            }
            return isOk;
        } else {
            ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_failed));
            return false;
        }
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

            @Override
            public void onFailure(String msg) {
                noticeResult(msg, false);
            }
        });
        clientAPI.action(assist);
        HexLog.d("readMeterNumber", "=========正常结束" + meterNumber + "=========");
    }

    /**
     * 保存瞬时量读取数据
     */
    public boolean saveInstantaneousData(TranXADRAssist data, String meterNo) {
        HexLog.d("saveInstantaneousData", "....");
        StringBuilder mStringBuilder = new StringBuilder();
        meterNo = meterNumber;
        boolean result = true;
        try {
            File insDtaFile = new File(ReadApp.FILEPATH_RECORD + File.separator + meterNo + ".csv");
            if (!insDtaFile.exists()) {
                result = insDtaFile.createNewFile();
            }
            mStringBuilder.append(TimeUtil.getNowTime());
            mStringBuilder.append(",");
            mStringBuilder.append("InstantaneousData");
            mStringBuilder.append("\n");
            for (TranXADRAssist.StructBean structBean : data.structList) {
                if (structBean.visible) {
                    mStringBuilder.append(structBean.name);
                    mStringBuilder.append(",");
                    mStringBuilder.append(structBean.value);
                    mStringBuilder.append(structBean.unit);
                    mStringBuilder.append("\n");
                }
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
                        ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.success));
                        getView().showData((TranXADRAssist) object);
                    } else {
                        ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                        getView().showToast(R.string.read_failed);
                    }
                }
            }
        });
    }
}
