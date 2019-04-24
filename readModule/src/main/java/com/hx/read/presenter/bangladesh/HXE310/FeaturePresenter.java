package com.hx.read.presenter.bangladesh.HXE310;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;

import com.hx.read.contact.bangladesh.HXE310.FeaturesContact;
import com.hx.read.view.bangladesh.read.HXE310_KP.DayFreezeActivity;

import com.hx.read.view.bangladesh.read.HXE310_KP.DemandActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.DisplayRotateActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.EnergyActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.EventRecordActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.InstantaneousActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.MonthFreezeActivity;

import com.hx.read.view.bangladesh.read.HXE310_KP.PrepaymentActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.RelayActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.TimeSetActivity;
import com.hx.read.view.bangladesh.read.HXE310_KP.TokenActivity;

import java.util.ArrayList;
import java.util.List;

public class FeaturePresenter extends RxBasePresenterImpl<FeaturesContact.View> implements FeaturesContact.Presenter {
    public FeaturePresenter(FeaturesContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {
        List<FeaturesMenuBean> dataList = new ArrayList<>();
        FeaturesMenuBean item = new FeaturesMenuBean();
        item.imgResId = R.mipmap.img_instanteous_amount;
        item.setResourceIdName(R.string.read_function_instantaneous_amount);
        item.setSort(1);
        item.setCls(InstantaneousActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_daily);
        item.setSort(2);
        item.imgResId = R.mipmap.img_day_freeze;
        item.setCls(DayFreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_monthly);
        item.setSort(3);
        item.imgResId = R.mipmap.img_month_freeze;
        item.setCls(MonthFreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_time_set);
        item.setSort(4);
        item.imgResId = R.mipmap.img_time_set;
        item.setCls(TimeSetActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_event_record);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_event_record;
        item.setCls(EventRecordActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_demand);
        item.setSort(6);
        item.imgResId = R.mipmap.img_func_demand;
        item.setCls(DemandActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_energy);
        item.setSort(7);
        item.imgResId = R.mipmap.img_func_energy;
        item.setCls(EnergyActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_prepayment);
        item.setSort(8);
        item.imgResId = R.mipmap.img_prepaid_packet;
        item.setCls(PrepaymentActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_token);
        item.setSort(9);
        item.imgResId = R.mipmap.img_func_token;
        item.setCls(TokenActivity.class);
        dataList.add(item);
        getView().showData(dataList);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_relay_status);
        item.setSort(10);
        item.imgResId = R.mipmap.img_relay;
        item.setCls(RelayActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_display_rotate);
        item.setSort(11);
        item.imgResId = R.mipmap.img_func_display;
        item.setCls(DisplayRotateActivity.class);
        dataList.add(item);

        getView().showData(dataList);
    }
}
