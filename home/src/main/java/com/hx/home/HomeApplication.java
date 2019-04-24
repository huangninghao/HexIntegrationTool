package com.hx.home;


import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.tools.AESEncrypt;
import com.hexing.libhexbase.tools.CBLSystemUtil;
import com.hx.base.BaseApplication;
import com.hx.base.mInterface.config.ModuleOptions;
import com.hx.base.mInterface.provider.IHomeProvider;
import com.hx.base.mInterface.provider.IReadProvider;
import com.hx.base.mInterface.provider.ISetProvider;
import com.hx.base.mInterface.provider.IUpgradeProvider;
import com.hx.base.mInterface.router.ModuleManager;


/**
 * @author caibinglong
 * date 2018/11/5.
 * desc desc
 */

public class HomeApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    private void initARouter() {
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
        ModuleOptions.ModuleBuilder builder = new ModuleOptions.ModuleBuilder(this)
                .addModule(IHomeProvider.HOME_MAIN_SERVICE, IHomeProvider.HOME_MAIN_SERVICE)
                .addModule(ISetProvider.SET_MODULE_MAIN_SERVICE, ISetProvider.SET_MODULE_MAIN_SERVICE)
                .addModule(IUpgradeProvider.UPGRADE_MODULE_MAIN_SERVICE, IUpgradeProvider.UPGRADE_MODULE_MAIN_SERVICE)
                .addModule(IReadProvider.READ_MODULE_MAIN_SERVICE, IReadProvider.READ_MODULE_MAIN_SERVICE);
        ModuleManager.getInstance().init(builder.build());
        readHHUId();
    }

    /**
     * 读取hhu id
     */
    public void readHHUId() {
        try {
            if (TextUtils.isEmpty(StringCache.get(Constant.PRE_KEY_HHU_ID))) {
                String IMEI = CBLSystemUtil.getSerialNumber();
                if (TextUtils.isEmpty(IMEI)) {
                    IMEI = CBLSystemUtil.getIMEI(getInstance());
                }
                if (!TextUtils.isEmpty(IMEI)) {
                    StringCache.put(Constant.PRE_KEY_HHU_ID, IMEI);
                    StringCache.put(Constant.PRE_KEY_HHU_PASSWORD, AESEncrypt.encrypt(IMEI).substring(0, 6).toUpperCase());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
