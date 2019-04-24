package com.hx.read.contact.southAfrica.HXE330;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface InstantaneousContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
        void read();
        boolean exportInstantaneousData(TranXADRAssist data);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);

        void showData(TranXADRAssist item);
    }
}
