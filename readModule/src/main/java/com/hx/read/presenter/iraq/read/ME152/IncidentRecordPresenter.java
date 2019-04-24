package com.hx.read.presenter.iraq.read.ME152;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.iraq.read.HXE12_DL.IncidentRecordContact;

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

public class IncidentRecordPresenter extends RxBasePresenterImpl<IncidentRecordContact.View> implements IncidentRecordContact.Presenter {
    private HexClient21API clientAPI;
    private String meterNumber = "";

    public IncidentRecordPresenter(IncidentRecordContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> obisList = new ArrayList<>();
        TranXADRAssist obisPara;
        //region description

        obisPara = new TranXADRAssist();
        obisPara.name = "Cover opened times";
        obisPara.obis = "F04A";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);


        obisPara = new TranXADRAssist();
        obisPara.name = "Lastest open the cover time";
        obisPara.obis = "F04B";
        obisPara.visible = true;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Lastest cover open energy";
        obisPara.obis = "F04C";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Terminal cover be opened times";
        obisPara.obis = "F048";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Lastest open the terminal cover time";
        obisPara.obis = "F049";
        obisPara.visible = true;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Terminal  cover open energy";
        obisPara.obis = "F04D";
        obisPara.visible = true;
        obisPara.unit = "kWh";
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Magnetic detect number";
        obisPara.obis = "F08C";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Last time magnetic detect";
        obisPara.obis = "F08D";
        obisPara.visible = true;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Magnetic detect energy";
        obisPara.obis = "F08E";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Current reverse number";
        obisPara.obis = "F051";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Current reverse time";
        obisPara.obis = "F052";
        obisPara.visible = true;
        obisPara.dataType = HexDataFormat.DATE_TIME;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);

        obisPara = new TranXADRAssist();
        obisPara.name = "Current reverse energy";
        obisPara.obis = "F053";
        obisPara.visible = true;
        obisPara.actionType = HexAction.ACTION_READ;
        obisList.add(obisPara);
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
