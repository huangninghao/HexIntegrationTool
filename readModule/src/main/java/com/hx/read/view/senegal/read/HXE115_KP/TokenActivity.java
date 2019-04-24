package com.hx.read.view.senegal.read.HXE115_KP;

import android.os.Bundle;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.senegal.HXE115_KP.TokenContact;

import cn.hexing.model.TranXADRAssist;


public class TokenActivity extends RxMvpBaseActivity<TokenContact.Presenter> implements TokenContact.View {

    private HeaderLayout headerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.read_function_token));
        headerLayout.showLeftBackButton();
    }

    @Override
    public TokenContact.Presenter createPresenter() {
        return null;
    }

    @Override
    public void showData(TranXADRAssist item) {

    }
}
