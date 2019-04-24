package com.hx.read.presenter.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.LowPowerThesholdContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static cn.hexing.dlms.HexDataFormat.LONG_UNSIGNED;

public class LowPowerThesholdPresenter extends RxBasePresenterImpl<LowPowerThesholdContact.View> implements LowPowerThesholdContact.Presenter{
    private HexClientAPI clientAPI;

    public LowPowerThesholdPresenter(LowPowerThesholdContact.View view) {
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
                actionAssist.obis = "3#1-0:13.31.0.255#02";
                actionAssist.scale = -3;
                actionAssist.dataType = LONG_UNSIGNED;
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
        if (StringUtil.isEmpty(threshold)){
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
                actionAssist.obis = "3#1-0:13.31.0.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.scale = 3;
                String data =  Float.parseFloat(threshold) * (float)Math.pow(10, actionAssist.scale) + "";
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
    public void readTime() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "3#1-0:13.43.0.255#02";
                actionAssist.scale = 0;
                actionAssist.dataType = LONG_UNSIGNED;
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
        try {
            if (Integer.parseInt(time) > 65535) {
                getView().showToast(R.string.invalid_data);
            }
        } catch (Exception e) {
            getView().showToast(R.string.invalid_data);
        }

        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "3#1-0:13.43.0.255#02";
                actionAssist.dataType = LONG_UNSIGNED;
                actionAssist.writeData = time;
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
