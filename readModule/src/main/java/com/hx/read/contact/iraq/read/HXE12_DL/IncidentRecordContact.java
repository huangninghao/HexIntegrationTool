package com.hx.read.contact.iraq.read.HXE12_DL;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface IncidentRecordContact {
    interface Presenter extends RxBasePresenter {
        List<TranXADRAssist> getShowList();
        void read();
        boolean saveData(List<TranXADRAssist> obisListData, String type);
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
