package com.hx.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.cache.StringCache;
import com.hx.home.contact.RegisterContract;
import com.hx.home.presenter.RegisterPresenter;

public class RegisterActivity extends RxMvpBaseActivity<RegisterContract.Presenter> implements RegisterContract.View {
    private LinearLayout layoutBack;
    private TextView tvHHUId;
    private TextView tvRegister;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initView() {
        super.initView();
        layoutBack = findViewById(R.id.layoutBack);
        tvHHUId = findViewById(R.id.tvHHUId);
        tvRegister = findViewById(R.id.tvRegister);
        etPassword = findViewById(R.id.etPassword);
        tvHHUId.setText(StringCache.get(Constant.PRE_KEY_HHU_ID));
    }

    @Override
    public void initListener() {
        super.initListener();
        layoutBack.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public RegisterContract.Presenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.layoutBack) {
            finish();
        } else if (view.getId() == R.id.tvRegister) {
            mvpPresenter.register(tvHHUId.getText().toString(), etPassword.getText().toString());
        }
    }

    @Override
    public void showResult(boolean bool) {
        if (bool) {
            finish();
        }
    }
}
