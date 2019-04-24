package com.hx.set.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.set.model.ParameterItem;

import java.util.List;

public interface ParameterSelectContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
    }

    interface View extends HexBaseView {
        void showData(List<ParameterItem> list);
    }
}
