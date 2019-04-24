package com.hx.read.presenter.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.TimeSetContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.PortConfig;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static cn.hexing.dlms.HexDataFormat.DATE_TIME;

public class TimeSetPresenter extends RxBasePresenterImpl<TimeSetContact.View> implements TimeSetContact.Presenter{
    private HexClientAPI clientAPI;
    public TimeSetPresenter(TimeSetContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public void readTime() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                HexLog.d("readTime", "=========开始=========");
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "8#0.0.1.0.0.255#02";
                actionAssist.dataType = DATE_TIME;
                actionAssist.actionType = HexAction.ACTION_READ;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist data, int pos) {
                        if (data.aResult == true) {
                            noticeResult(data, true);
                        }else {
                            noticeResult(data, false);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(actionAssist);

                HexLog.d("readTime", "=========正常结束=========");
            }
        });

    }

    @Override
    public void writeTime(final String time) {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                HexLog.d("writeTime", "=========开始=========");
                TranXADRAssist actionAssist = new TranXADRAssist();
                actionAssist.obis = "8#0.0.1.0.0.255#02";
                actionAssist.dataType = DATE_TIME;
                actionAssist.writeData = time;
                actionAssist.actionType = HexAction.ACTION_WRITE;
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist data, int pos) {
                        if (data.aResult == true) {
                            noticeResult(data, true);
                        }else {
                            noticeResult(data, false);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        super.onFailure(msg);
                        noticeResult(msg, false);
                    }
                });
                clientAPI.action(actionAssist);
                HexLog.d("writeTime", "=========正常结束=========");
            }
        });
    }

    /**
     * 通知结果
     *
     * @param object object
     */
    private void noticeResult(final Object object, final boolean isSuccess) {
        HexThreadManager.runTaskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (getView() != null) {
                    getView().hideLoading();
                }
                if (isSuccess) {
                    ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.success));
                    getView().showData((TranXADRAssist) object);
                } else {
                    ToastUtils.showToast(ReadApp.getInstance(), ReadApp.getInstance().getString(R.string.failed));
                }
            }
        });
    }
}
