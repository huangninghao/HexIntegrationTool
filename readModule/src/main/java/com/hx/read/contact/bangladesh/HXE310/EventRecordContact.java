package com.hx.read.contact.bangladesh.HXE310;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface EventRecordContact {
    interface Presenter extends RxBasePresenter {
        void getEventRecord(String startTime, String endTime, int select);
        boolean saveData(List<TranXADRAssist> obisListData, String type);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> object);
    }
}
