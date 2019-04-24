package com.hx.home.contact;


import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

/**
 * @author by HEC271
 * on 2018/3/13.
 */

public interface WelcomeContract {
    interface Presenter extends RxBasePresenter {
        void verifyLogin();
    }

    interface View extends HexBaseView {
        void verifyResult(boolean bool);
    }
}
