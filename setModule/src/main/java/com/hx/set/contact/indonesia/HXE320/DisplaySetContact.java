package com.hx.set.contact.indonesia.HXE320;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.set.model.FunctionItem;

import java.util.List;

public interface DisplaySetContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
    }

    interface View extends HexBaseView {
        void showData(List<FunctionItem> list);
    }
}
