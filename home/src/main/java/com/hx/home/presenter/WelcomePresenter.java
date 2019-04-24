package com.hx.home.presenter;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.home.Constant;
import com.hx.home.contact.WelcomeContract;

/**
 * @author by HEC271
 * on 2018/6/1.
 */

public class WelcomePresenter extends RxBasePresenterImpl<WelcomeContract.View> implements WelcomeContract.Presenter {
    public WelcomePresenter(WelcomeContract.View view) {
        super(view);
    }

    @Override
    public void verifyLogin() {
        boolean isLogin = false;
        if (StringCache.getJavaBean(Constant.USER_INFO) != null) {
            isLogin = true;
        }
        getView().verifyResult(isLogin);
    }
}
