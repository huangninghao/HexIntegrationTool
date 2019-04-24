package com.hx.set.contact.indonesia.HXE320;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface RateTimeContact {
    interface Presenter extends RxBasePresenter {
        void set(List<TranXADRAssist> data, String time);
    }

    interface View extends HexBaseView {
        void ShowTime(String datetime);
    }
}
