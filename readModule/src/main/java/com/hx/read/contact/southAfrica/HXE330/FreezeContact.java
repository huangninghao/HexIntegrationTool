package com.hx.read.contact.southAfrica.HXE330;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface FreezeContact {
    interface Presenter extends RxBasePresenter {
        void getShowList(TranXADRAssist assist);
        boolean exportFreezeData(List<TranXADRAssist> obisListData);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);
    }
}
