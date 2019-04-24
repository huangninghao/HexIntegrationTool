package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.indonesia.HXE320.SetMainContact;

public class SetMainPresenter extends RxBasePresenterImpl<SetMainContact.View> implements SetMainContact.Presenter{
    public SetMainPresenter(HexBaseView view) {
        super((SetMainContact.View) view);
    }
}
