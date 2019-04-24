package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.base.model.DisplayMeterBean;
import com.hx.base.model.FreezeMeterBean;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.ParameterSetContact;
import com.hx.set.model.ParameterItem;

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

import static com.hx.base.mInterface.module.baseModule.BaseService.getIndonesiaDisplayJson;
import static com.hx.base.mInterface.module.baseModule.BaseService.getIndonesiaJson;

public class DisplayRotatePresenter extends RxBasePresenterImpl<ParameterSetContact.View> implements ParameterSetContact.Presenter {
    private List<DisplayMeterBean> lst_FreezeMeterBean;
    private List<ParameterItem> list = new ArrayList<>();
    private HexClientAPI clientAPI;
    private String displayType = "";
    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(final List<TranXADRAssist> data) {
            // noticeResult(data, data.aResult);

            List<TranXADRAssist> txr = data;
            String obis = "";
            int index = 0;
            for (int i = 0; i < txr.size(); i++) {
                obis = String.format("%04x", Integer.valueOf(txr.get(i).structList.get(0).value)).toUpperCase();
                for (int j = 0; j < lst_FreezeMeterBean.size(); j++) {
                    if (obis.equals(lst_FreezeMeterBean.get(j).getPROTOCOL_ID())) {

                        list.add(new ParameterItem(lst_FreezeMeterBean.get(j).getEN_NAME(), true, index++, txr.get(i).structList.get(1).value, txr.get(i).structList.get(0).value, txr.get(i).structList.get(1).value));
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


        }


        @Override
        public void onSuccess(final TranXADRAssist data, int pos) {
            // noticeResult(data, data.aResult);

            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {

                    if (data.aResult) {
                        getView().showToast("Succeed");
                        getView().showData(list);
                        getView().hideLoading();
                    } else {
                        getView().showToast(R.string.failed);
                        getView().showData(list);
                        getView().hideLoading();
                    }
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
                    getView().showData(list);

                }
            });


        }
    };

    public DisplayRotatePresenter(ParameterSetContact.View view) {
        super(view);
        displayType = StringCache.get("displayType");
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
        lst_FreezeMeterBean = getIndonesiaDisplayJson();
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
                clientAPI.action(getTranXAD());
            }
        });
    }

    public TranXADRAssist getTranXAD() {
        //  List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();
        switch (displayType) {
            case "0":
                assist.obis = "1#" + "1-0:136.128.10.255" + "#2";
                break;
            case "1":
                assist.obis = "1#" + "1-0:136.128.11.255" + "#2";
                break;
            case "2":
                assist.obis = "1#" + "1-0:136.128.12.255" + "#2";
                break;

        }

        assist.actionType = HexAction.ACTION_READ_BLOCK;
        assist.dataType = HexDataFormat.DISPLAY_ARRAY;
        assist.structList = new ArrayList<>();

        TranXADRAssist.StructBean bean = new TranXADRAssist.StructBean();
        bean.dataType = HexDataFormat.LONG_UNSIGNED;
        assist.structList.add(bean);

        TranXADRAssist.StructBean bean1 = new TranXADRAssist.StructBean();
        bean1.dataType = HexDataFormat.VISIBLE_STRING;
        assist.structList.add(bean1);
        return assist;

    }

    public List<TranXADRAssist> getTranXAD_set(List<ParameterItem> parameterItemList) {
        this.list =parameterItemList;
        List<TranXADRAssist> list = new ArrayList<>();
        String obis = "";
        switch (displayType) {
            case "0":
                obis = "1#" + "1-0:136.128.10.255" + "#2";
                break;
            case "1":
                obis = "1#" + "1-0:136.128.11.255" + "#2";
                break;
            case "2":
                obis = "1#" + "1-0:136.128.12.255" + "#2";
                break;

        }

        for (int i = 0; i < parameterItemList.size(); i++) {
            TranXADRAssist assist = new TranXADRAssist();
            assist.obis = obis;
            assist.actionType = HexAction.ACTION_WRITE;
            assist.dataType = HexDataFormat.DISPLAY_ARRAY;
            assist.structList = new ArrayList<>();
            TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
            structBean.writeData = parameterItemList.get(i).classid;  //String.format("%04x",Integer.valueOf(parameterItemList.get(i).classid))+"#"+ parameterItemList.get(i).obis+"#"+String.format("%02x",Integer.valueOf(parameterItemList.get(i).attr.substring(0,1)));
            structBean.dataType = HexDataFormat.LONG_UNSIGNED;
            assist.structList.add(structBean);

            TranXADRAssist.StructBean structBean2 = new TranXADRAssist.StructBean();
            structBean2.writeData = parameterItemList.get(i).obis;  //String.format("%04x",Integer.valueOf(parameterItemList.get(i).classid))+"#"+ parameterItemList.get(i).obis+"#"+String.format("%02x",Integer.valueOf(parameterItemList.get(i).attr.substring(0,1)));
            structBean2.dataType = HexDataFormat.VISIBLE_STRING;
            assist.structList.add(structBean2);

            list.add(assist);
        }

        return list;

    }

}
