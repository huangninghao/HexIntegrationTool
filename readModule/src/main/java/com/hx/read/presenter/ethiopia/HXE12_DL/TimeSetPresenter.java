package com.hx.read.presenter.ethiopia.HXE12_DL;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.read.R;
import com.hx.read.contact.ethiopia.HXE12_DL.TimeSetContact;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.IHexListener;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.iec21.HexClient21API;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class TimeSetPresenter extends RxBasePresenterImpl<TimeSetContact.View> implements TimeSetContact.Presenter {
    private HexClient21API client21API;

    public TimeSetPresenter(TimeSetContact.View view) {
        super(view);
        ParaConfig.Builder builder = new ParaConfig.Builder();
        builder.setCommMethod(HexDevice.METHOD_OPTICAL)
                .setComName(HexDevice.COMM_NAME_USB)
                .setDevice(HexDevice.KT50)
                .setIsHands(false)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setDataFrameWaitTime(1500)
                .setDebugMode(true)
                .setRecDataConversion(true)
                .setIsBitConversion(true)
                .setStrMeterPwd("00000000")
                .setDataBit(8)
                .setStopBit(1)
                .setVerify("N").build();
        client21API = builder.build21();
    }

    @Override
    public void readTime() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assists = new ArrayList<>();
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = "C181";
                assist.actionType = HexAction.ACTION_READ;
                assists.add(assist);
                client21API.addListener(listener);
                client21API.action(assists);
            }
        });
    }

    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(TranXADRAssist data, int pos) {
            noticeResult(data, data.aResult);
        }

        @Override
        public void onFailure(String msg) {
            noticeResult(msg, false);
        }
    };

    @Override
    public void writeTime(final String time) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assists = new ArrayList<>();
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = "C181";
                assist.writeData = time;
                assist.dataType = HexDataFormat.DATE_TIME;
                assist.actionType = HexAction.ACTION_WRITE;
                assists.add(assist);
                client21API.addListener(new IHexListener() {
                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                    }

                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        super.onSuccess(data, pos);
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (data.aResult) {
                                    getView().showToast(R.string.success);
                                } else
                                    getView().showToast(R.string.failed);
                            }
                        });
                    }
                });
                client21API.action(assists);
            }
        });
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
                        if (((TranXADRAssist) object).value.length() != 14) { //避免表回复错误数据，导致程序崩溃
                            getView().showToast(R.string.error_format);
                        }
                        getView().showData((TranXADRAssist) object);
                    } else {
                        if (object instanceof String) {
                            getView().showToast(object.toString());
                        } else {
                            getView().showToast(R.string.read_failed);
                        }
                    }
                }
            }
        });
    }
}
