package com.hx.read.presenter.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.OverloadThresholdContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static cn.hexing.dlms.HexDataFormat.DOUBLE_LONG_UNSIGNED;

public class OverloadThresholdPresenter extends RxBasePresenterImpl<OverloadThresholdContact.View> implements OverloadThresholdContact.Presenter {
    private HexClientAPI clientAPI;

    public OverloadThresholdPresenter(OverloadThresholdContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public void readThreshold() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "71#0-0:17.0.0.255#03";
                actionAssist.scale = -2;
                actionAssist.dataType = HexDataFormat.LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
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
                                } else
                                    getView().showToast(R.string.failed);
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
                clientAPI.action(actionAssist);
            }
        });

    }

    @Override
    public void setThreshold(final String threshold) {
        if (StringUtil.isEmpty(threshold)) {
            getView().showToast(R.string.invalid_data);
            return;
        }
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "71#0-0:17.0.0.255#03";
                actionAssist.dataType = HexDataFormat.LONG_UNSIGNED;
                actionAssist.scale = 2;
                String data = Float.parseFloat(threshold) * (float) Math.pow(10, actionAssist.scale) + "";
                actionAssist.writeData = data.substring(0, data.indexOf("."));
                actionAssist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (!data.aResult)
                                    getView().showToast(R.string.failed);
                                else
                                    getView().showToast(R.string.success);
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
                clientAPI.action(actionAssist);
            }
        });
    }

    @Override
    public void readTime() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "71#0-0:17.0.0.255#04";
                actionAssist.scale = -2;
                actionAssist.dataType = HexDataFormat.LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
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
                                } else
                                    getView().showToast(R.string.failed);
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
                clientAPI.action(actionAssist);
            }
        });
    }

    @Override
    public void setTime(final String time) {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "71#0-0:17.0.0.255#04";
                actionAssist.dataType = HexDataFormat.LONG_UNSIGNED;
                actionAssist.scale = 2;
                String data =  Float.parseFloat(time) * (float)Math.pow(10, actionAssist.scale) + "";
                actionAssist.writeData =  data.substring(0,data.indexOf("."));
                actionAssist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (!data.aResult)
                                    getView().showToast(R.string.failed);
                                else
                                    getView().showToast(R.string.success);
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
                clientAPI.action(actionAssist);
            }
        });
    }

    @Override
    public void read() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "71#0-0:17.0.0.255#05";
                actionAssist.scale = -2;
                actionAssist.dataType = HexDataFormat.LONG_UNSIGNED;
                actionAssist.actionType = HexAction.ACTION_READ;
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
                                } else
                                    getView().showToast(R.string.failed);
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
                clientAPI.action(actionAssist);
            }
        });
    }

    @Override
    public void set(final String string) {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "71#0-0:17.0.0.255#05";
                actionAssist.scale = 2;
                actionAssist.dataType = HexDataFormat.LONG_UNSIGNED;
                String data = Float.parseFloat(string) * (float) Math.pow(10, actionAssist.scale) + "";
                actionAssist.writeData = data.substring(0, data.indexOf("."));
                actionAssist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().hideLoading();
                                if (!data.aResult)
                                    getView().showToast(R.string.failed);
                                else
                                    getView().showToast(R.string.success);
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
                clientAPI.action(actionAssist);
            }
        });
    }
}
