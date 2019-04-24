package com.hx.set.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.RateSetContact;
import com.hx.set.model.DTItem;
import com.hx.set.model.TimeItem;

import java.util.ArrayList;
import java.util.List;

public class RateSetPresenter extends RxBasePresenterImpl<RateSetContact.View> implements RateSetContact.Presenter {

    private List<DTItem> list = new ArrayList<>();
    private List<TimeItem> timeList = new ArrayList<>();
    public RateSetPresenter(RateSetContact.View view) {
        super(view);
    }

    @Override
    public void getShowDTList() {
        list.add(new DTItem("DT1",true));
        list.add(new DTItem("DT2",false));
        list.add(new DTItem("DT3",false));
        list.add(new DTItem("DT4",false));
        list.add(new DTItem("DT5",false));
        list.add(new DTItem("DT6",false));
        list.add(new DTItem("DT7",false));
        list.add(new DTItem("DT8",false));
        getView().showDTData(list);
    }

    @Override
    public void getShowTimeList() {
//        timeList.add(new TimeItem("09:00-10:00","T1"));
//        timeList.add(new TimeItem("09:00-10:00","T2"));
//        timeList.add(new TimeItem("09:00-10:00","T3"));
//        timeList.add(new TimeItem("09:00-10:00","T4"));
        timeList.add(new TimeItem(true));
        getView().showTimeData(timeList);
    }
}
