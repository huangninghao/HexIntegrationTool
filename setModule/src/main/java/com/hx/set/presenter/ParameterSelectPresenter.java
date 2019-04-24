package com.hx.set.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.contact.ParameterSelectContact;
import com.hx.set.contact.ParameterSetContact;
import com.hx.set.model.ParameterItem;

import java.util.ArrayList;
import java.util.List;

public class ParameterSelectPresenter extends RxBasePresenterImpl<ParameterSelectContact.View> implements ParameterSelectContact.Presenter {
    public ParameterSelectPresenter(ParameterSelectContact.View view) {
        super(view);
    }
    private List<ParameterItem> list = new ArrayList<>();

    @Override
    public void getShowList() {
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
        getView().showData(list);
    }
}
