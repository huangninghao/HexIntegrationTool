package com.hx.read.presenter.southAfrica.HXE330;


import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.southAfrica.HXE330.InstantaneousContact;
import com.hx.read.utils.ObisUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public void getShowList() {
        getView().showData(initData());
    }

    private List<TranXADRAssist> initData() {
        List<TranXADRAssist> assistList = new ArrayList<>();
        TranXADRAssist item = new TranXADRAssist();
        TranXADRAssist itemB;
        TranXADRAssist itemC;
        //0003 1, 0, 32, 7, 15, 255 02 A相瞬时量数据
        //0003 1, 0, 52, 7, 15, 255 02 B相瞬时量数据
        //0003 1, 0, 72, 7, 15, 255 02 C相瞬时量数据
        item.obis = "3#1-0:32.7.15.255#2";
        item.dataType = HexDataFormat.STRUCTURE;
        item.actionType = HexAction.ACTION_READ;
        item.structList = new ArrayList<>();

        TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_voltage);
        structBean.dataType = HexDataFormat.LONG_UNSIGNED;
        structBean.scale = -1;
        structBean.unit = "V";
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_current);
        structBean.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        structBean.scale = -3;
        structBean.unit = "A";
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_active_plus);
        structBean.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        structBean.scale = -1;
        structBean.unit = "W";
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_active_reverse);
        structBean.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        structBean.scale = -3;
        structBean.unit = "kWh";
        structBean.visible = false;
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_reactive_plus);
        structBean.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        structBean.scale = -3;
        structBean.unit = "kWh";
        structBean.visible = false;
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_reactive_reverse);
        structBean.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        structBean.scale = -3;
        structBean.unit = "kWh";
        structBean.visible = false;
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_apparent_power);
        structBean.dataType = HexDataFormat.LONG_UNSIGNED;
        structBean.unit = "VA";
        structBean.visible = false;
        item.structList.add(structBean);

        structBean = new TranXADRAssist.StructBean();
        structBean.name = ReadApp.getInstance().getString(R.string.read_instantaneous_power_factor);
        structBean.dataType = HexDataFormat.LONG_UNSIGNED;
        structBean.scale = -3;
        item.structList.add(structBean);

        assistList.add(item);
        itemB = item.clone();
        itemB.obis = "3#1-0:52.7.15.255#2";
        assistList.add(itemB);
        itemC = item.clone();
        itemC.obis = "3#1-0:72.7.15.255#2";
        assistList.add(itemC);
        return assistList;
    }


    @Override
    public void read() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                readMeterNumber();
                readA();
            }
        });
    }

    private void readA() {
        clientAPI.addListener(new IHexListener() {
            @Override
            public void onSuccess(TranXADRAssist data, int pos) {
                TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
                if (pos == 0) {
                    structBean.name = "phase L1";
                    data.structList.add(0, structBean);
                } else if (pos == 1) {
                    structBean.name = "phase L2";
                    data.structList.add(0, structBean);
                } else if (pos == 2) {
                    structBean.name = "phase L3";
                    data.structList.add(0, structBean);
                }
                noticeResult(data, true);
            }

            @Override
            public void onFailure(String msg) {
                noticeResult(msg, false);
            }
        });
        clientAPI.action(initData());
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
                        HexLog.e("打印数据", JSON.toJSONString(((TranXADRAssist) object).structList));
                    } else {
                        ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                        getView().showToast(R.string.read_failed);
                    }
                }
            }
        });
    }
}
