package com.hx.read.presenter.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.GPRSContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexStringUtil;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.TranXADRAssist;

public class GPRSPresenter extends RxBasePresenterImpl<GPRSContact.View> implements GPRSContact.Presenter {
    private HexClientAPI clientAPI;

    public GPRSPresenter(GPRSContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public void readGPRS() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assists = new ArrayList<>();
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "29#0-0:2.1.0.255#06";
                actionAssist.dataType = HexDataFormat.OCTET_STRING;
                actionAssist.actionType = HexAction.ACTION_READ;
                assists.add(actionAssist);
                actionAssist = new TranXADRAssist();
                actionAssist.obis = "45#0-0:25.4.0.255#02";
                actionAssist.dataType = HexDataFormat.OCTET_STRING;
                actionAssist.actionType = HexAction.ACTION_READ;
                assists.add(actionAssist);
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        if (data.aResult) {
                            final TranXADRAssist tranXADRAssist = analysisData(data);
                            HexThreadManager.runTaskOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (getView() != null) {
                                        getView().showToast(R.string.success);
                                        getView().showData(tranXADRAssist);
                                    }
                                }
                            });
                        } else {
                            HexThreadManager.runTaskOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (getView() != null) {
                                        getView().hideLoading();
                                        getView().showToast(R.string.failed);
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(final String msg) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getView() != null) {
                                    getView().hideLoading();
                                    getView().showToast(msg);
                                }
                            }
                        });
                    }
                });
                clientAPI.action(assists);
            }
        });
    }

    @Override
    public void setGPRS(final String ip, final String port, final String apn) {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                String[] item = ip.split("\\.");
                String ipp = HexStringUtil.padRight(item[0], 3, '0') + "." + HexStringUtil.padRight(item[1], 3, '0')
                        + "." + HexStringUtil.padRight(item[2], 3, '0') + "." + HexStringUtil.padRight(item[3], 3, '0')
                        + ":" + HexStringUtil.padRight(port, 5, '0');
                String param1 = HexStringUtil.asciiToHexString(ipp).toUpperCase();
                String param2 = HexStringUtil.asciiToHexString(apn).toUpperCase();
                List<TranXADRAssist> assists = new ArrayList<>();
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "29#0-0:2.1.0.255#06";
                actionAssist.actionType = HexAction.ACTION_WRITE;
                actionAssist.writeData = "01010915" + param1;
                assists.add(actionAssist);
                actionAssist = new TranXADRAssist();
                actionAssist.obis = "45#0-0:25.4.0.255#02";
                actionAssist.writeData = "09" + HexStringUtil.padRight(HexStringUtil.toHex(apn.length()), 2, '0') + param2;
                actionAssist.actionType = HexAction.ACTION_WRITE;
                assists.add(actionAssist);
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onFailure(final String msg) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (getView() != null) {
                                    getView().hideLoading();
                                    getView().showToast(msg);
                                }
                            }
                        });
                    }

                    @Override
                    public void onSuccess(final TranXADRAssist data, final int pos) {
                        if (data.aResult) {

                            HexThreadManager.runTaskOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (getView() != null) {
                                        getView().hideLoading();
                                        getView().showToast(R.string.success);
                                    }
                                }
                            });
                        } else {
                            HexThreadManager.runTaskOnMainThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (getView() != null) {
                                        getView().hideLoading();
                                        getView().showToast(R.string.failed);
                                    }
                                }
                            });
                        }
                    }
                });
                clientAPI.action(assists);
            }
        });


    }

    private TranXADRAssist analysisData(TranXADRAssist tranXADRAssist) {
        TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
        if (tranXADRAssist.obis.equals("29#0-0:2.1.0.255#06")) {
            String value = HexStringUtil.convertHexToString(tranXADRAssist.recStrData.substring(8));//000.000.000.000:0000
            tranXADRAssist.structList = new ArrayList<>();
            structBean.name = "ip";
            String[] item = (value.substring(0, 15)).split("\\.");
            String ip;
            ip = (Integer.parseInt(item[0]) + ".") + (Integer.parseInt(item[1]) + ".") + (Integer.parseInt(item[2]) + ".") + (Integer.parseInt(item[3]));
            structBean.value = ip;
            tranXADRAssist.structList.add(structBean);
            structBean = new TranXADRAssist.StructBean();
            structBean.name = "port";
            structBean.value = Integer.parseInt(value.substring(16)) + "";
            tranXADRAssist.structList.add(structBean);
        } else {
            structBean = new TranXADRAssist.StructBean();
            structBean.name = "apn";
            structBean.value = HexStringUtil.convertHexToString(tranXADRAssist.value);
            tranXADRAssist.structList.add(structBean);
        }
        return tranXADRAssist;
    }
}
