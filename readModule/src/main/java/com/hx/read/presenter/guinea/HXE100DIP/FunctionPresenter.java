package com.hx.read.presenter.guinea.HXE100DIP;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.guinea.HXE100DIP.FunctionContact;
import com.hx.read.view.guinea.read.HXE100DIP.FreezeActivity;
import com.hx.read.view.guinea.read.HXE100DIP.FreezeMonthActivity;
import com.hx.read.view.guinea.read.HXE100DIP.InstantaneousActivity;
import com.hx.read.view.guinea.read.HXE100DIP.PrepaidPacketActivity;
import com.hx.read.view.guinea.read.HXE100DIP.RelayActivity;
import com.hx.read.view.guinea.read.HXE100DIP.TimeSetActivity;

import java.util.ArrayList;
import java.util.List;

public class FunctionPresenter extends RxBasePresenterImpl<FunctionContact.View> implements FunctionContact.Presenter {

    public FunctionPresenter(FunctionContact.View view) {
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
        item.setResourceIdName(R.string.read_function_prepaid_packet);
        item.setSort(6);
        item.imgResId = R.mipmap.img_prepaid_packet;
        item.setCls(PrepaidPacketActivity.class);
        dataList.add(item);

        getView().showData(dataList);
    }
}
