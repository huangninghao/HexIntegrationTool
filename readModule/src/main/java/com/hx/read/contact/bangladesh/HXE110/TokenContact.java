package com.hx.read.contact.bangladesh.HXE110;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface TokenContact {
    interface Presenter extends RxBasePresenter {
        void writeToken(String token);
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
