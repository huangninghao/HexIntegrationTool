package com.hx.read.contact.senegal.HXE310_KP;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.FeaturesMenuBean;

import java.util.List;

public interface FunctionContact {

    interface Presenter extends RxBasePresenter {
        void getShowList();
    }

    interface View extends HexBaseView {
        void showData(List<FeaturesMenuBean> list);
    }
}
