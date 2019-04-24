package com.hx.read.presenter.bangladesh.HXE310;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE310.TokenContact;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static cn.hexing.dlms.HexDataFormat.OCTET_CODING_TOKEN;
import static cn.hexing.dlms.HexDataFormat.OCTET_STRING;

public class TokenPresenter extends RxBasePresenterImpl<TokenContact.View> implements TokenContact.Presenter {
    private HexClientAPI clientAPI;

    public TokenPresenter(TokenContact.View view) {
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
    public void writeToken(final String token) {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "1#1.0.129.129.2.255#2";
                actionAssist.dataType = OCTET_STRING;
                actionAssist.coding = OCTET_CODING_TOKEN;
                actionAssist.writeData = token.replaceAll("-","");
                actionAssist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (data.aResult) {
                                    ToastUtils.showToast(ReadApp.getInstance(), getErrorString(data.value));
                                } else {
                                    ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                                }
                            }
                        });

                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                            }
                        });

                    }
                });
                clientAPI.action(actionAssist);
            }
        });
    }
    private String getErrorString(String strResult) {
        if ("00".equals(strResult)) {
            strResult = "Token Download Success";
        } else if ("01".equals(strResult)) {
            strResult = "TOKEN Parse Error";
        } else if ("02".equals(strResult)) {
            strResult = "TOKEN Used";
        } else if ("03".equals(strResult)) {
            strResult = "TOKEN Out of Date";
        } else if ("04".equals(strResult)) {
            strResult = "Key Out of Date";
        } else if ("05".equals(strResult)) {
            strResult = "Recharge value over accumulation limit";
        } else if ("06".equals(strResult)) {
            strResult = "Key TYPE is not allowed to recharge";
        } else if ("07".equals(strResult)) {
            strResult = "Test code produced by a non designated manufacturer";
        } else if ("08".equals(strResult)) {
            strResult = "Token Type Error";
        } else if ("09".equals(strResult)) {
            strResult = "Key Type Error";
        } else if ("10".equals(strResult)) {
            strResult = "Token Type Error";
        } else {
            strResult = "Token Download Failed";
        }
        return strResult;
    }

}
