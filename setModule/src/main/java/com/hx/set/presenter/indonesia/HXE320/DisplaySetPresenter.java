package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.DisplaySetContact;
import com.hx.set.model.FunctionItem;

import java.util.ArrayList;
import java.util.List;

public class DisplaySetPresenter extends RxBasePresenterImpl<DisplaySetContact.View> implements DisplaySetContact.Presenter {

    private List<FunctionItem> list = new ArrayList<>();
    public DisplaySetPresenter(DisplaySetContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {
        list.add(new FunctionItem(R.string.set_display_rotate));
        list.add(new FunctionItem(R.string.set_display_button_1));
        list.add(new FunctionItem(R.string.set_display_button_2));
        list.add(new FunctionItem(R.string.set_display_interval));
        getView().showData(list);
    }
}
