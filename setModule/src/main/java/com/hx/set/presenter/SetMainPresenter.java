package com.hx.set.presenter;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.SetMainContact;

public class SetMainPresenter extends RxBasePresenterImpl<SetMainContact.View> implements SetMainContact.Presenter{
    public SetMainPresenter(HexBaseView view) {
        super((SetMainContact.View) view);
    }
}
