package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.RateSetContact;
import com.hx.set.model.DTItem;
import com.hx.set.model.TimeItem;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.dlms.IHexListener;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class RateSetPresenter extends RxBasePresenterImpl<RateSetContact.View> implements RateSetContact.Presenter {
    private HexClientAPI clientAPI;
    private List<DTItem> list = new ArrayList<>();
    private List<TimeItem> timeList = new ArrayList<>();
    private List<TranXADRAssist> sorcedata = new ArrayList<>();
    private int curDt = 0;
    private IHexListener listener = new IHexListener() {
        @Override
        public void onSuccess(List<TranXADRAssist> data) {
            // noticeResult(data, data.aResult);
            sorcedata = data;
            dealSourceData(data);
            HexThreadManager.runTaskOnMainThread(new Runnable() {
                @Override
                public void run() {
                    getView().showDTData(list);
                    timeList.add(new TimeItem(true));
                    getView().showTimeData(timeList);
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
                    timeList.add(new TimeItem(true));
                    getView().showToast(R.string.failed);
                    getView().showDTData(list);
                    getView().showTimeData(timeList);
                }
            });


        }
    };

    public RateSetPresenter(RateSetContact.View view) {
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

    public void dealSourceData(List<TranXADRAssist> srcData) {
//        for (int i = 0; i < srcData.size(); i++
//                ) {
//            list.add(new DTItem("DT" + String.valueOf(i + 1), i == 0 ? true : false));
//        }


        boolean ifexsit = false;

        for (int i = 0; i < sorcedata.size(); i++) {
            if (0 == i) {

                ifexsit = true;
                break;
            }

        }
        if (ifexsit) {

            TranXADRAssist tagdata = sorcedata.get(0);
            for (int i = 0; i < tagdata.structList.size(); i++) {
                String date = tagdata.structList.get(i).beanItems.get(0).value.substring(0, 4);
                date = String.valueOf(Integer.parseInt(date.substring(0, 2), 16)) + ":" + String.valueOf(Integer.parseInt(date.substring(2, 4), 16));
                String fee = tagdata.structList.get(i).beanItems.get(2).value;
                timeList.add(new TimeItem(date, "T" + fee, i));

            }
        }


    }

    public void add(TimeItem it) {
        //timeList.add(it);
        if (curDt > sorcedata.size())//不能跳表添加
        {
            getView().showToast(R.string.noadd);
            return;
        } else if (curDt == sorcedata.size()) {
            TranXADRAssist assist = new TranXADRAssist();
            assist.structList = new ArrayList<>();
            assist.dataType = HexDataFormat.DAY_RATE;
            assist.obis = "20#0.0.13.0.0.255#09";
            assist.actionType = HexAction.ACTION_READ_BLOCK;
            assist.value = String.valueOf(curDt);
            sorcedata.add(assist);

        }

        timeList.add(timeList.size() - 1, it);


        TranXADRAssist tagdata = sorcedata.get(curDt);
        TranXADRAssist.StructBean structBean = new TranXADRAssist.StructBean();
        structBean.beanItems = new ArrayList<>();
        TranXADRAssist.StructBean.BeanItem beanItem = new TranXADRAssist.StructBean.BeanItem();
        beanItem.value = String.format("%02x", Integer.valueOf(it.time.split(":")[0])) + String.format("%02x", Integer.valueOf(it.time.split(":")[1])) + "FFFF";

        TranXADRAssist.StructBean.BeanItem beanItem1 = new TranXADRAssist.StructBean.BeanItem();
        beanItem1.value = "00000A0064FF";

        TranXADRAssist.StructBean.BeanItem beanItem2 = new TranXADRAssist.StructBean.BeanItem();
        beanItem2.value = it.type.substring(1, 2);
        structBean.beanItems.add(beanItem);
        structBean.beanItems.add(beanItem1);
        structBean.beanItems.add(beanItem2);
        tagdata.structList.add(structBean);

        getView().showTimeData(timeList);
    }


    public void delete(TimeItem it) {
        //timeList.add(it);

        if (curDt < sorcedata.size() - 1)
        {
            if(sorcedata.get(curDt).structList.size()<=1)
            {
                getView().showToast(R.string.nodelete);
                return;
            }
        }


            timeList.remove(it);
        TranXADRAssist tagdata = sorcedata.get(curDt);

        tagdata.structList.remove(it.id);
        if (tagdata.structList.size() == 0) {
            sorcedata.remove(curDt);
        }
        for (int i = 0; i < timeList.size(); i++) {
            timeList.get(i).id = i;

        }

        getView().showTimeData(timeList);
    }

    public TranXADRAssist getTranXAD() {
      //  List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist assist = new TranXADRAssist();
        assist.structList = new ArrayList<>();
        assist.dataType = HexDataFormat.DAY_RATE;
        assist.obis = "20#0.0.13.0.0.255#09";
        assist.actionType = HexAction.ACTION_READ_BLOCK;
        TranXADRAssist.StructBean bean = new TranXADRAssist.StructBean();
        bean.dataType = HexDataFormat.UNSIGNED;
        assist.structList.add(bean);
        bean.beanItems = new ArrayList<>();
        TranXADRAssist.StructBean.BeanItem beanItem = new TranXADRAssist.StructBean.BeanItem();
        beanItem.dataType = HexDataFormat.OCTET_STRING;
        assist.structList.get(0).beanItems.add(beanItem);
        beanItem = new TranXADRAssist.StructBean.BeanItem();
        beanItem.dataType = HexDataFormat.OCTET_STRING;
        assist.structList.get(0).beanItems.add(beanItem);

        beanItem = new TranXADRAssist.StructBean.BeanItem();
        beanItem.dataType = HexDataFormat.LONG_UNSIGNED;
        assist.structList.get(0).beanItems.add(beanItem);


//        TranXADRAssist.StructBean bean1 = new TranXADRAssist.StructBean();
//        bean1.dataType = HexDataFormat.UNSIGNED;
//        assist.structList.add(bean1);
//        bean1.beanItems = new ArrayList<>();
//        TranXADRAssist.StructBean.BeanItem beanItem1 = new TranXADRAssist.StructBean.BeanItem();
//        beanItem1.dataType = HexDataFormat.OCTET_STRING;
//        assist.structList.get(1).beanItems.add(beanItem1);
//        beanItem1 = new TranXADRAssist.StructBean.BeanItem();
//        beanItem1.dataType = HexDataFormat.OCTET_STRING;
//        assist.structList.get(1).beanItems.add(beanItem1);
//
//        beanItem1 = new TranXADRAssist.StructBean.BeanItem();
//        beanItem1.dataType = HexDataFormat.LONG_UNSIGNED;
//        assist.structList.get(1).beanItems.add(beanItem1);

        //list.add(assist);
        return assist;

    }

    @Override
    public void getShowDTList() {
        list.clear();
        timeList.clear();
        list.add(new DTItem("DT1", true));
        list.add(new DTItem("DT2", false));
        list.add(new DTItem("DT3", false));
        list.add(new DTItem("DT4", false));
        list.add(new DTItem("DT5", false));
        list.add(new DTItem("DT6", false));
        list.add(new DTItem("DT7", false));
        list.add(new DTItem("DT8", false));
        read();
//        getView().showDTData(list);
    }

    @Override
    public void getShowTimeList(int dtIndex) {
        curDt = dtIndex;
//        timeList.add(new TimeItem("09:00-10:00", "T1"));
//        timeList.add(new TimeItem("09:00-10:00", "T2"));
//        timeList.add(new TimeItem("09:00-10:00", "T3"));
//        timeList.add(new TimeItem("09:00-10:00", "T4"));
        timeList.clear();
        boolean ifexsit = false;

        for (int i = 0; i < sorcedata.size(); i++) {
            if (dtIndex == i) {
                ifexsit = true;

                break;
            }

        }
        if (ifexsit) {
            TranXADRAssist tagdata = sorcedata.get(dtIndex);
            for (int i = 0; i < tagdata.structList.size(); i++) {

                String date = tagdata.structList.get(i).beanItems.get(0).value.substring(0, 4);
                date = String.format("%02d", Integer.valueOf(Integer.parseInt(date.substring(0, 2), 16))) + ":" + String.format("%02d", Integer.valueOf(Integer.parseInt(date.substring(2, 4), 16)));
                String fee = tagdata.structList.get(i).beanItems.get(2).value;
                timeList.add(new TimeItem(date, "T" + fee, i));

            }
        }
        timeList.add(new TimeItem(true));
        getView().showTimeData(timeList);
    }

    @Override
    public void getWriteData() {
        getView().showWriteData(sorcedata);

    }

}
