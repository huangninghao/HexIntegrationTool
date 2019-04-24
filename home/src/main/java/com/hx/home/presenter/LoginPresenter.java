package com.hx.home.presenter;

import android.text.TextUtils;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.CollectionUtil;
import com.hexing.libhexbase.tools.GJsonUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.base.model.UserInfoEntity;
import com.hx.home.Constant;
import com.hx.home.HomeApplication;
import com.hx.home.R;
import com.hx.home.contact.LoginContract;

import java.util.List;


/**
 * @author by HEC271
 * on 2018/3/13.
 */

public class LoginPresenter extends RxBasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {
    private UserInfoEntity userInfoEntity = null;

    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param pwd      用户密码
     */
    public void login(String username, String pwd) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            getView().showToast(R.string.username_password_empty);
            return;
        }
        userInfoEntity = null;
        String json = FileUtil.getJson(HomeApplication.getInstance().getContext(), "users.json");
        List<UserInfoEntity> users = GJsonUtil.fromJsonList(json, UserInfoEntity.class);
        if (!CollectionUtil.isEmpty(users)) {
            for (UserInfoEntity item : users) {
                if (item.getUsername().equals(username) && item.getPassword().equals(pwd)) {
                    userInfoEntity = item;
                    StringCache.putJavaBean(Constant.USER_INFO, userInfoEntity);
                    break;
                }
            }
        }
        HexThreadManager.runTaskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (userInfoEntity == null && getView() != null) {
                    getView().showToast(R.string.login_error);
                } else if (userInfoEntity != null && getView() != null) {
                    getView().showUser(userInfoEntity);
                }
            }
        });
    }
}
