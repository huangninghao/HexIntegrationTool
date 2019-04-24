package com.hx.set.view.activity;

import android.os.Bundle;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.R;
import com.hx.set.contact.DisplayIntervalContact;
import com.hx.set.presenter.DisplayIntervalPresenter;

public class DisplayIntervalActivity extends RxMvpBaseActivity<DisplayIntervalContact.Presenter> implements DisplayIntervalContact.View {

    private HeaderLayout headerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_interval);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_display_interval));
        headerLayout.showLeftBackButton();
    }

    @Override
    public DisplayIntervalContact.Presenter createPresenter() {
        return new DisplayIntervalPresenter(this);
    }
}
