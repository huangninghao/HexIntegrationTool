package com.hx.home.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.HomeMenu;

import java.util.List;

/**
 * @author by HEC271
 * on 2018/3/13.
 */

public interface HomeContract {
    interface Presenter extends RxBasePresenter {
        void getLeftMenu();
    }

    interface View extends HexBaseView {
        void showLeftMenu(List<HomeMenu> menus);
    }
}
