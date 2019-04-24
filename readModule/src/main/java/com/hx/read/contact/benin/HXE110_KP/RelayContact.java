package com.hx.read.contact.benin.HXE110_KP;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface RelayContact {
    interface Presenter extends RxBasePresenter {
        void openRelay();
        void closeRelay();
        void reload();
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
