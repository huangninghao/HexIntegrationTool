package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.FunctionContact;
import com.hx.set.model.FunctionItem;

import java.util.ArrayList;
import java.util.List;

public class FunctionPresenter extends RxBasePresenterImpl<FunctionContact.View> implements FunctionContact.Presenter {

    private List<FunctionItem> list = new ArrayList<>();

    public FunctionPresenter(FunctionContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {
        list.add(new FunctionItem(R.mipmap.img_rate,R.string.set_function_rate));
        list.add(new FunctionItem(R.mipmap.img_settlement,R.string.set_function_settlement));
        list.add(new FunctionItem(R.mipmap.img_display,R.string.set_function_display));
        list.add(new FunctionItem(R.mipmap.img_display,R.string.set_billingTime));
        getView().showData(list);
    }
}
