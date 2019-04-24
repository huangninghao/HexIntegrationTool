package com.hx.home.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.home.contact.ManualContract;

public class ManualPresenter extends RxBasePresenterImpl<ManualContract.View> implements ManualContract.Presenter {
    public ManualPresenter(ManualContract.View view) {
        super(view);
    }

    @Override
    public void getShowList() {

    }
}
