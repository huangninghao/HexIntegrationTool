package com.hx.read.presenter.bangladesh.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.read.contact.bangladesh.HXE110.SetContact;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class SetPresenter extends RxBasePresenterImpl<SetContact.View> implements SetContact.Presenter {
    private HexClientAPI clientAPI;
    IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final TranXADRAssist data, int pos) {
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (data.aResult) {
                        getView().hideLoading();
                        getView().showData(data);
                    }
                }
            });
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
    };

    public SetPresenter(SetContact.View view) {
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

    /**
     * 设置透支金额
     */
    public void setOverdraft(final String data) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = "1#1.0.134.129.4.255#2";
                assist.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                assist.writeData = data;
                assist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(listener);
                clientAPI.action(assist);
            }
        });
    }

    /**
     * 设置表内报警余额
     */
    public void setAlarm(final String data) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = "1#1.0.134.129.1.255#2";
                assist.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                assist.writeData = data;
                assist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(listener);
                clientAPI.action(assist);
            }
        });
    }

    /**
     * 读表内报警余额
     */
    public void readAlarm() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = "1#1.0.134.129.1.255#2";
                assist.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                assist.actionType = HexAction.ACTION_READ;
                clientAPI.addListener(listener);
                clientAPI.action(assist);
            }
        });
    }

    /**
     * 读透支金额
     */
    public void readOverdraft() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = "1#1.0.134.129.4.255#2";
                assist.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
                assist.actionType = HexAction.ACTION_READ;
                clientAPI.addListener(listener);
                clientAPI.action(assist);
            }
        });
    }

}
