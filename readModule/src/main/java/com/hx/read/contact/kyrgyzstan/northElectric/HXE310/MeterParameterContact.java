package com.hx.read.contact.kyrgyzstan.northElectric.HXE310;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface MeterParameterContact {
    interface Presenter extends RxBasePresenter {
        List<TranXADRAssist> getShowList();
        void read();
        boolean export(List<TranXADRAssist> assists, String type);
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
