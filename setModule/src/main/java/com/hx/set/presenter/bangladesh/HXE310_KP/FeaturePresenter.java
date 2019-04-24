package com.hx.set.presenter.bangladesh.HXE310_KP;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.set.R;
import com.hx.set.contact.bangladesh.HXE310_KP.FeaturesContact;
import com.hx.set.view.bangladesh.set.HXE310_KP.GPRSActivity;
import com.hx.set.view.bangladesh.set.HXE310_KP.RelayActivity;
import com.hx.set.view.bangladesh.set.HXE310_KP.TimeSetActivity;
import com.hx.set.view.bangladesh.set.HXE310_KP.TokenActivity;

import java.util.ArrayList;
import java.util.List;

public class FeaturePresenter extends RxBasePresenterImpl<FeaturesContact.View> implements FeaturesContact.Presenter {
    public FeaturePresenter(FeaturesContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {
        List<FeaturesMenuBean> dataList = new ArrayList<>();
        FeaturesMenuBean  item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.set_function_time_set);
        item.setSort(4);
        item.imgResId = R.mipmap.set_icon_time_set;
        item.setCls(TimeSetActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.set_function_token);
        item.setSort(9);
        item.imgResId = R.mipmap.img_func_token;
        item.setCls(TokenActivity.class);
        dataList.add(item);
        getView().showData(dataList);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.set_function_relay_status);
        item.setSort(10);
        item.imgResId = R.mipmap.set_icon_relay;
        item.setCls(RelayActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.set_function_gprs);
        item.setSort(10);
        item.imgResId = R.mipmap.set_icon_gprs_param;
        item.setCls(GPRSActivity.class);
        dataList.add(item);

        getView().showData(dataList);
    }
}
