package com.hx.read.presenter.bangladesh.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.read.contact.bangladesh.HXE110.TokenContact;

public class TokenPresenter extends RxBasePresenterImpl<TokenContact.View> implements TokenContact.Presenter{
    public TokenPresenter(TokenContact.View view) {
        super(view);
    }

    @Override
    public void writeToken(String token) {

    }
}
