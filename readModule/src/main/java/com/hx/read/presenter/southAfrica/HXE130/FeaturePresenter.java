package com.hx.read.presenter.southAfrica.HXE130;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;

import com.hx.read.contact.southAfrica.HXE130.FeaturesContact;
import com.hx.read.view.southAfrica.read.HXE130.FreezeActivity;
import com.hx.read.view.southAfrica.read.HXE130.FreezeMonthActivity;
import com.hx.read.view.southAfrica.read.HXE130.InstantaneousActivity;
import com.hx.read.view.southAfrica.read.HXE130.PrepaidPacketActivity;
import com.hx.read.view.southAfrica.read.HXE130.RelayActivity;
import com.hx.read.view.southAfrica.read.HXE130.TimeSetActivity;

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
        item.setCls(FreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_monthly);
        item.setSort(3);
        item.imgResId = R.mipmap.img_month_freeze;
        item.setCls(FreezeMonthActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_time_set);
        item.setSort(4);
        item.imgResId = R.mipmap.img_time_set;
        item.setCls(TimeSetActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_relay_status);
        item.setSort(5);
        item.imgResId = R.mipmap.img_relay;
        item.setCls(RelayActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_prepayment);
        item.setSort(6);
        item.imgResId = R.mipmap.img_prepaid_packet;
        item.setCls(PrepaidPacketActivity.class);
        dataList.add(item);

        getView().showData(dataList);
    }
}
