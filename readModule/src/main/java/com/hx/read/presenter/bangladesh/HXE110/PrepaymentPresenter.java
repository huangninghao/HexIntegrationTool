package com.hx.read.presenter.bangladesh.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.read.contact.bangladesh.HXE110.PrepaymentContact;

import cn.hexing.model.TranXADRAssist;

public class PrepaymentPresenter extends RxBasePresenterImpl<PrepaymentContact.View> implements PrepaymentContact.Presenter {
    public PrepaymentPresenter(PrepaymentContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {

    }

    @Override
    public void read() {

    }

    @Override
    public boolean exportInstantaneousData(TranXADRAssist data) {
        return false;
    }
}
