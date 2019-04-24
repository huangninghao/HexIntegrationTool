package com.hx.home.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.HomeMenu;

import java.util.List;

public interface ManualContract {
    interface Presenter extends RxBasePresenter {
        void getShowList();
    }

    interface View extends HexBaseView {
        void showList(List<HomeMenu> menus);
    }
}
