package com.hx.read.contact.bangladesh.HXE110;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import cn.hexing.model.TranXADRAssist;

public interface SetContact {
    interface Presenter extends RxBasePresenter {
        void setOverdraft(final String data);
        void setAlarm(final String data);
        void readAlarm();
        void readOverdraft();
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
