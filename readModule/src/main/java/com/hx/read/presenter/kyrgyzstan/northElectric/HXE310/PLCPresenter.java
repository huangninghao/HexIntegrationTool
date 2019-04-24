package com.hx.read.presenter.kyrgyzstan.northElectric.HXE310;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE310.PLCContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class PLCPresenter extends RxBasePresenterImpl<PLCContact.View> implements PLCContact.Presenter {
    private HexClientAPI clientAPI;

    public PLCPresenter(PLCContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public void readPLC() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                final TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "1#1-2:0.2.0.255#02";
                actionAssist.dataType = HexDataFormat.VISIBLE_STRING;
                actionAssist.actionType = HexAction.ACTION_READ;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        if (data.aResult) {
                            HexThreadManager.runTaskOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    getView().hideLoading();
                                    getView().showData(data);
                                }
                            });
                        } else {
                            getView().hideLoading();
                            getView().showToast(R.string.failed);
                        }
                    }
                    @Override
                    public void onFailure(String msg){
                        getView().hideLoading();
                        getView().showToast(msg);
                    }
                });
                clientAPI.action(actionAssist);
            }
        });
    }

}
