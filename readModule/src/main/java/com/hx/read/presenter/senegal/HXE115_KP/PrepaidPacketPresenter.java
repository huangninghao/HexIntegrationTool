package com.hx.read.presenter.senegal.HXE115_KP;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.senegal.HXE115_KP.PrepaidPacketContact;
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

public class PrepaidPacketPresenter extends RxBasePresenterImpl<PrepaidPacketContact.View> implements PrepaidPacketContact.Presenter {

    private List<TranXADRAssist> list = new ArrayList<>();
    private HexClientAPI clientAPI;
    private String meterNumber = "";

    public PrepaidPacketPresenter(PrepaidPacketContact.View view) {
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
        initData();
        getView().showData(list);
    }

    private void initData() {
        list.clear();
        TranXADRAssist item = new TranXADRAssist();
        //0001 1, 0, 140, 129, 1, 255 02 预付费信息包
        item.obis = "1#1-0:134.129.5.255#2";
        item.dataType = HexDataFormat.STRUCTURE;
        item.actionType = HexAction.ACTION_READ;
        item.structList = new ArrayList<>();

        //Output state of relay
        //Available credit
        TranXADRAssist.StructBean MeterBalance = new TranXADRAssist.StructBean();
        MeterBalance.name = ReadApp.getInstance().getString(R.string.read_prepaid_available_credit);
        MeterBalance.dataType = HexDataFormat.DOUBLE_LONG;
        MeterBalance.scale = -2;
        MeterBalance.unit = "kWh";

        TranXADRAssist.StructBean RelayStatus = new TranXADRAssist.StructBean();
        RelayStatus.name = ReadApp.getInstance().getString(R.string.read_prepaid_out_state_of_relay);
        RelayStatus.dataType = HexDataFormat.BOOL;
        RelayStatus.scale = 0;

        TranXADRAssist.StructBean RelayReson = new TranXADRAssist.StructBean();
        RelayReson.name = ReadApp.getInstance().getString(R.string.read_prepaid_relay_operate_reson);
        RelayReson.dataType = HexDataFormat.UNSIGNED;
        RelayReson.scale = 0;

        TranXADRAssist.StructBean RelayModel = new TranXADRAssist.StructBean();
        RelayModel.name = ReadApp.getInstance().getString(R.string.read_prepaid_relay_model);
        RelayModel.dataType = HexDataFormat.ENUM;
        RelayModel.scale = 0;

        TranXADRAssist.StructBean MeterWorkingMode = new TranXADRAssist.StructBean();
        MeterWorkingMode.name = ReadApp.getInstance().getString(R.string.read_prepaid_meter_work_mode);
        MeterWorkingMode.dataType = HexDataFormat.LONG;
        MeterWorkingMode.scale = 0;

        TranXADRAssist.StructBean MeterStatus = new TranXADRAssist.StructBean();
        MeterStatus.name = ReadApp.getInstance().getString(R.string.read_prepaid_meter_status);
        MeterStatus.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        MeterStatus.scale = 0;

        TranXADRAssist.StructBean ActivePowerPositive = new TranXADRAssist.StructBean();
        ActivePowerPositive.name = ReadApp.getInstance().getString(R.string.read_prepaid_active_power_plus);
        ActivePowerPositive.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        ActivePowerPositive.scale = -2;
        ActivePowerPositive.unit = "kWh";

        item.structList.add(MeterBalance);
        item.structList.add(RelayStatus);
        item.structList.add(RelayReson);
        item.structList.add(RelayModel);
        item.structList.add(MeterWorkingMode);
        item.structList.add(MeterStatus);
        item.structList.add(ActivePowerPositive);
        list.add(item);
    }

    @Override
    public void read() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                initData();
                readMeterNumber();
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist data, int pos) {
                        if (data.aResult) {
                            TranXADRAssist newData = data;
                            List<TranXADRAssist.StructBean> meterStatusList = new ArrayList<>();
                            for (TranXADRAssist.StructBean assist : newData.structList) {
                                //继电器状态
                                if (assist.name.equals(ReadApp.getInstance().getString(R.string.read_prepaid_out_state_of_relay))) {
                                    if (assist.value.equals("00")) {
                                        assist.value = ReadApp.getInstance().getString(R.string.read_relay_open);
                                    } else {
                                        assist.value = ReadApp.getInstance().getString(R.string.read_relay_close);
                                    }
                                } else if (assist.name.equals(ReadApp.getInstance().getString(R.string.read_prepaid_relay_model))) {
                                    if (assist.value.equals("00")) {
                                        //assist.value = ReadApp.getInstance().getString(R.string.read_relay_model_00);
                                        assist.value = "Model 0";
                                    } else if (assist.value.equals("04")) {
                                        //assist.value = ReadApp.getInstance().getString(R.string.read_relay_model_04);
                                        assist.value = "Model 4";
                                    }

                                } else if (assist.name.equals(ReadApp.getInstance().getString(R.string.read_prepaid_meter_work_mode))) {
                                    if (assist.value.equals("00")) {
                                        assist.value = ReadApp.getInstance().getString(R.string.read_meter_work_model_00);
                                    } else if (assist.value.equals("01")) {
                                        assist.value = ReadApp.getInstance().getString(R.string.read_meter_work_model_01);
                                    }

                                } else if (assist.name.equals(ReadApp.getInstance().getString(R.string.read_prepaid_meter_status))) {
                                    String val = Integer.toBinaryString(Integer.parseInt(assist.value));
                                    // String val = Integer.toBinaryString(Integer.parseInt("3"));
                                    StringBuffer buffer = new StringBuffer(val);
                                    if (buffer.length() < 32) {
                                        int size = 32 - buffer.length();
                                        for (int i = 0; i < size; i++) {
                                            buffer = buffer.insert(0, '0');
                                        }
                                    }
                                    buffer = buffer.reverse();
                                    char[] chars = buffer.toString().substring(0, 16).toCharArray();
                                    String[] items = ReadApp.getInstance().getResources().getStringArray(R.array.meterStatusArray);
                                    for (int i = 0; i < chars.length; i++) {
                                        if (chars[i] == '1') {
                                            TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
                                            structBean.name = ReadApp.getInstance().getString(R.string.read_prepaid_meter_status);
                                            structBean.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                                            structBean.scale = 0;
                                            structBean.value = items[i];
                                            meterStatusList.add(structBean);
                                        }
                                    }
                                    if (meterStatusList.size() == 0) {
                                        assist.value = ReadApp.getInstance().getString(R.string.read_meter_status_0000);
                                    }
                                } else if (assist.name.equals(ReadApp.getInstance().getString(R.string.read_prepaid_relay_operate_reson))) {
                                    switch (assist.value.toUpperCase()) {
                                        case "0":
                                        case "00":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_00);
                                            break;
                                        case "01":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_01);
                                            break;
                                        case "02":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_02);
                                            break;
                                        case "03":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_03);
                                            break;
                                        case "04":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_04);
                                            break;
                                        case "05":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_05);
                                            break;
                                        case "06":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_06);
                                            break;
                                        case "07":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_07);
                                            break;
                                        case "09":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_09);
                                            break;
                                        case "10":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_10);
                                            break;
                                        case "0A":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_0A);
                                            break;
                                        case "0B":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_0B);
                                            break;
                                        case "0C":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_0C);
                                            break;
                                        case "0D":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_0D);
                                            break;
                                        case "0E":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_0E);
                                            break;
                                        case "0F":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_0F);
                                            break;
                                        case "20":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reson_20);
                                            break;
                                        case "11":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reason_11);
                                            break;
                                        case "9B":
                                            assist.value = ReadApp.getInstance().getString(R.string.read_relay_reason_9B);
                                            break;
                                    }
                                }
                            }

                            if (meterStatusList.size() > 0) {
                                newData.structList.remove(5);
                                for (int i = 0; i < meterStatusList.size(); i++) {
                                    TranXADRAssist.StructBean structBean = meterStatusList.get(i);
                                    newData.structList.add(5 + i, structBean);
                                }
                            }

                            noticeResult(newData, true);
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
                clientAPI.action(list);
            }
        });
    }

    @Override
    public boolean exportPrepaidPacketData(TranXADRAssist data) {
        if (meterNumber.length() > 0) {
            ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_success));
            return savePrepaidPacketData(data, meterNumber);
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
    public boolean savePrepaidPacketData(TranXADRAssist data, String meterNo) {
        HexLog.d("savePrepaidPacketData", "....");
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
            mStringBuilder.append("PrepaidPacketData");
            mStringBuilder.append("\n");
            for (TranXADRAssist.StructBean structBean : data.structList) {
                mStringBuilder.append(structBean.name);
                mStringBuilder.append(",");
                mStringBuilder.append(structBean.value);
                mStringBuilder.append(structBean.unit);
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
                        ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.success));
                        getView().showData((TranXADRAssist) object);
                    } else {
                        ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                        if (list.size() == 0) {
                            getView().hideLoading();
                        }
                        getView().showToast(R.string.read_failed);
                    }
                }
            }
        });
    }
}
