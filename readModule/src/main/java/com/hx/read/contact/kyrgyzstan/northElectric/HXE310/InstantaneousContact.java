package com.hx.read.contact.kyrgyzstan.northElectric.HXE310;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface InstantaneousContact {
    interface Presenter extends RxBasePresenter {
       List<TranXADRAssist> getShowList();
        void read();
        boolean exportInstantaneousData(TranXADRAssist data);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);

        void showData(TranXADRAssist item);
    }
}
