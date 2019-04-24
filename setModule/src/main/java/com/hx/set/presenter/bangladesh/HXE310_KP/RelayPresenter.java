package com.hx.set.presenter.bangladesh.HXE310_KP;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hx.set.App;
import com.hx.set.R;
import com.hx.set.contact.bangladesh.HXE310_KP.RelayContact;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static cn.hexing.dlms.HexDataFormat.BOOL;

public class RelayPresenter extends RxBasePresenterImpl<RelayContact.View> implements RelayContact.Presenter {
    private HexClientAPI clientAPI;
    public RelayPresenter(RelayContact.View view) {
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

    @Override
    public void openRelay() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "70#0.0.96.3.10.255#01";
                actionAssist.writeData = "";
                actionAssist.dataType = BOOL;
                actionAssist.actionType = HexAction.ACTION_EXECUTE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist data, int pos) {
                        if (data.aResult) {
                            TranXADRAssist newData = new TranXADRAssist();
                            newData = data;
                            newData.value = "00";
                            noticeResult(newData, true);
                        }else {
                            noticeResult(data, false);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(actionAssist);
            }
        });
    }

    @Override
    public void closeRelay() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "70#0.0.96.3.10.255#02";
                actionAssist.writeData = "";
                actionAssist.dataType = BOOL;
                actionAssist.actionType = HexAction.ACTION_EXECUTE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist data, int pos) {
                        if (data.aResult) {
                            TranXADRAssist newData = new TranXADRAssist();
                            newData = data;
                            newData.value = "01";
                            noticeResult(newData, true);
                        }else {
                            noticeResult(data, false);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(actionAssist);
            }
        });
    }

    @Override
    public void reload() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "70#0.0.96.3.10.255#02";
                actionAssist.writeData = "";
                actionAssist.dataType = BOOL;
                actionAssist.actionType = HexAction.ACTION_READ;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist data, int pos) {
                        if (data.aResult) {
                            noticeResult(data, true);
                        }else {
                            noticeResult(data, false);
                        }

                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(actionAssist);
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
                }
                if (isSuccess) {
                    ToastUtils.showToast(App.getInstance(), App.getInstance().getString(R.string.set_success));
                    getView().showData((TranXADRAssist) object);
                } else {
                    ToastUtils.showToast(App.getInstance(), App.getInstance().getString(R.string.failed));
                }
            }
        });
    }
}
