package com.hx.base.mInterface.provider;

import android.app.Activity;

/**
 * description：
 * update by:
 * update day:
 */
public interface IHomeProvider extends IBaseProvider {
    //Service
    String HOME_MAIN_SERVICE = "/home/main/service";
    //开屏
    String HOME_ACT_SPLASH = "/home/act/splash";
    //home主页
    String HOME_ACT_HOME = "/home/act/home";
    String HOME_TABTYPE = "home_tab_type";

    void selectedTab(Activity activity, int position);

}
