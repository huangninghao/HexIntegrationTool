package com.hx.set.contact.indonesia.HXE320;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

public interface DisplayIntervalContact {
    interface Presenter extends RxBasePresenter {
        void  read();
        void  set( String val);
    }

    interface View extends HexBaseView {
     void show( String interval);
    }
}
