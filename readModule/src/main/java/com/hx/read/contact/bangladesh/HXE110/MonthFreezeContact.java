package com.hx.read.contact.bangladesh.HXE110;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface MonthFreezeContact {
    interface Presenter extends RxBasePresenter {
        void getMonthFreeze(final String startTime, final String endTime, final int type);
        boolean saveData(List<TranXADRAssist> obisListData, String type);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> object);
    }
}
