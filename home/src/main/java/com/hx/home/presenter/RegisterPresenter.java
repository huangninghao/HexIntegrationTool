package com.hx.home.presenter;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.home.Constant;
import com.hx.home.R;
import com.hx.home.contact.RegisterContract;

public class RegisterPresenter extends RxBasePresenterImpl<RegisterContract.View> implements RegisterContract.Presenter {
    public RegisterPresenter(RegisterContract.View view) {
        super(view);
    }

    @Override
    public void register(String username, String pwd) {
        if (pwd.length() == 0 && getView() != null) {
            getView().showToast(R.string.home_please_enter_code);
            return;
        }
        if (!username.equals(StringCache.get(Constant.PRE_KEY_HHU_ID)) ||
                !pwd.toUpperCase().equals(StringCache.get(Constant.PRE_KEY_HHU_PASSWORD))) {
            if (getView() != null) {
                getView().showToast(R.string.home_please_enter_code_error);
            }
            return;
        }
        StringCache.put(Constant.HHU_ID_REGISTER, "TRUE");
        if (getView() != null) {
            getView().showResult(true);
        }
    }
}
