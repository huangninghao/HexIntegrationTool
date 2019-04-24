package com.hx.read.contact.bangladesh.HXE310;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.read.model.ParameterItem;

import java.util.List;

public interface ParameterSetContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
        void set(List<ParameterItem> parameterItemList);
    }

    interface View extends HexBaseView {
        void showData(List<ParameterItem> list);
    }
}
