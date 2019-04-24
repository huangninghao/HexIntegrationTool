package com.hx.read.contact.kenya.HXT300;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface GPSContact {
    interface Presenter extends RxBasePresenter {
        void readGPS();
        boolean saveData(List<TranXADRAssist> obisListData, String type);
    }

    interface View extends HexBaseView {
        void showData(TranXADRAssist item);
    }
}
