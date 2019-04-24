package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.DisplayIntervalContact;
import com.hx.set.model.TimeItem;

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

public class DisplayIntervalPresenter extends RxBasePresenterImpl<DisplayIntervalContact.View> implements DisplayIntervalContact.Presenter {
    private HexClientAPI clientAPI;
    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final TranXADRAssist data, int pos) {
            // noticeResult(data, data.aResult);

            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (data.actionType == HexAction.ACTION_READ) {
                        getView().show(data.value);
                    } else {
                        getView().showToast("Succeed");
                    }


                    getView().hideLoading();
                }
            });

        }

        @Override
        public void onFailure(String msg) {
            //noticeResult(msg, false);
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {

                    getView().hideLoading();

                    getView().showToast(R.string.failed);

                }
            });


        }
    };

    public DisplayIntervalPresenter(DisplayIntervalContact.View view) {
        super(view);
        ParaConfig.Builder builder = new ParaConfig.Builder();
        builder.setCommMethod(HexDevice.METHOD_OPTICAL)
                .setComName(HexDevice.COMM_NAME_USB)
                .setDevice(HexDevice.KT50)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setDebugMode(true)
                .setIsHands(true)
                .setDataFrameWaitTime(1500)

                .setIsFirstFrame(true)
                .build();
        clientAPI = builder.build();
        clientAPI.addListener(listener);
    }

    public void read() {
        getView().showLoading();

        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                //initData();
                clientAPI.action(getTranXAD());
            }
        });
    }

    public void set(final String val) {

        getView().showLoading();

        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                //initData();
                clientAPI.action(getTranXAD_write(val));
            }
        });

    }

    public TranXADRAssist getTranXAD() {
        //  List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();

        assist.dataType = HexDataFormat.UNSIGNED;
        assist.obis = "1#1.0.130.128.0.255#2";
        assist.actionType = HexAction.ACTION_READ;


        return assist;

    }

    public TranXADRAssist getTranXAD_write(String val) {
        //  List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();

        assist.dataType = HexDataFormat.UNSIGNED;
        assist.obis = "1#1.0.130.128.0.255#2";
        assist.actionType = HexAction.ACTION_WRITE;
        assist.writeData = val;


        return assist;

    }
}
