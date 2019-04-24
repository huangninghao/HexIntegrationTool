package com.hx.read.view.kyrgyzstan.northElectric.HXE110;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.PLCContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXE110.PLCPresenter;

import cn.hexing.model.TranXADRAssist;

public class PLCActivity extends RxMvpBaseActivity<PLCContact.Presenter> implements PLCContact.View{

    private HeaderLayout headerLayout;
    private TextView tvTime,tvTips;
    private TextView tvRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plc);
    }

    @Override
    public PLCContact.Presenter createPresenter() {
        return new PLCPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvTime = findViewById(R.id.tvTime);
        tvRead = findViewById(R.id.tvRead);
        tvTips = findViewById(R.id.tvTips);
        headerLayout.showTitle(getString(R.string.plc_title));
        headerLayout.showLeftBackButton();
        tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readPLC();
            }
        });
    }

    @Override
    public void initData() {
        tvTips.setText(getString(R.string.plc_tips));
    }

    @Override
    public void showData(TranXADRAssist assist) {
        tvTime.setText(assist.value);
    }
}
