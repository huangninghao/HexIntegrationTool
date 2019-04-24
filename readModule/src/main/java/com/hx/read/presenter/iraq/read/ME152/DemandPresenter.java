package com.hx.read.presenter.iraq.read.ME152;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.iraq.read.HXE12_DL.DemandContact;
import com.hx.read.utils.Constant;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.hexing.HexAction;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.iec21.HexClient21API;
import cn.hexing.model.TranXADRAssist;

public class DemandPresenter extends RxBasePresenterImpl<DemandContact.View> implements DemandContact.Presenter {
    private HexClient21API clientAPI;
    private String meterNumber = "";
    public DemandPresenter(DemandContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> obisList = new ArrayList<>();
        TranXADRAssist obisPara;

        /**
         * 正向有功总最大需量
         */
        obisPara = new TranXADRAssist();
        obisPara.name = "Cumulative demand";
        obisPara.obis = "F046";
        obisPara.visible = true;
        obisPara.format = "XXXX.XXXX";
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Power demand";
        obisPara.obis = "F03E";
        obisPara.visible = true;
        obisPara.format = "XXXX.XXXX";
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Power Demand time";
        obisPara.obis = "F03F";
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Last Month Power Demand";
        obisPara.obis = "F040";
        obisPara.format = "XXXX.XXXX";
        obisPara.unit = "kW";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Last Month Power Demand Time";
        obisPara.obis = "F041";
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Last two Month Power Demand";
        obisPara.obis = "F042";
        obisPara.format = "XXXX.XXXX";
        obisPara.unit = "kW";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Last two Month Power Demand Time";
        obisPara.obis = "F043";
        obisPara.dataType = HexDataFormat.DATE_TIME;
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
        if (TextUtils.isEmpty(StringCache.get(Constant.METER_NUMBER))) {
            return false;
        }
        meterNumber = StringCache.get(Constant.METER_NUMBER);
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
