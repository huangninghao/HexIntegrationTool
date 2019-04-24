package com.hx.home.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

public interface RegisterContract {
    interface Presenter extends RxBasePresenter {
        void register(String username, String pwd);
    }

    interface View extends HexBaseView {
        void showResult(boolean bool);
    }
}
