package com.hx.read.contact.ethiopia.HXE12_DL;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface FreezeContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
        String getLastMonth();
        boolean saveData(List<TranXADRAssist> obisListData, String type);
    }

    interface View extends HexBaseView {
        void showData(Object object);
    }
}
