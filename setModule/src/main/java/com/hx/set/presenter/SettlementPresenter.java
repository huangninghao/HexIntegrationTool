package com.hx.set.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.SettlementContact;

public class SettlementPresenter extends RxBasePresenterImpl<SettlementContact.View> implements SettlementContact.Presenter {
    public SettlementPresenter(SettlementContact.View view) {
        super(view);
    }
}
