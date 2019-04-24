package com.hx.read.presenter.ethiopia.HXE12_DL;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.ethiopia.HXE12_DL.FunctionContact;
import com.hx.read.view.ethiopia.read.HXE12_DL.FreezeActivity;
import com.hx.read.view.ethiopia.read.HXE12_DL.InstantaneousActivity;
import com.hx.read.view.ethiopia.read.HXE12_DL.TimeSetActivity;

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
        item.setResourceIdName(R.string.read_function_monthly);
        item.setSort(2);
        item.imgResId = R.mipmap.img_month_freeze;
        item.setCls(FreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_time_set);
        item.setSort(4);
        item.imgResId = R.mipmap.img_time_set;
        item.setCls(TimeSetActivity.class);
        dataList.add(item);

        getView().showData(dataList);
    }
}
