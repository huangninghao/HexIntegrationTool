package com.hx.read.contact.kyrgyzstan.northElectric.HXF300;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface GPRSModularContact {
    interface Presenter extends RxBasePresenter {
        void readGPRSModular();
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist assist);
    }
}
