package com.hx.read.presenter.bangladesh.HXE310;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.base.model.DisplayMeterBean;
import com.hx.read.R;
import com.hx.read.contact.bangladesh.HXE310.ParameterSetContact;
import com.hx.read.model.ParameterItem;

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

import static com.hx.base.mInterface.module.baseModule.BaseService.getBangladeshDisplayJson;

public class DisplayRotatePresenter extends RxBasePresenterImpl<ParameterSetContact.View> implements ParameterSetContact.Presenter {
    private List<DisplayMeterBean> displayMeterBeanList = new ArrayList<>();
    private HexClientAPI clientAPI;
    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final TranXADRAssist data, int pos) {
            if (data.aResult) {
                if (data.actionType == HexAction.ACTION_READ) {
                    final List<ParameterItem> list = new ArrayList<>();
                    ParameterItem parameterItem;
                    int index = 0;
                    String result = data.value;
                    int total = Integer.parseInt(result.substring(0, 2), 16);
                    result = result.substring(2);
                    for (int i = 0; i < total; i++) {
                        String item = result.substring(i * 4, 4 * i + 4);
                        for (DisplayMeterBean displayMeterBean : displayMeterBeanList) {
                            if (item.equals(displayMeterBean.getPROTOCOL_ID())) {
                                parameterItem = new ParameterItem(displayMeterBean.getEN_NAME(), false, index++, displayMeterBean.getPROTOCOL_OBIS(), "", "");
                                list.add(parameterItem);
                                break;
                            }
                        }
                    }
                    HexThreadManager.runTaskOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().showToast(R.string.success);
                            getView().showData(list);
                            getView().hideLoading();
                        }
                    });
                } else {
                    HexThreadManager.runTaskOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().showToast(R.string.success);
                            getView().hideLoading();
                        }
                    });
                }
            } else {
                HexThreadManager.runTaskOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().showToast(R.string.failed);
                        getView().hideLoading();
                    }
                });
            }

        }

        @Override
        public void onFailure(String msg) {
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    getView().hideLoading();
                    getView().showToast(R.string.failed);
                }
            });
        }
    };

    public DisplayRotatePresenter(ParameterSetContact.View view) {
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
    public void getShowList() {
        getView().showLoading();
        HexThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                if (displayMeterBeanList.size() == 0) {
                    displayMeterBeanList = getBangladeshDisplayJson();
                }
                clientAPI.addListener(listener);
                clientAPI.action(getTranXAD());
            }
        });
    }

    private TranXADRAssist getTranXAD() {
        TranXADRAssist assist = new TranXADRAssist();
        assist.obis = "01#1.0.131.128.0.255#02";
        assist.actionType = HexAction.ACTION_READ;
        assist.dataType = HexDataFormat.OCTET_STRING;
        return assist;
    }

    @Override
    public void set(final List<ParameterItem> parameterItemList) {
        getView().showLoading();
        HexThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                if (displayMeterBeanList.size() == 0) {
                    displayMeterBeanList = getBangladeshDisplayJson();
                }
                clientAPI.addListener(listener);
                clientAPI.action(getTranXAD_set(parameterItemList));
            }
        });

    }

    private TranXADRAssist getTranXAD_set(List<ParameterItem> parameterItemList) {
        List<ParameterItem> parameterItems = parameterItemList;
//        List<ParameterItem> parameterItems = new ArrayList<>();//去重
//        for (ParameterItem parameterItem : parameterItemList){
//            boolean tag = true;
//            for (ParameterItem p : parameterItemList){
//                if (p.paramName.equals(parameterItem.paramName)){
//                    tag = false;
//                    break;
//                }
//            }
//            if (tag){
//                parameterItems.add(parameterItem);
//            }
//        }
        TranXADRAssist tranXADRAssist = new TranXADRAssist();
        tranXADRAssist.actionType = HexAction.ACTION_WRITE;
        tranXADRAssist.obis = "01#1.0.131.128.0.255#02";
        String data = String.format("%02x", parameterItems.size()).toUpperCase();
        for (ParameterItem parameterItem : parameterItems) {
            for (DisplayMeterBean displayMeterBean : displayMeterBeanList) {
                if (parameterItem.paramName.equals(displayMeterBean.getEN_NAME())) {
                    data = data + displayMeterBean.getPROTOCOL_ID();
                    break;
                }
            }
        }
        data = "09" + String.format("%02x", data.length() / 2).toUpperCase() + data;
        tranXADRAssist.writeData = data;
        return tranXADRAssist;
    }
}
