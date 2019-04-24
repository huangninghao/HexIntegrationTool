package com.hx.read.contact.senegal.HXE310_KP;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface TimeSetContact {
    interface Presenter extends RxBasePresenter {
        void readTime();
        void writeTime(String time);
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
