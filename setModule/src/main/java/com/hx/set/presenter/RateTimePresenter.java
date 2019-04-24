package com.hx.set.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.RateTimeContact;

public class RateTimePresenter extends RxBasePresenterImpl<RateTimeContact.View> implements RateTimeContact.Presenter {
    public RateTimePresenter(RateTimeContact.View view) {
        super(view);
    }
}
