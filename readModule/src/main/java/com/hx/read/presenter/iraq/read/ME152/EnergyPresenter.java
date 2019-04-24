package com.hx.read.presenter.iraq.read.ME152;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.iraq.read.HXE12_DL.EnergyContact;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.hexing.HexAction;
import cn.hexing.iec21.HexClient21API;
import cn.hexing.model.TranXADRAssist;

public class EnergyPresenter extends RxBasePresenterImpl<EnergyContact.View> implements EnergyContact.Presenter {
    private HexClient21API clientAPI;
    private String meterNumber = "";

    public EnergyPresenter(EnergyContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> obisList = new ArrayList<>();
        TranXADRAssist obisPara;
        //region description 正向有功
        /**
         * 正向有功总电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Total Energy";
        obisPara.obis = "F002";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率1电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Total Energy of T1 tariff";
        obisPara.obis = "F003";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率2电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Total Energy of T2 tariff";
        obisPara.obis = "F004";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率3电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Total Energy of T3 tariff";
        obisPara.obis = "F005";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        /**
         * 正向有功费率4电能量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Total Energy of T4 tariff";
        obisPara.obis = "F006";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


//        /**
//         * 正向有功费率5电能量
//         */
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Total Energy of T5 tariff";
//        obisPara.obis = "F0FF";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        /**
//         * 正向有功费率6电能量
//         */
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Total Energy of T6 tariff";
//        obisPara.obis = "F0FE";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        /**
//         * 正向有功费率7电能量
//         */
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Total Energy of T7 tariff";
//        obisPara.obis = "F0FD";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        /**
//         * 正向有功费率8电能量
//         */
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Total Energy of T8 tariff";
//        obisPara.obis = "F0FC";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//        //endregion description
//        //region description 上一月正向有功
//        /**
//         * 上月正向有功总电能量
//         */
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Total Energy last month";
//        obisPara.obis = "F030";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T1 Tariff";
//        obisPara.obis = "F031";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T2 Tariff";
//        obisPara.obis = "F032";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T3 Tariff";
//        obisPara.obis = "F033";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T4 Tariff";
//        obisPara.obis = "F034";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T5 Tariff";
//        obisPara.obis = "F0FB";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T6 Tariff";
//        obisPara.obis = "F0FA";

//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T6 Tariff";
//        obisPara.obis = "F0F9";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T8 Tariff";
//        obisPara.obis = "F0F8";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//        //endregion description
//        //region description 上两月正向有功
//        obisPara = new TranXADRAssist();
//        obisPara.name = "Total Energy last two months";
//        obisPara.obis = "F037";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last two months of T1 Tariff";
//        obisPara.obis = "F038";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last two months of T2 Tariff";
//        obisPara.obis = "F039";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last two months of T3 Tariff";
//        obisPara.obis = "F03A";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last two months of T4 Tariff";
//        obisPara.obis = "F03B";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T5 Tariff";
//        obisPara.obis = "F0F7";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T6 Tariff";
//        obisPara.obis = "F0F6";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T7 Tariff";
//        obisPara.obis = "F0F5";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
//
//        obisPara = new TranXADRAssist();
//        obisPara.name = " Total Energy last month of T8 Tariff";
//        obisPara.obis = "F0F4";
//        obisPara.visible = true;
//        obisPara.unit = "kWh";
//        obisPara.actionType = HexAction.ACTION_READ;
//        obisList.add(obisPara);
        //endregion description
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
                clientAPI.addListener(new cn.hexing.IHexListener() {
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
