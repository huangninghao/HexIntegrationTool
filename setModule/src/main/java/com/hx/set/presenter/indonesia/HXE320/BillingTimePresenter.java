package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.BillingTimeContact;
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

public class BillingTimePresenter extends RxBasePresenterImpl<RateTimeContact.View> implements BillingTimeContact.Presenter {
    private HexClientAPI clientAPI;

    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final TranXADRAssist  data, int pos) {
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

                        getView().ShowTime( String.valueOf(Integer.parseInt(data.recStrData.substring(data.recStrData.length()-4,data.recStrData.length()-2),16))+" "+ String.valueOf(Integer.parseInt(data.recStrData.substring(12,14),16)));

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

    public BillingTimePresenter(RateTimeContact.View view) {
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
    public void set(  final String time) {
        getView().showLoading();


        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                //initData();
                clientAPI.action(setTranXAD(time));
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


    public TranXADRAssist setTranXAD( String  time) {

        TranXADRAssist assist = new TranXADRAssist();
        assist.structList = new ArrayList<>();
        //assist.dataType = HexDataFormat.STRUCTURE;
        assist.obis = "22#0.0.15.0.0.255#04";
        assist.actionType = HexAction.ACTION_WRITE;
        assist.writeData = "010102020904"+  String.format("%02x", Integer.parseInt(time.split(" ")[1]))  +"0000000905FFFFFF"+String.format("%02x", Integer.parseInt(time.split(" ")[0])) +"FF" ;





        return assist;

    }


    public TranXADRAssist getTranXAD() {

        TranXADRAssist assist = new TranXADRAssist();
        assist.structList = new ArrayList<>();
        assist.dataType = HexDataFormat.STRUCTURE;
        assist.obis = "22#0.0.15.0.0.255#04";
        assist.actionType = HexAction.ACTION_READ;




        TranXADRAssist.StructBean bean = new TranXADRAssist.StructBean();
        bean.dataType = HexDataFormat.OCTET_STRING;
        assist.structList.add(bean);

        TranXADRAssist.StructBean bean1 = new TranXADRAssist.StructBean();
        bean1.dataType = HexDataFormat.OCTET_STRING;
        assist.structList.add(bean1);

        return assist;

    }


}
