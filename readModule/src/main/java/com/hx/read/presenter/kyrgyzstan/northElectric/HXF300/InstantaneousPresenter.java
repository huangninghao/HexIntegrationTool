package com.hx.read.presenter.kyrgyzstan.northElectric.HXF300;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kyrgyzstan.northElectric.HXF300.InstantaneousContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;
import com.hx.read.utils.ObisUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class InstantaneousPresenter extends RxBasePresenterImpl<InstantaneousContact.View> implements InstantaneousContact.Presenter {
    private List<TranXADRAssist> list = new ArrayList<>();
    private HexClientAPI clientAPI;
    private String meterNumber = "";

    public InstantaneousPresenter(InstantaneousContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist item;
        item = new TranXADRAssist();
        item.obis = "3#1-0:32.7.0.255#2";
        item.unit = "V";
        item.name = "Instantaneous voltage L1";
        item.scale = -1;
        item.dataType = HexDataFormat.LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:52.7.0.255#2";
        item.unit = "V";
        item.name = "Instantaneous voltage L2";
        item.scale = -1;
        item.dataType = HexDataFormat.LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:72.7.0.255#2";
        item.unit = "V";
        item.name = "Instantaneous voltage L3";
        item.scale = -1;
        item.dataType = HexDataFormat.LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "3#1-0:31.7.0.255#2";
        item.unit = "A";
        item.name = "Instantaneous current  L1";
        item.scale = -3;
        item.dataType = HexDataFormat.LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:51.7.0.255#2";
        item.unit = "A";
        item.name = "Instantaneous current  L2";
        item.scale = -3;
        item.dataType = HexDataFormat.LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:71.7.0.255#2";
        item.unit = "A";
        item.name = "Instantaneous current  L3";
        item.scale = -3;
        item.dataType = HexDataFormat.LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "3#1-0:21.7.0.255#2";
        item.unit = "W";
        item.name = "Instantaneous active import power (+A)  L1";
        item.scale = -1;
        item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:41.7.0.255#2";
        item.unit = "W";
        item.name = "Instantaneous active import power (+A)  L2";
        item.scale = -1;
        item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:61.7.0.255#2";
        item.unit = "W";
        item.name = "Instantaneous active import power (+A)  L3";
        item.scale = -1;
        item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "3#1-0:1.8.0.255#2";
        item.unit = "Wh";
        item.name = "Active energy import (+A)";
        item.scale = 0;
        item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        item = new TranXADRAssist();
        item.obis = "3#1-0:2.8.0.255#2";
        item.unit = "Wh";
        item.name = "Active energy export (-A)";
        item.scale = 0;
        item.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        return list;
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
                        if (data.aResult == true) {
                            noticeResult(data, true);
                        } else {
                            noticeResult(data, false);
                        }

                    }

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
                clientAPI.action(getShowList());
            }
        });
    }

    @Override
    public boolean exportInstantaneousData(TranXADRAssist data) {
        if (meterNumber.length() > 0) {
            ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_success));
            return saveInstantaneousData(data, meterNumber);
        } else {
            ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.export_failed));
            return false;
        }

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
     * 保存瞬时量读取数据
     */
    public boolean saveInstantaneousData(TranXADRAssist data, String meterNo) {
        HexLog.d("saveInstantaneousData", "....");
        StringBuilder mStringBuilder = new StringBuilder();
        meterNo = meterNumber;
        boolean result = true;
        try {
            File insDtaFile = new File(ReadApp.FILEPATH_RECORD + File.separator + meterNo + ".csv");
            if (!insDtaFile.exists()) {
                result = insDtaFile.createNewFile();
            }
            mStringBuilder.append(TimeUtil.getNowTime());
            mStringBuilder.append(",");
            mStringBuilder.append("InstantaneousData");
            mStringBuilder.append("\n");
            for (TranXADRAssist.StructBean structBean : data.structList) {
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
                if (isSuccess) {
                    getView().showData((TranXADRAssist) object);
                } else {
                    if (object instanceof String) {
                        getView().showToast(object.toString());
                    } else
                        getView().showToast(R.string.failed);
                }
            }
        });
    }
}
