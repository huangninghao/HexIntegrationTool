package com.hx.read.presenter.kenya.HXT300;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.DayFreezeContact;
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

public class DayFreezePresenter extends RxBasePresenterImpl<DayFreezeContact.View> implements DayFreezeContact.Presenter {

    private HexClientAPI clientAPI;
    private String meterNumber = "";
    IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final List<TranXADRAssist> dataList) {
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (getView() != null) {
                        getView().hideLoading();
                        getView().showData(dataList);
                    }
                }
            });
        }

        @Override
        public void onFailure(String msg) {
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (getView() != null) {
                        getView().hideLoading();
                        getView().showToast(R.string.failed);
                    }
                }
            });
        }
    };

    public DayFreezePresenter(DayFreezeContact.View view) {
        super(view);
        meterNumber = StringCache.get(Constant.METER_NUMBER);
        clientAPI = PortConfig.getInstance().getClientAPI();
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
                        OBIS = ObisUtil.HXT300_METER_DAILY_FREEZE_3; //正反、有无功电量
                        break;
                    case 1:
                        OBIS = ObisUtil.HXT300_METER_DAILY_FREEZE_2; //正向无功电量及分费率
                        break;
                    case 2:
                        OBIS = ObisUtil.HXT300_METER_DAILY_FREEZE_1; //正向有功电量及分费率
                        break;
                }
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
                item.scale = -1;
                item.name = "Active energy (+)";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.scale = -1;
                item.unit = "kWh";
                item.name = "Active energy (-)";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kvar";
                item.scale = -1;
                item.name = "Reactive energy (+)";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.scale = -1;
                item.unit = "kvar";
                item.name = "Reactive energy (-)";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
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
                item.scale = -1;
                item.name = "Positive reactive energy";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive reactive rate 1 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive reactive rate 2 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive reactive rate 3 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive reactive rate 4 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive reactive rate 5 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
//
//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive reactive rate 6 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
//
//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive reactive rate 7 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
//
//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive reactive rate 8 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
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
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active energy";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 1 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 2 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 3 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 4 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                result.add(item);

//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive active rate 5 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
//
//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive active rate 6 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
//
//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive active rate 7 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
//
//                item = new TranXADRAssist.StructBean();
//                item.visible = true;
//                item.unit = "kWh";
//                item.scale = -1;
//                item.name = "Positive active rate 8 electricity";
//                item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
//                result.add(item);
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
}
