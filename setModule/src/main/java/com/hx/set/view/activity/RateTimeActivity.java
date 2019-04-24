package com.hx.set.view.activity;

import android.os.Bundle;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.R;
import com.hx.set.contact.RateTimeContact;
import com.hx.set.presenter.RateTimePresenter;

public class RateTimeActivity extends RxMvpBaseActivity<RateTimeContact.Presenter> implements RateTimeContact.View {

    private HeaderLayout headerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_time);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_rote_effective));
        headerLayout.showLeftBackButton();
    }

    @Override
    public RateTimeContact.Presenter createPresenter() {
        return new RateTimePresenter(this);
    }
}
