package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.base.model.FreezeMeterBean;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.ParameterSetContact;
import com.hx.set.model.ParameterItem;
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

import static com.hx.base.mInterface.module.baseModule.BaseService.getIndonesiaJson;

public class ParameterSetPresenter extends RxBasePresenterImpl<ParameterSetContact.View> implements ParameterSetContact.Presenter {
    private List<FreezeMeterBean> lst_FreezeMeterBean;
    private List<ParameterItem> list = new ArrayList<>();
    private HexClientAPI clientAPI;
    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final TranXADRAssist data, int pos) {
            // noticeResult(data, data.aResult);
            if (data.actionType!=HexAction.ACTION_WRITE) {
                TranXADRAssist txr = data;
                String obis = "";
                int index = 0;
                for (int i = 0; i < txr.structList.size(); i++) {
                    obis = parseObis(txr.structList.get(i).obis.substring(0, 4), txr.structList.get(i).obis.substring(4, txr.structList.get(i).obis.length() - 2), txr.structList.get(i).obis.substring(txr.structList.get(i).obis.length() - 2, txr.structList.get(i).obis.length()));
                    for (int j = 0; j < lst_FreezeMeterBean.size(); j++) {
                        if (obis.equals(lst_FreezeMeterBean.get(j).getOBIS())) {

                            list.add(new ParameterItem(lst_FreezeMeterBean.get(j).getEN_NAME(), true, index++, lst_FreezeMeterBean.get(j).getPROTOCOL_OBIS(),lst_FreezeMeterBean.get(j).getCLASS_ID(),lst_FreezeMeterBean.get(j).getATTRIBUTE()));
                        }

                    }


                }


                HexThreadManager.runTaskOnMainThread(new Runnable() {
                    @Override
                    public void run() {


                        getView().showData(list);
                        getView().hideLoading();
                    }
                });
            }
            else
            {
                HexThreadManager.runTaskOnMainThread(new Runnable() {
                    @Override
                    public void run() {

                        if (data.aResult) {
                            getView().showToast(R.string.suc);
                        }
                        else
                        {
                            getView().showToast(R.string.failed);

                        }


                        getView().hideLoading();
                    }
                });

            }




        }

        @Override
        public void onFailure(String msg) {
            //noticeResult(msg, false);
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {

                    getView().hideLoading();

                    getView().showToast(R.string.failed);
                    getView().showData(list);

                }
            });


        }
    };

    public ParameterSetPresenter(ParameterSetContact.View view) {
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
        lst_FreezeMeterBean = getIndonesiaJson();
    }

    @Override
    public void getShowList() {
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",true));
        //getView().showData(list);

        read();
    }

    private String parseObis(String classid, String obis, String attr) {
        String rtn =
                Integer.valueOf(obis.substring(4, 6)).toString() + "." +
                        Integer.valueOf(obis.substring(6, 8)).toString() + "." +
                        Integer.valueOf(obis.substring(8, 10)).toString();
        return rtn;


    }

    public void set(final List<ParameterItem> parameterItemList) {
        getView().showLoading();

        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                //initData();
                clientAPI.action(getTranXAD_set(parameterItemList));
            }
        });

    }

    public void read() {
        getView().showLoading();
        list.clear();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                //initData();
                clientAPI.readCaptureObjectNew(getTranXAD());
            }
        });
    }

    public TranXADRAssist getTranXAD() {
        //  List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();

        assist.obis = "7#" + "0-0:98.1.0.255" + "#3";
        assist.actionType = HexAction.ACTION_READ;

        return assist;

    }

    public TranXADRAssist getTranXAD_set(List<ParameterItem> parameterItemList) {
        //  List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();

        assist.obis = "7#" + "0-0:98.1.0.255" + "#3";
        assist.actionType = HexAction.ACTION_WRITE;
        assist.dataType = HexDataFormat.FREEZE_CAPTURE;
        assist.structList = new ArrayList<>();
        for (int i = 0; i < parameterItemList.size(); i++) {
            TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
            structBean.value = String.format("%04x",Integer.valueOf(parameterItemList.get(i).classid))+"#"+ parameterItemList.get(i).obis+"#"+String.format("%02x",Integer.valueOf(parameterItemList.get(i).attr.substring(0,1)));
            assist.structList.add(structBean);
        }

        return assist;

    }

}
