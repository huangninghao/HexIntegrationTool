package com.hx.base.mInterface.router;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hx.base.mInterface.config.HexBundle;


/**
 * description：
 * update by:
 * update day:
 */
public class HexRouter {

    private Postcard postcard;

    private HexRouter(Postcard postcard) {
        this.postcard = postcard;
    }

    public static HexRouter newInstance(String path) {
        return new HexRouter(ARouter.getInstance().build(path));
    }

    private boolean checkPostcard() {
        if (postcard == null)
            throw new IllegalArgumentException("HexRouter 的 postcard 为null");
        return true;
    }

    public HexRouter withBundle(HexBundle bundle) {
        if (bundle == null) return this;
        checkPostcard();
        postcard.with(bundle.build());
        return this;
    }

    public HexRouter addFlag(int flag) {
        checkPostcard();
        postcard.withFlags(flag);
        return this;
    }

    public Object navigation() {
        return navigation(null);
    }

    public Object navigation(Context context) {
        return navigation(context, null);
    }

    public void navigation(Activity activity, int requestCode) {
        navigation(activity, requestCode, null);
    }

    public Object navigation(Context context, NavigationCallback callback) {
        checkPostcard();
        return postcard.navigation(context, callback);
    }

    public void navigation(Activity activity, int requestCode, NavigationCallback callback) {
        postcard.navigation(activity, requestCode, callback);
    }

}
