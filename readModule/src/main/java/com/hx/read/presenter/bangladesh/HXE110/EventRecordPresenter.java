package com.hx.read.presenter.bangladesh.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.read.contact.bangladesh.HXE110.EventRecordContact;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public class EventRecordPresenter extends RxBasePresenterImpl<EventRecordContact.View> implements EventRecordContact.Presenter {
    public EventRecordPresenter(EventRecordContact.View view) {
        super(view);
    }

    @Override
    public void getShowList(TranXADRAssist assist) {

    }

    @Override
    public boolean exportFreezeData(List<TranXADRAssist> obisListData) {
        return false;
    }
}
