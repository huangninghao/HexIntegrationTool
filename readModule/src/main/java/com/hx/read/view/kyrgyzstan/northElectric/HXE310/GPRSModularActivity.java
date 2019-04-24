package com.hx.read.view.kyrgyzstan.northElectric.HXE310;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE310.GPRSModularContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXE310.GPRSModularPresenter;

import cn.hexing.model.TranXADRAssist;

public class GPRSModularActivity extends RxMvpBaseActivity<GPRSModularContact.Presenter> implements GPRSModularContact.View {
    private HeaderLayout headerLayout;
    private TextView tvTime,tvTips;
    private TextView tvRead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gprs_modular);
    }

    @Override
    public GPRSModularContact.Presenter createPresenter() {
        return new GPRSModularPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvTime = findViewById(R.id.tvTime);
        tvTips = findViewById(R.id.tvTips);
        tvRead = findViewById(R.id.tvRead);
        headerLayout.showTitle(getString(R.string.gprs_modular));
        headerLayout.showLeftBackButton();
        tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readGPRSModular();
            }
        });
    }

    @Override
    public void initData() {
        tvTips.setText(R.string.gprs_modular_title);
    }

    @Override
    public void showData(TranXADRAssist assist) {
        tvTime.setText(assist.value);
    }
}
