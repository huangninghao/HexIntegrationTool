package com.hx.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hx.base.model.UserInfoEntity;
import com.hx.home.contact.LoginContract;
import com.hx.home.presenter.LoginPresenter;

/**
 * @author caibinglong
 * date 2018/11/7.
 * desc desc
 */

public class LoginActivity extends RxMvpBaseActivity<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    private TextView tvLogin, tvRegister;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_login);
    }

    @Override
    public void initView() {
        super.initView();
        tvLogin = findViewById(R.id.tvLogin);
        tvRegister = findViewById(R.id.tvRegister);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    @Override
    public void initListener() {
        super.initListener();
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tvLogin) {
            mvpPresenter.login(etUsername.getText().toString(), etPassword.getText().toString());
        } else if (view.getId() == R.id.tvRegister) {
            toActivity(RegisterActivity.class);
        }
    }

    @Override
    public void showUser(UserInfoEntity user) {
        if (user != null) {
            toActivityWithFinish(HomeActivity.class, null);
        }
    }
}
