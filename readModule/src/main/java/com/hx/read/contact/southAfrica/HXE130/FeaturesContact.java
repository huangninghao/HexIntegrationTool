package com.hx.read.contact.southAfrica.HXE130;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.FeaturesMenuBean;

import java.util.List;

public interface FeaturesContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
    }

    interface View extends HexBaseView {
        void showData(List<FeaturesMenuBean> list);
    }
}
