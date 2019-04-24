package com.hx.upgrade.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.base.mInterface.module.home.HomeService;
import com.hx.base.model.DeviceBean;
import com.hx.base.model.UserInfoEntity;
import com.hx.upgrade.contact.USelectMeterContact;

import java.util.ArrayList;
import java.util.List;

public class USelectMeterPresenter extends RxBasePresenterImpl<USelectMeterContact.View> implements USelectMeterContact.Presenter {
    public USelectMeterPresenter(USelectMeterContact.View view) {
        super(view);
    }

    private List<DeviceBean> deviceBeanList = new ArrayList<>();
    @Override
    public void getAllMeter(final UserInfoEntity userInfoEntity) {
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                deviceBeanList = HomeService.getDeviceList(userInfoEntity);
                HexThreadManager.runTaskOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getView() != null){
                            getView().showData(deviceBeanList);
                        }
                    }
                });
            }
        });
    }
}
