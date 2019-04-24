package com.hx.read.presenter.kyrgyzstan.northElectric.HXF300;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXF300.MeterParameterContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;
import com.hx.read.utils.ObisUtil;

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

public class MeterParameterPresenter extends RxBasePresenterImpl<MeterParameterContact.View> implements MeterParameterContact.Presenter{
    private HexClientAPI clientAPI;
    private String meterNumber = "";
    public MeterParameterPresenter(MeterParameterContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist item;
        item = new TranXADRAssist();
        item.obis = "1#0-0:96.1.0.255#2";
        item.name = "Device ID 1-Serial number";
        item.dataType = HexDataFormat.VISIBLE_STRING;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "1#0-0:96.1.1.255#2";
        item.name = "Device ID 2-Equipment ID ";
        item.dataType = HexDataFormat.VISIBLE_STRING;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "1#1-0:0.2.0.255#2";
        item.name = "Active firmware Identifier";
        item.dataType = HexDataFormat.VISIBLE_STRING;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "1#1-1:0.2.0.255#2";
        item.name = "Active firmware Identifier 1";
        item.dataType = HexDataFormat.VISIBLE_STRING;
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "1#1-2:0.2.0.255#2";
        item.name = "Active firmware Identifier 2";
        item.dataType = HexDataFormat.VISIBLE_STRING;
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
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        super.onSuccess(data, pos);
                        if (data.aResult) {
                            noticeResult(data, true);
                        } else {
                            noticeResult(data, false);
                        }
                    }
                    @Override
                    public void onFailure(String msg){
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(getShowList());
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
    public boolean export(List<TranXADRAssist> assists, String type) {
        return false;
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
