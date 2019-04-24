package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.RateTimeContact;

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

public class RateTimePresenter extends RxBasePresenterImpl<RateTimeContact.View> implements RateTimeContact.Presenter {
    private HexClientAPI clientAPI;

    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final TranXADRAssist data, int pos) {
            // noticeResult(data, data.aResult);

            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (data.actionType == HexAction.ACTION_WRITE) {

                        if (data.aResult) {
                            getView().showToast(R.string.suc);
                        } else {
                            getView().showToast(R.string.failed);

                        }
                    }
                    else
                    {

                        getView().ShowTime(data.value.split(" ")[0]);

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

    public RateTimePresenter(RateTimeContact.View view) {
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
        read();
    }

    @Override
    public void set(final List<TranXADRAssist> writedata, String time) {
        getView().showLoading();
        for (int i = 0; i < writedata.size(); i++
                ) {
            if (i == 0) {
                writedata.get(i).writeData = time + " 00:00:00";
            }
            writedata.get(i).actionType = HexAction.ACTION_WRITE;
            writedata.get(i).dataType = HexDataFormat.DAY_RATE;
            writedata.get(i).value = String.valueOf(i + 1);
        }

        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                //initData();
                clientAPI.action(writedata);
            }
        });
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


    public TranXADRAssist getTranXAD() {
        List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();
        assist.structList = new ArrayList<>();
        assist.dataType = HexDataFormat.DATE_TIME;
        assist.obis = "20#0.0.13.0.0.255#10";
        assist.actionType = HexAction.ACTION_READ;

        return assist;

    }


}
