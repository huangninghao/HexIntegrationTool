package com.hx.set.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.ParameterSetContact;
import com.hx.set.model.ParameterItem;

import java.util.ArrayList;
import java.util.List;

public class ParameterSetPresenter extends RxBasePresenterImpl<ParameterSetContact.View> implements ParameterSetContact.Presenter {

    private List<ParameterItem> list = new ArrayList<>();

    public ParameterSetPresenter(ParameterSetContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",true));
        getView().showData(list);
    }
}
