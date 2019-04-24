package com.hx.read.presenter.kenya.HXT300;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.MonthFreezeContact;
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

public class MonthFreezePresenter extends RxBasePresenterImpl<MonthFreezeContact.View> implements MonthFreezeContact.Presenter  {

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

    public MonthFreezePresenter(MonthFreezeContact.View view) {
        super(view);
        meterNumber = StringCache.get(Constant.METER_NUMBER);
        clientAPI = PortConfig.getInstance().getClientAPI();
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
    /**
     * 获取 月冻结数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public void getMonthFreeze(final String startTime, final String endTime, final int type) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                String OBIS = ObisUtil.HXT300_METER_MONTHLY_FREEZE;
                HexLog.d("getMonthFreeze", "=========开始=========");
                clientAPI.addListener(listener);
                clientAPI.action(getMonthlyFreezeXAD(type, OBIS, startTime, endTime));
                HexLog.d("getMonthFreeze", "=========正常结束=========");
            }
        });
    }


    /**
     * 获取冻结项
     *
     * @return List<TranXADRAssist>
     */
    private TranXADRAssist getMonthlyFreezeXAD(int type,String OBIS,String startTime,String endTime) {
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
                item.scale = -1;
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
                item.dataType = HexDataFormat.BCD;
                List<TranXADRAssist.StructBean.BeanItem> structBeanList ;
                structBeanList = new ArrayList<>();
                TranXADRAssist.StructBean.BeanItem structBean ;
                structBean = new TranXADRAssist.StructBean.BeanItem();
                structBean.name = "Active MD (+)";
                structBean.unit = "kW";
                structBean.size = 3;
                structBean.dataType = HexDataFormat.INTEGER;
                structBean.scale = -4;
                structBeanList.add(structBean);

                structBean = new TranXADRAssist.StructBean.BeanItem();
                structBean.name = "occurring time";
                structBean.size = 5;
                structBeanList.add(structBean);
                item.beanItems = structBeanList;
                item.visible = true;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.BCD;
                structBeanList = new ArrayList<>();
                structBean = new TranXADRAssist.StructBean.BeanItem();
                structBean.name = "Active MD (-)";
                structBean.unit = "kW";
                structBean.size = 3;
                structBean.dataType = HexDataFormat.INTEGER;
                structBean.scale = -4;
                structBeanList.add(structBean);

                structBean = new TranXADRAssist.StructBean.BeanItem();
                structBean.name = "occurring time";
                structBean.size = 5;
                structBeanList.add(structBean);
                item.beanItems = structBeanList;
                item.visible = true;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.name = "Average power factor";
                item.dataType = HexDataFormat.LONG_UNSIGNED;
                item.unit = "";
                item.scale = -3;
                item.visible = true;
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
                item.scale = -1;
                item.name = "Positive active energy";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 1 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 2 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 3 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 4 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 5 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 6 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 7 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -1;
                item.name = "Positive active rate 8 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);
                //endregion description
                break;
            case 4:
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
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 1 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 2 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 3 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 4 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 5 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 6 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 7 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "kWh";
                item.scale = -2;
                item.name = "Reverse active rate 8 electricity";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);
                //endregion description
                break;
            case 5:
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
                item.name = "Charge amount of current month";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "TK";
                item.scale = -2;
                item.name = "Consumption of current month";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "TK";
                item.scale = -2;
                item.name = "Tariff 1 consumption of current month";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "TK";
                item.scale = -2;
                item.name = "Tariff 2 consumption of current month";
                item.dataType = HexDataFormat.DOUBLE_LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "";
                item.scale = 0;
                item.name = "Number of long power failures in any phase";
                item.dataType = HexDataFormat.LONG;
                result.add(item);

                item = new TranXADRAssist.StructBean();
                item.visible = true;
                item.unit = "";
                item.scale = 0;
                item.name = "Number of overload";
                item.dataType = HexDataFormat.LONG;
                result.add(item);
                //endregion description
                break;
        }
        tranXADRAssist.structList = result;
        return tranXADRAssist;
    }
}