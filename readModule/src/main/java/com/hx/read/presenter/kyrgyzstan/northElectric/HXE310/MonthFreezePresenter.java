package com.hx.read.presenter.kyrgyzstan.northElectric.HXE310;

import android.text.TextUtils;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.base.mInterface.module.baseModule.BaseService;
import com.hx.base.model.MeterDataModel;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE310.MonthFreezeContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;
import com.hx.read.utils.ObisUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
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

public class MonthFreezePresenter extends RxBasePresenterImpl<MonthFreezeContact.View> implements MonthFreezeContact.Presenter {
    private HexClientAPI clientAPI;
    private TranXADRAssist assist;
    private String meterNumber = "";
    private MeterDataModel meterDataModel;

    public MonthFreezePresenter(MonthFreezeContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public void getShowList(final String startTime, final String endTime) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                meterDataModel = BaseService.getNorthElectricHXE110Json();
                readMeterNumber();
                assist = new TranXADRAssist();
                assist.obis = ObisUtil.HXE110_METER_MONTH_FREEZE_OBIS;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onFailure(final String msg) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                getView().showToast(msg);
                            }
                        });
                    }
                });
                assist = clientAPI.readCaptureObjectNew(assist);
                if (assist.structList.size() > 0) {
                    for (TranXADRAssist.StructBean structBean : assist.structList) { //冻结项
                        for (MeterDataModel.DataModelBean.OBISBean obisBean : meterDataModel.DataModel.OBIS) { //冻结项obis
                            if (structBean.obis.substring(4,16).equals(fnChangeOBIS(obisBean.obis).toUpperCase())) {
                                structBean.name = obisBean.name;
                                for (MeterDataModel.DataModelBean.OBISBean.AttributeBean attributeBean : obisBean.attribute) {//根据属性，找到量纲，单位
                                    if (attributeBean.id.equals(structBean.obis.substring(structBean.obis.length() - 1))) {
                                        if (!TextUtils.isEmpty(attributeBean.item.scaler)) {
                                            structBean.scale = Double.parseDouble(attributeBean.item.scaler);
                                        }
                                        structBean.unit = attributeBean.item.unit;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    assist.startTime = startTime;
                    assist.endTime = endTime;
                    assist.obis = ObisUtil.HXE110_METER_MONTH_FREEZE;
                    assist.actionType = HexAction.ACTION_READ_BLOCK;
                    clientAPI.addListener(new IHexListener() {
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
                    });
                    clientAPI.action(assist);
                } else {
                    HexThreadManager.runTaskOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().hideLoading();
                            getView().showToast(R.string.emptyData);
                        }
                    });
                }
            }
        });
    }


    private void readMeterNumber() {
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

    /**
     * 解析obis 格式
     *
     * @param strOBIS String
     * @return string 16进制 字符串
     */
    private static String fnChangeOBIS(String strOBIS) {
        String strData = "";
        try {
            strOBIS = strOBIS.replaceAll(":", ".").replaceAll("-", ".");
            StringBuilder strB = new StringBuilder();

            String[] strMain = strOBIS.split("\\.");
            for (int i = 0; i < strMain.length; i++) {
                strB.append(String.format("%02x", Integer.parseInt(strMain[i], 10)));
            }
            strData = strB.toString();
        } catch (Exception ex) {
            strData = strOBIS;
        }
        return strData;
    }

}
