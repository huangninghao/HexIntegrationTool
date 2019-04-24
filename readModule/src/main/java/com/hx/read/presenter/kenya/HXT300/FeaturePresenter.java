package com.hx.read.presenter.kenya.HXT300;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.kenya.HXT300.FeaturesContact;
import com.hx.read.view.kenya.read.HXT300.DayFreezeActivity;
import com.hx.read.view.kenya.read.HXT300.DemandActivity;
import com.hx.read.view.kenya.read.HXT300.EnergyActivity;
import com.hx.read.view.kenya.read.HXT300.GPSActivity;
import com.hx.read.view.kenya.read.HXT300.InstantaneousActivity;
import com.hx.read.view.kenya.read.HXT300.MonthFreezeActivity;
import com.hx.read.view.kenya.read.HXT300.TimeSetActivity;

import java.util.ArrayList;
import java.util.List;

public class FeaturePresenter extends RxBasePresenterImpl<FeaturesContact.View> implements FeaturesContact.Presenter{
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
        item.setResourceIdName(R.string.read_function_gps);
        item.setSort(8);
        item.imgResId = R.mipmap.img_func_display;
        item.setCls(GPSActivity.class);
        dataList.add(item);


        getView().showData(dataList);
    }
}
