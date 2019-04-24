package com.hx.read.contact.kyrgyzstan.northElectric.HXE310;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface MonthFreezeContact {
    interface Presenter extends RxBasePresenter {
        void getShowList(String startTime, String endTime);
        boolean saveData(List<TranXADRAssist> obisListData, String type);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);
    }
}
