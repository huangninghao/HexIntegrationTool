package com.hx.read.contact.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface OverloadThresholdContact {
    interface Presenter extends RxBasePresenter {
        void readThreshold();
        void setThreshold(String threshold);
        void readTime();
        void setTime(String time);
        void read();
        void set(String string);

    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
