package com.hx.read.contact.southAfrica.HXE130;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

public interface PrepaidPacketContact {
    interface Presenter extends RxBasePresenter {
        void getShowList();
        void read();
        boolean exportPrepaidPacketData(TranXADRAssist data);
    }

    interface View extends HexBaseView {
        void showData(List<TranXADRAssist> list);

        void showData(TranXADRAssist item);
    }
}
