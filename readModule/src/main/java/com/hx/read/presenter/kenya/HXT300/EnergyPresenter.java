package com.hx.read.presenter.kenya.HXT300;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.EnergyContact;
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

public class EnergyPresenter extends RxBasePresenterImpl<EnergyContact.View> implements EnergyContact.Presenter {
    private HexClientAPI clientAPI;
    private String meterNumber = "";

    public EnergyPresenter(EnergyContact.View view) {
        super(view);
        meterNumber = StringCache.get(Constant.METER_NUMBER);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> obisList = new ArrayList<>();
        TranXADRAssist obisPara;
        //region description 单三相表共用
        /**
         * 正向有功总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+)";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率1电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 1";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE1;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率2电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 2";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE2;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率3电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 3";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE3;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率4电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 4";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE4;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率5电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 5";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE5;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率6电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 6";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE6;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率7电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 7";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE7;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率8电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (+) tariff 8";
        obisPara.obis = ObisUtil.POSITIVE_ACTIVE_ENERGY_RATE8;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-)";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率1电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 1";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE1;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率2电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 2";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE2;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率3电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 3";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE3;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率4电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 4";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE4;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率5电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 5";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE5;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率6电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 6";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE6;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率7电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 7";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE7;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率8电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Active energy (-) tariff 8";
        obisPara.obis = ObisUtil.REVERSE_ACTIVE_ENERGY_RATE8;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        //endregion description

        //region description 三相表独有
        /**
         * 正向无功总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+)";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率1电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 1";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE1;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率2电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 2";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE2;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率3电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 3";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE3;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率4电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 4";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE4;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率5电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 5";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE5;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率6电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 6";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE6;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率7电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 7";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE7;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率8电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (+) tariff 8";
        obisPara.obis = ObisUtil.POSITIVE_REACTIVE_ENERGY_RATE8;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-)";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率1电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 1";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE1;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


        /**
         * 反向有功费率2电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 2";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE2;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


        /**
         * 反向有功费率3电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 3";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE3;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率4电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 4";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE4;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率5电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 5";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE5;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率6电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 6";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE6;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率7电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 7";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE7;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向有功费率8电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Reactive energy (-) tariff 8";
        obisPara.obis = ObisUtil.REVERSE_REACTIVE_ENERGY_RATE8;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG;
        obisPara.visible = true;
        obisPara.unit = "kvarh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
        //endregion description

        /**
         * 正向视在总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Apparent total energy(+)";
        obisPara.obis = ObisUtil.POSITIVE_APPARENT_TOTAL_ENERGY;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 反向视在总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Apparent total energy(-)";
        obisPara.obis = ObisUtil.REVERSE_APPARENT_TOTAL_ENERGY;
        obisPara.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        obisPara.visible = true;
        obisPara.unit = "Wh";
        obisPara.scale = -1;
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
