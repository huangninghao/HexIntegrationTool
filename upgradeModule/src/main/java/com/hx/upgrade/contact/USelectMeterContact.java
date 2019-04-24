package com.hx.upgrade.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.DeviceBean;
import com.hx.base.model.UserInfoEntity;

import java.util.List;

public interface USelectMeterContact {

    interface Presenter extends RxBasePresenter {
        void getAllMeter(UserInfoEntity userInfoEntity);
    }

    interface View extends HexBaseView {
        void showData(List<DeviceBean> deviceBeans);
    }
}
