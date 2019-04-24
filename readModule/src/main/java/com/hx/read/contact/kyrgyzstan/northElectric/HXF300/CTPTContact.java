package com.hx.read.contact.kyrgyzstan.northElectric.HXF300;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface CTPTContact {
    interface Presenter extends RxBasePresenter {
        void readCT();
        void setCT(String ct1,String ct2);
        void readPT();
        void setPT(String pt1,String pt2);

    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
