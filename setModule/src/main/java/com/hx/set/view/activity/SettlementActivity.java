package com.hx.set.view.activity;

import android.os.Bundle;

import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.R;
import com.hx.set.contact.SettlementContact;
import com.hx.set.presenter.SettlementPresenter;

public class SettlementActivity extends RxMvpBaseActivity<SettlementContact.Presenter> implements SettlementContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_function_settlement));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public SettlementContact.Presenter createPresenter() {
        return new SettlementPresenter(this);
    }
}
