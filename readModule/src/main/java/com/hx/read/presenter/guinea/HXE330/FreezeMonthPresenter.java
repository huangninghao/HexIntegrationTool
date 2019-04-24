package com.hx.read.presenter.guinea.HXE330;

import android.text.TextUtils;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.guinea.HXE330.FreezeMonthContact;
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

public class FreezeMonthPresenter extends RxBasePresenterImpl<FreezeMonthContact.View> implements FreezeMonthContact.Presenter  {

    private HexClientAPI clientAPI;
    private String meterNumber = "";

    public FreezeMonthPresenter(FreezeMonthContact.View view) {
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
    public void getShowList(final TranXADRAssist assiste) {
        if (getView() != null) {
            getView().showLoading();
        }
        if (TextUtils.isEmpty(assiste.startTime) || TextUtils.isEmpty(assiste.endTime)) {
            getView().hideLoading();
            getView().showToast(R.string.read_freeze_select_time);
            return;
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                HexLog.d("getDayFreeze", "=========开始=========");
                readMeterNumber();

                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.startTime = assiste.startTime;
                actionAssist.endTime = assiste.endTime;
                actionAssist.obis = assiste.obis;
                actionAssist.actionType = HexAction.ACTION_READ_BLOCK;

                actionAssist.structList = new ArrayList<>();

                if (actionAssist.obis.equals(ObisUtil.METER_MONTHLY_FREEZE_POWER_INFORMATION)) {
                    TranXADRAssist.StructBean FreezeTime = new TranXADRAssist.StructBean();
                    FreezeTime.dataType = HexDataFormat.DATE_TIME;
                    FreezeTime.name = ReadApp.getInstance().getString(R.string.read_freeze_time);
                    actionAssist.structList.add(FreezeTime);

                    TranXADRAssist.StructBean ActiveEnergyPositive = new TranXADRAssist.StructBean();
                    ActiveEnergyPositive.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                    ActiveEnergyPositive.name = ReadApp.getInstance().getString(R.string.read_freeze_active_pluse);
                    ActiveEnergyPositive.unit = "kWh";
                    ActiveEnergyPositive.scale = -2;
                    actionAssist.structList.add(ActiveEnergyPositive);

                    TranXADRAssist.StructBean ActiveEnergyNegative = new TranXADRAssist.StructBean();
                    ActiveEnergyNegative.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                    ActiveEnergyNegative.name = ReadApp.getInstance().getString(R.string.read_freeze_active_reserve);
                    ActiveEnergyNegative.unit = "kWh";
                    ActiveEnergyNegative.scale = -2;
                    actionAssist.structList.add(ActiveEnergyNegative);
                }else if (actionAssist.obis.equals(ObisUtil.METER_MONTH_FREEZE_PREPAID)){

                    TranXADRAssist.StructBean FreezeTime = new TranXADRAssist.StructBean();
                    FreezeTime.dataType = HexDataFormat.DATE_TIME;
                    FreezeTime.name = ReadApp.getInstance().getString(R.string.read_freeze_time);
                    actionAssist.structList.add(FreezeTime);
                    //Meter balance
                    TranXADRAssist.StructBean MeterBalance = new TranXADRAssist.StructBean();
                    MeterBalance.dataType = HexDataFormat.DOUBLE_LONG;
                    MeterBalance.name = ReadApp.getInstance().getString(R.string.read_freeze_available_credit);
                    MeterBalance.unit = "kWh";
                    MeterBalance.scale = -2;
                    actionAssist.structList.add(MeterBalance);
                }

                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(List<TranXADRAssist> data) {
                        noticeResult(data, true);
                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(actionAssist);

                HexLog.d("getDayFreeze", "=========正常结束=========");
            }
        });
    }

    @Override
    public boolean exportFreezeData(List<TranXADRAssist> obisListData) {
        if (meterNumber.length() > 0) {
            ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_success));
            return saveMeterFreezeData(obisListData, meterNumber);
        }else {
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
     * 保存冻结读取数据
     */
    public boolean saveMeterFreezeData(List<TranXADRAssist> obisListData, String meterNo) {
        HexLog.d("saveMeterFreezeData", "=========开始=========");
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
            mStringBuilder.append("MeterMonthFreezeData");
            mStringBuilder.append("\n");
            for (TranXADRAssist data : obisListData) {
                if (data.structList != null && data.structList.size() > 0) {
                    for (int j = 0; j < data.structList.size(); j++) {
                        mStringBuilder.append(data.structList.get(j).name);
                        mStringBuilder.append(",");
                        mStringBuilder.append(data.structList.get(j).value);
                        mStringBuilder.append(data.structList.get(j).unit);
                        mStringBuilder.append("\n");
                    }
                }
            }
            ByteArrayInputStream istr = new ByteArrayInputStream(mStringBuilder.toString().getBytes());
            FileUtil.inputStreamToFile(istr, insDtaFile);
        } catch (Exception e) {
            HexLog.e("saveMeterFreezeData", "====出错=" + e.getMessage() + "====");
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
                if (isSuccess) {
                    ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.success));
                    List<TranXADRAssist> list = (List<TranXADRAssist>) object;
                    getView().showData(list);
                } else {
                    ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                }
            }
        });
    }
}
