package com.hx.read.contact.ethiopia.HXE12_DL;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface InstantaneousContact {
    interface Presenter extends RxBasePresenter {
        List<TranXADRAssist> getShowList();
        boolean saveData(List<TranXADRAssist> obisListData, String type);
        void read();
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);

        void showData(TranXADRAssist item);
    }
}
