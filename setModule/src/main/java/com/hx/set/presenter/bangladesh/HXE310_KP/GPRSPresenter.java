package com.hx.set.presenter.bangladesh.HXE310_KP;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hx.base.model.GPRSBean;
import com.hx.set.R;
import com.hx.set.contact.bangladesh.HXE310_KP.GPRSContact;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

import static com.hx.base.BaseConstant.APN_OBIS;
import static com.hx.base.BaseConstant.PDP_OBIS;

public class GPRSPresenter extends RxBasePresenterImpl<GPRSContact.View> implements GPRSContact.Presenter {
    private HexClientAPI clientAPI;

    public GPRSPresenter(GPRSContact.View view) {
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
    }

    @Override
    public void readGprs() {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                List<TranXADRAssist> assistList = new ArrayList<>();
                TranXADRAssist assist = new TranXADRAssist();
                assist.dataType = HexDataFormat.GPRS_IP_PORT_APN_STRUCT;
                assist.obis = APN_OBIS;
                assist.actionType = HexAction.ACTION_READ;

                List<TranXADRAssist.StructBean> structBeen = new ArrayList<>();
                TranXADRAssist.StructBean item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.GPRS_IP;
                structBeen.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.GPRS_PORT;
                structBeen.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.GPRS_APN;
                structBeen.add(item);
                assist.structList = structBeen;

                assistList.add(assist);

                assist = new TranXADRAssist();
                assist.obis = PDP_OBIS;
                assist.dataType = HexDataFormat.GPRS_PDP_NAME_PASSWORD_STRUCT;
                assist.actionType = HexAction.ACTION_READ;

                structBeen = new ArrayList<>();
                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.OCTET_STRING_ASCS;
                structBeen.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.OCTET_STRING_ASCS;
                structBeen.add(item);
                assist.structList = structBeen;

                assistList.add(assist);
                final GPRSBean bean = new GPRSBean();
                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(TranXADRAssist item, int pos) {
                        if (pos == 0) {
                            bean.setApn(item.structList.get(2).value);
                            bean.setIp(item.structList.get(0).value);
                            bean.setPort(item.structList.get(1).value);
                        }
                        if (pos == 1) {
                            bean.setPdpName(item.structList.get(0).value);
                            bean.setPdpPassword(item.structList.get(1).value);
                            noticeResult(bean, true);
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        noticeResult(bean, false);
                    }
                });
                clientAPI.action(assistList);

            }
        });
    }

    @Override
    public void writeGprs(final GPRSBean bean) {
        if (StringUtil.isEmpty(bean.getApn()) || StringUtil.isEmpty(bean.getIp()) || StringUtil.isEmpty(bean.getPort())
                || StringUtil.isEmpty(bean.getSms()) || StringUtil.isEmpty(bean.getPdpName()) || StringUtil.isEmpty(bean.getPdpPassword())) {
            getView().showToast(R.string.enter_error);
            return;
        }
        if (!isIpAddress(bean.getIp())) {
            getView().showToast(R.string.ip_err);
            return;
        }
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                final List<TranXADRAssist> assistList = new ArrayList<>();

                //ip port apn
                TranXADRAssist assist = new TranXADRAssist();
                assist.obis = APN_OBIS;
                assist.dataType = HexDataFormat.GPRS_IP_PORT_APN_STRUCT;
                assist.actionType = HexAction.ACTION_WRITE;

                List<TranXADRAssist.StructBean> structBeen = new ArrayList<>();
                TranXADRAssist.StructBean item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.GPRS_IP;
                item.value = bean.getIp();
                structBeen.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.GPRS_PORT;
                item.value = bean.getPort();
                structBeen.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.GPRS_APN;
                item.value = bean.getApn();
                structBeen.add(item);
                assist.structList = structBeen;
                assistList.add(assist);

                // pdp name password
                assist = new TranXADRAssist();
                assist.obis = PDP_OBIS;
                assist.dataType = HexDataFormat.OCTET_STRING;
                assist.actionType = HexAction.ACTION_WRITE;
                structBeen = new ArrayList<>();
                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.OCTET_STRING_ASCS;
                item.value = bean.getPdpName();
                structBeen.add(item);

                item = new TranXADRAssist.StructBean();
                item.dataType = HexDataFormat.OCTET_STRING_ASCS;
                item.value = bean.getPdpPassword();
                structBeen.add(item);
                assist.structList = structBeen;

                assistList.add(assist);

                clientAPI.addListener(new IHexListener() {
                    @Override
                    public void onSuccess(final TranXADRAssist data, final int pos) {
                        HexThreadManager.runTaskOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                if (pos >= assistList.size()) {
                                    if (data.aResult) {
                                        getView().showToast(R.string.set_success);
                                    } else {
                                        getView().showToast(R.string.failed);
                                    }
                                    getView().hideLoading();
                                    getView().showData(bean);
                                }
                            }
                        });
                    }
                });
                clientAPI.action(assistList);
            }
        });

    }

    /**
     * ip校验
     *
     * @param s
     * @return Boolean
     */
    private Boolean isIpAddress(String s) {
        String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 通知结果
     *
     * @param bean GPRSBean
     */
    private void noticeResult(final GPRSBean bean, final boolean isSuccess) {
        HexThreadManager.runTaskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isSuccess) {
                    if (getView() != null) {
                        getView().hideLoading();
                        getView().showData(bean);
                    }
                } else {
                    getView().hideLoading();
                    getView().showToast(R.string.failed);
                }
            }
        });
    }
}
