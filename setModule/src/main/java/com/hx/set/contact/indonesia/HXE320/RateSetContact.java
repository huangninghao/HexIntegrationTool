package com.hx.set.contact.indonesia.HXE320;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.set.model.DTItem;
import com.hx.set.model.TimeItem;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface RateSetContact {
    interface Presenter extends RxBasePresenter {
        void getShowDTList();
        void getShowTimeList(int index);
        void add(TimeItem it);
        void delete(TimeItem it);
       void getWriteData();
    }

    interface View extends HexBaseView {
        void showDTData(List<DTItem> list);
        void showTimeData(List<TimeItem> list);
        void showWriteData(List<TranXADRAssist> data);
    }
}
