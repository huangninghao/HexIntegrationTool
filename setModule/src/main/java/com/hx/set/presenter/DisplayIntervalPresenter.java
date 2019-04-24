package com.hx.set.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.DisplayIntervalContact;

public class DisplayIntervalPresenter extends RxBasePresenterImpl<DisplayIntervalContact.View> implements DisplayIntervalContact.Presenter {
    public DisplayIntervalPresenter(DisplayIntervalContact.View view) {
        super(view);
    }
}
