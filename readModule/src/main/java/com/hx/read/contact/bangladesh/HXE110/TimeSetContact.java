package com.hx.read.contact.bangladesh.HXE110;

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
