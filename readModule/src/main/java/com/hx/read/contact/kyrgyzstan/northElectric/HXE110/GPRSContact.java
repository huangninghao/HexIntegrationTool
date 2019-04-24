package com.hx.read.contact.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface GPRSContact {
    interface Presenter extends RxBasePresenter {
        void readGPRS();
        void setGPRS(String ip, String port,String apn);
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist assist);
    }
}
