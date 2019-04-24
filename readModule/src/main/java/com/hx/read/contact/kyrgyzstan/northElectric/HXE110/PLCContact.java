package com.hx.read.contact.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface PLCContact {
    interface Presenter extends RxBasePresenter {
        void readPLC();
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist assist);
    }
}
