package com.hx.read.presenter.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.CTPTContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static cn.hexing.dlms.HexDataFormat.LONG_UNSIGNED;

public class CTPTPresenter extends RxBasePresenterImpl<CTPTContact.View> implements CTPTContact.Presenter {
    private HexClientAPI clientAPI;

    public CTPTPresenter(CTPTContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public void readCT() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assistList = new ArrayList<>();
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.2.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
                assistList.add(actionAssist);
                actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.5.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
                assistList.add(actionAssist);
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (data.aResult) {
                                    getView().showData(data);
                                    getView().showToast(R.string.success);
                                } else {
                                    getView().showToast(R.string.failed);
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
                });
                clientAPI.action(assistList);
            }
        });

    }

    @Override
    public void setCT(final String ct1,final String ct2) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assistList = new ArrayList<>();
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.2.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_WRITE;
                actionAssist.writeData = ct1;
                assistList.add(actionAssist);
                actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.5.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_WRITE;
                actionAssist.writeData = ct2;
                assistList.add(actionAssist);
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, final int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (data.aResult) {
                                    if (pos == 1) {
                                        getView().showToast(R.string.success);
                                    }
                                } else {
                                    getView().showToast(R.string.failed);
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
                });
                clientAPI.action(assistList);
            }
        });
    }

    @Override
    public void readPT() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assistList = new ArrayList<>();
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.3.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
                assistList.add(actionAssist);
                actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.6.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
                assistList.add(actionAssist);
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (data.aResult) {
                                    getView().showData(data);
                                    getView().showToast(R.string.success);
                                } else {
                                    getView().showToast(R.string.failed);
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
                });
                clientAPI.action(assistList);
            }
        });

    }

    @Override
    public void setPT(final String pt1, final String pt2) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assistList = new ArrayList<>();
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.3.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_WRITE;
                actionAssist.writeData = pt1;
                assistList.add(actionAssist);
                actionAssist = new TranXADRAssist();
                actionAssist.obis = "01#1-0:0.4.6.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_WRITE;
                actionAssist.writeData = pt2;
                assistList.add(actionAssist);
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, final int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (data.aResult) {
                                    if (pos == 1) {
                                        getView().showToast(R.string.success);
                                    }
                                } else {
                                    getView().hideLoading();
                                    getView().showToast(R.string.failed);
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
                });
                clientAPI.action(assistList);
            }
        });
    }
}
