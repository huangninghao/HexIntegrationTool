package com.hx.home;

import android.os.Bundle;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hx.home.contact.WelcomeContract;
import com.hx.home.presenter.WelcomePresenter;

public class WelcomeActivity extends RxMvpBaseActivity<WelcomeContract.Presenter> implements WelcomeContract.View {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mvpPresenter.verifyLogin();
    }

    @Override
    public WelcomeContract.Presenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    public void verifyResult(final boolean bool) {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bool) {
                    toActivityWithFinish(HomeActivity.class, null);
                } else {
                    toActivityWithFinish(LoginActivity.class, null);
                }
            }
        }, 2000);
    }
}
