package com.hx.read.presenter.bangladesh.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE110.DayFreezeContact;
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

public class DayFreezePresenter extends RxBasePresenterImpl<DayFreezeContact.View> implements DayFreezeContact.Presenter {

    private HexClientAPI clientAPI;
    private String meterNumber = "";
    IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final List<TranXADRAssist> dataList) {
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    getView().hideLoading();
                    getView().showData(dataList);
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
    };

    public DayFreezePresenter(DayFreezeContact.View view) {
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

    /**
     * 获取 日冻结数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public void getDayFreeze(final String startTime, final String endTime, final int type) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                HexLog.d("getDayFreeze", "=========开始=========");
                String OBIS = "";
                switch (type) {
                    case 0:
                        OBIS = ObisUtil.HXE310_METER_DAILY_FREEZE_POWER_INFORMATION;
                        break;
                    case 1:
                        OBIS = ObisUtil.HXE310_KP_METER_DAILY_FREEZE_PREPAYMENT_INFORMATION;
                        break;
                    case 2:
                        OBIS = ObisUtil.HXE310_KP_METER_DAILY_FREEZE_POSITIVE_ACTIVE_ENERGY;
                        break;
                    case 3:
                        OBIS = ObisUtil.HXE310_KP_METER_DAILY_FREEZE_REVERSE_ACTIVE_ENERGY;
                        break;

                }
                readMeterNumber();
                clientAPI.addListener(listener);
                clientAPI.action(getDailyFreezeXAD(type, OBIS, startTime, endTime));
                HexLog.d("getDayFreeze", "=========正常结束=========");
            }
        });
    }

    /**
     * 获取冻结项
     *
     * @return List<TranXADRAssist>
     */
    private TranXADRAssist getDailyFreezeXAD(int type, String OBIS, String startTime, String endTime) {
        TranXADRAssist tranXADRAssist = new TranXADRAssist();
        tranXADRAssist.startTime = startTime;
        tranXADRAssist.obis = OBIS;
        tranXADRAssist.endTime = endTime;
        tranXADRAssist.actionType = HexAction.ACTION_READ_BLOCK;
        List<TranXADRAssist.StructBean> result = new ArrayList<>();
        TranXADRAssist.StructBean item;
        switch (type) {
            case 0:
                //region description
                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.name = "Date&&Time";
                item.dataType = HexDataFormat.OCTET_STRING;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Active energy (+)";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.scale = -2;
                item.unit = "kWh";
                item.name = "Active energy (-)";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);
                //endregion description
                break;
            case 1:
                //region description
                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.name = "Date&&Time";
                item.dataType = HexDataFormat.OCTET_STRING;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "TK";
                item.scale = -2;
                item.name = "Balance";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);
                //endregion description
                break;
            case 2:
                //region description
                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.name = "Date&&Time";
                item.dataType = HexDataFormat.OCTET_STRING;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active energy";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 1 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 2 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 3 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 4 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 5 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 6 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 7 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Positive active rate 8 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);
                //endregion description
                break;
            case 3:
                //region description
                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.name = "Date&&Time";
                item.dataType = HexDataFormat.OCTET_STRING;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active energy";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 1 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 2 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 3 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 4 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 5 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 6 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 7 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 8 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);
                //endregion description
                break;
        }
        tranXADRAssist.structList = result;
        return tranXADRAssist;
    }

    /**
     * 保存读取数据
     * <p>
     * 所有的数据保存都走这里
     */
    public boolean saveData(List<TranXADRAssist> obisListData, String type) {
        HexLog.d("saveData", "....");
        if (StringUtil.isEmpty(meterNumber)) {
            return false;
        }
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
}
