package com.hx.read.contact.benin.HXE310_KP;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface FreezeMonthContact {
    interface Presenter extends RxBasePresenter {
        void getShowList(TranXADRAssist assist);
        boolean exportFreezeData(List<TranXADRAssist> obisListData);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);
    }
}
