package com.hx.read.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;

import com.hx.read.contact.ReadMianContact;

public class ReadMainPresenter extends RxBasePresenterImpl<ReadMianContact.View> implements ReadMianContact.Presenter {
    public ReadMainPresenter(ReadMianContact.View view) {
        super(view);
    }
}
