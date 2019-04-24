package com.hx.set.contact.bangladesh.HXE310_KP;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.GPRSBean;

public interface GPRSContact {
    interface Presenter extends RxBasePresenter {
        void readGprs();

        void writeGprs(GPRSBean bean);
    }

    interface View extends HexBaseView {
        void showData(GPRSBean bean);
    }
}
