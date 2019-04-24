package com.hx.read.presenter.kenya.HXT300;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.InstantaneousContact;
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

public class InstantaneousPresenter extends RxBasePresenterImpl<InstantaneousContact.View> implements InstantaneousContact.Presenter {
    private HexClientAPI clientAPI;
    private String meterNumber ;

    public InstantaneousPresenter(InstantaneousContact.View view) {
        super(view);
        meterNumber = StringCache.get(Constant.METER_NUMBER);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> obisList = new ArrayList<>();
        TranXADRAssist obisPara;

        /**
         * A 相 电压
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Voltage in phase L1 ";
        obisPara.obis = ObisUtil.A_PHASE_VOLTAGE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "V";
        obisPara.scale = -2;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 电压
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Voltage in phase L2 ";
        obisPara.obis = ObisUtil.B_PHASE_VOLTAGE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "V";
        obisPara.scale = -2;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 电压
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Voltage in phase L3 ";
        obisPara.obis = ObisUtil.C_PHASE_VOLTAGE;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "V";
        obisPara.scale = -2;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 电流
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Current in phase L1";
        obisPara.obis = ObisUtil.A_PHASE_CURRENT;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "A";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 电流
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Current in phase L2";
        obisPara.obis = ObisUtil.B_PHASE_CURRENT;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "A";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 电流
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Current in phase L3";
        obisPara.obis = ObisUtil.C_PHASE_CURRENT;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "A";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 正向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(+) in phase L1";
        obisPara.obis = ObisUtil.A_PHASE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         *  B相 正向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(+) in phase L2";
        obisPara.obis = ObisUtil.B_PHASE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 正向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(+) in phase L3";
        obisPara.obis = ObisUtil.C_PHASE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 反向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(-) in phase L1";
        obisPara.obis = ObisUtil.A_REVERSE_ACTIVE_POWER_PHASE;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 反向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(-) in phase L2";
        obisPara.obis = ObisUtil.B_REVERSE_ACTIVE_POWER_PHASE;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 反向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active power(-) in phase L3";
        obisPara.obis = ObisUtil.C_REVERSE_ACTIVE_POWER_PHASE;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * A 相 正向视在功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Apparent export power L1";
        obisPara.obis = ObisUtil.A_APPARENT_IMPORT_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "VA";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * B 相 正向视在功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Apparent export power L2";
        obisPara.obis = ObisUtil.B_APPARENT_IMPORT_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "VA";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * C 相 正向视在功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Apparent export power L3";
        obisPara.obis = ObisUtil.C_APPARENT_IMPORT_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "VA";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 总正向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Activity power(+)";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 总反向有功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Activity power(-)";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "W";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


        /**
         * 总正向无功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactivity power(+)";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "var";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


        /**
         * 总反向无功功率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactivity power(-)";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_POWER;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.unit = "var";
        obisPara.scale = -1;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 三相总功率因数
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Power factor";
        obisPara.obis = ObisUtil.POWER_FACTOR;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 三相电流矢量和
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Three-phase current vector sum";
        obisPara.obis = ObisUtil.CURRENT_VECTOR_SUM;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "";
        obisPara.scale = -3;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 电网频率
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Frequency";
        obisPara.obis = ObisUtil.FREQUENCY;
        obisPara.dataType = HexDataFormat.LONG_UNSIGNED;
        obisPara.unit = "Hz";
        obisPara.scale = -2;
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
                                if (getView() != null) {
                                    getView().hideLoading();
                                    getView().showToast(msg);
                                }
                            }
                        });
                    }
                });
                clientAPI.action(getShowList());
            }
        });
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
