package com.hx.read.presenter.bangladesh.HXE310;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE310.EventRecordContact;
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
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class EventRecordPresenter extends RxBasePresenterImpl<EventRecordContact.View> implements EventRecordContact.Presenter {
    private HexClientAPI clientAPI;
    private String meterNumber = "";
    private int type;
    IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final List<TranXADRAssist> dataList) {
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    List<TranXADRAssist> assists = insertDescribe(dataList, type);
                    getView().hideLoading();
                    getView().showData(assists);
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

    public EventRecordPresenter(EventRecordContact.View view) {
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
    public void getEventRecord(final String startTime, final String endTime, final int select) {
        type = select;
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                String OBIS = "";
                switch (select) {
                    case Constant.STANDARD_EVENTS:
                        OBIS = ObisUtil.HXE110KP_STANDARD_EVENT_RECORD_INFORMATION;
                        break;
                    case Constant.FRAUDULENT_EVENTS:
                        OBIS = ObisUtil.HXE110KP_FRAUDULENT_EVENT_RECORD_INFORMATION;
                        break;
                    case Constant.RELAY_EVENT:
                        OBIS = ObisUtil.HXE110KP_RELAY_EVENT_RECORD_INFORMATION;
                        break;
                    case Constant.POWER_GRID_EVENT:
                        OBIS = ObisUtil.HXE110KP_POWER_GRID_EVENT_RECORD_INFORMATION;
                        break;
                }
                readMeterNumber();
                HexLog.d("getEventRecord", "=========开始=========");
                clientAPI.addListener(listener);
                clientAPI.action(getEventRecord(OBIS, startTime, endTime));
                HexLog.d("getEventRecord", "=========正常结束=========");
            }
        });
    }

    /**
     * 事件项
     *
     * @return List<TranXADRAssist>
     */
    private TranXADRAssist getEventRecord(String OBIS, String startTime, String endTime) {
        TranXADRAssist tranXADRAssist = new TranXADRAssist();
        tranXADRAssist.startTime = startTime;
        tranXADRAssist.endTime = endTime;
        tranXADRAssist.obis = OBIS;
        tranXADRAssist.actionType = HexAction.ACTION_READ_BLOCK;
        List<TranXADRAssist.StructBean> structBeans = new ArrayList<>();

        TranXADRAssist.StructBean item = new TranXADRAssist.StructBean();
        item.visible = true;
        item.name = "Date&&Time";
        item.dataType = HexDataFormat.DATE_TIME;
        structBeans.add(item);

        item = new TranXADRAssist.StructBean();
        item.visible = true;
        item.name = "Description";
        item.dataType = HexDataFormat.UNSIGNED;
        structBeans.add(item);
        tranXADRAssist.structList = structBeans;
        return tranXADRAssist;
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

    /**
     * 插入事件描述
     *
     * @param list 事件
     * @return List<List   <   TranXADRAssist>>
     */
    private List<TranXADRAssist> insertDescribe(List<TranXADRAssist> list, int type) {
        List<TranXADRAssist> listList = list;
        String[] item = null;
        switch (type) {
            case Constant.STANDARD_EVENTS:
                item = ReadApp.getInstance().getResources().getStringArray(R.array.standardEvents);
                break;
            case Constant.FRAUDULENT_EVENTS:
                item = ReadApp.getInstance().getResources().getStringArray(R.array.fraudulentEvents);
                break;
            case Constant.RELAY_EVENT:
                item = ReadApp.getInstance().getResources().getStringArray(R.array.relayEvent);
                break;
            case Constant.POWER_GRID_EVENT:
                item = ReadApp.getInstance().getResources().getStringArray(R.array.powerGridEvent);
                break;
        }
        for (int i = 0; i < listList.size(); i++) {
            String describe = "";
            TranXADRAssist.StructBean structBean = listList.get(i).structList.get(1);
            try {
                describe = item[Integer.parseInt(structBean.value, 16)];
            } catch (Exception e) {
                e.printStackTrace();
            }
            listList.get(i).structList.get(1).value = describe;
        }
        return listList;
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
}
