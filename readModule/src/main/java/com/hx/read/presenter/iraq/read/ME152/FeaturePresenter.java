package com.hx.read.presenter.iraq.read.ME152;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.iraq.read.HXE12_DL.FeaturesContact;
import com.hx.read.view.iraq.read.ME152.EnergyActivity;
import com.hx.read.view.iraq.read.ME152.InstantaneousActivity;

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
        item.imgResId = R.mipmap.img_func_energy;
        item.setResourceIdName(R.string.read_function_energy);
        item.setSort(1);
        item.setCls(EnergyActivity.class);
        dataList.add(item);

        getView().showData(dataList);
    }
}
