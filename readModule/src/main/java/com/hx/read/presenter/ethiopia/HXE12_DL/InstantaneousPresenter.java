package com.hx.read.presenter.ethiopia.HXE12_DL;


import android.text.TextUtils;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.ethiopia.HXE12_DL.InstantaneousContact;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.IHexListener;
import cn.hexing.ParaConfig;
import cn.hexing.iec21.HexClient21API;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class InstantaneousPresenter extends RxBasePresenterImpl<InstantaneousContact.View> implements InstantaneousContact.Presenter {
    private HexClient21API client21API;
    private String meterNumber = "";

    public InstantaneousPresenter(InstantaneousContact.View view) {
        super(view);
        ParaConfig.Builder builder = new ParaConfig.Builder();
        builder.setCommMethod(HexDevice.METHOD_OPTICAL)
                .setComName(HexDevice.COMM_NAME_USB)
                .setDevice(HexDevice.KT50)
                .setIsHands(false)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setDataFrameWaitTime(1500)
                .setDebugMode(true)
                .setRecDataConversion(true)
                .setIsBitConversion(true)
                .setStrMeterPwd("00000000")
                .setDataBit(8)
                .setStopBit(1)
                .setVerify("N").build();
        client21API = builder.build21();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist item = new TranXADRAssist();
        item.obis = "C400";
        item.format = "XXXX.XX";
        item.unit = "V";
        item.name = "Voltage";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "C410";
        item.format = "XXXX.XX";
        item.unit = "A";
        item.name = "Current";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "C420";
        item.format = "XXX.XXX";
        item.unit = "kW";
        item.name = "Power+";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "901F";
        item.format = "";
        item.unit = "kWh";
        item.name = "Active energy (+)";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        return list;
    }

    private void readMeterNumber() {
        HexLog.d("readMeterNumber", "=========开始=========");
        List<TranXADRAssist> assists = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();
        assist.obis = "C004";
        assist.actionType = HexAction.ACTION_READ;
        client21API.addListener(new cn.hexing.IHexListener() {
            @Override
            public void onSuccess(TranXADRAssist data, int pos) {
                meterNumber = data.value;
            }

        });
        assists.add(assist);
        client21API.action(assists);
        HexLog.d("readMeterNumber", "=========正常结束" + meterNumber + "=========");
    }


    @Override
    public void read() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                readMeterNumber();
                client21API.addListener(listener);
                client21API.action(getShowList());
            }
        });
    }

    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(TranXADRAssist data, int pos) {
            if (data.obis.equals("C004")){
                meterNumber = data.value;
            }
            else {
                noticeResult(data, data.aResult);
            }
        }

        @Override
        public void onFailure(String msg) {
            noticeResult(msg, false);
        }
    };

    /**
     * 通知结果
     *
     * @param object object
     */
    private void noticeResult(final Object object, final boolean isSuccess) {
        HexThreadManager.runTaskOnMainThread(new Runnable() {
            @Override
            public void run() {
                getView().hideLoading();
                if (getView() != null) {
                    if (isSuccess) {
                        getView().showData((TranXADRAssist) object);
                    } else {
                        getView().showToast(R.string.read_failed);
                    }
                }
            }
        });
    }

    /**
     * 保存读取数据
     * <p>
     * 所有的数据保存都走这里
     */
    @Override
    public boolean saveData(List<TranXADRAssist> obisListData, String type) {
        if (TextUtils.isEmpty(meterNumber)){
            return false;
        }
        HexLog.d("saveData", "....");
        StringBuilder mStringBuilder = new StringBuilder();
        boolean result = true;
        try {
            File insDtaFile = new File(ReadApp.FILEPATH_CACHE + File.separator + meterNumber + ".csv");
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
}
