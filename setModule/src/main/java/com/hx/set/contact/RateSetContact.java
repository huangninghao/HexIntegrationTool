package com.hx.set.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.set.model.DTItem;
import com.hx.set.model.TimeItem;

import java.util.List;

public interface RateSetContact {
    interface Presenter extends RxBasePresenter {
        void getShowDTList();
        void getShowTimeList();
    }

    interface View extends HexBaseView {
        void showDTData(List<DTItem> list);
        void showTimeData(List<TimeItem> list);
    }
}
