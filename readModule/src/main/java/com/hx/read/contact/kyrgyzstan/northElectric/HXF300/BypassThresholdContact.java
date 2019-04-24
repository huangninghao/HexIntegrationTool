package com.hx.read.contact.kyrgyzstan.northElectric.HXF300;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface BypassThresholdContact {
    interface Presenter extends RxBasePresenter {
        void readThreshold();
        void setThreshold(String threshold);
        void readTime();
        void setTime(String time);

    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
