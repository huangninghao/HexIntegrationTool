package com.hx.set.contact.indonesia.HXE320;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.set.model.ParameterItem;

import java.util.List;

public interface ParameterSetContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
        void set( List<ParameterItem>  parameterItemList);
    }

    interface View extends HexBaseView {
        void showData(List<ParameterItem> list);
    }
}
