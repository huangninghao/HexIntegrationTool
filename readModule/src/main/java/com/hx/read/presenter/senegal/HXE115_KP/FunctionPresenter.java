package com.hx.read.presenter.senegal.HXE115_KP;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.senegal.HXE115_KP.FunctionContact;
import com.hx.read.view.senegal.read.HXE115_KP.FreezeActivity;
import com.hx.read.view.senegal.read.HXE115_KP.FreezeMonthActivity;
import com.hx.read.view.senegal.read.HXE115_KP.InstantaneousActivity;
import com.hx.read.view.senegal.read.HXE115_KP.PrepaidPacketActivity;
import com.hx.read.view.senegal.read.HXE115_KP.RelayActivity;
import com.hx.read.view.senegal.read.HXE115_KP.TimeSetActivity;
import com.hx.read.view.senegal.read.HXE115_KP.TokenActivity;

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
