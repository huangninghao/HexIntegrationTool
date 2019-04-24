package com.hx.home.contact;


import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.UserInfoEntity;

/**
 * @author by HEC271
 * on 2018/3/13.
 */

public interface LoginContract {
    interface Presenter extends RxBasePresenter {
        void login(String username, String password);
    }

    interface View extends HexBaseView {

        void showUser(UserInfoEntity user);
    }
}
