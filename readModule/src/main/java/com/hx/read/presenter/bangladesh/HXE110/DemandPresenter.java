package com.hx.read.presenter.bangladesh.HXE110;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE110.DemandContact;
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

public class DemandPresenter extends RxBasePresenterImpl<DemandContact.View> implements DemandContact.Presenter {
    private HexClientAPI clientAPI;
    private String meterNumber = "";
    public DemandPresenter(DemandContact.View view) {
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
         * 正向有功总最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_TOTAL_ACTIVE_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active total demand";
        obisPara.obis = ObisUtil.POSITIVE_TOTAL_ACTIVE_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率1最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE1_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 1 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE1_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 正向有功费率2最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE2_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 2 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE2_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率3最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE3_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 3 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE3_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 正向有功费率4最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE4_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 4 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE4_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 正向有功费率5最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE5_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 5 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE5_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 正向有功费率6最大需量
         */

        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE6_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 6 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE6_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 正向有功费率7最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE7_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 7 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE7_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 正向有功费率8最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_POSITIVE_ACTIVE_RATE8_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Positive active rate 8 maximum demand";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_RATE8_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


        /**
         * 反向有功总最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_TOTAL_ACTIVE_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active total demand";
        obisPara.obis = ObisUtil.REVERSE_TOTAL_ACTIVE_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率1最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE1_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 1 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE1_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 反向有功费率2最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE2_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 2 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE2_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率3最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE3_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 3 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE3_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 反向有功费率4最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE4_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 4 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE4_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 反向有功费率5最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE5_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 5 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE5_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 反向有功费率6最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE6_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 6 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE6_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 反向有功费率7最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE7_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 7 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE7_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        /**
         * 反向有功费率8最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "occurring time";
        obisPara.obis = ObisUtil.DATETIME_REVERSE_ACTIVE_RATE8_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Reverse active rate 8 maximum demand";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_RATE8_MAXIMUM_DEMAND;
        obisPara.dataType = HexDataFormat.BCD;
        obisPara.format = "XX.XXXX";
        obisPara.unit = "kW";
        obisPara.scale = -4;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

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
