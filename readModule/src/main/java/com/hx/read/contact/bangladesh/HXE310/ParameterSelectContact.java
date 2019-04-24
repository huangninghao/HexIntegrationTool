package com.hx.read.contact.bangladesh.HXE310;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.read.model.ParameterItem;

import java.util.List;

public interface ParameterSelectContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
        void setReadData(List<ParameterItem> parameterItemList, int type);
    }

    interface View extends HexBaseView {
        void showData(List<ParameterItem> list);
    }
}
