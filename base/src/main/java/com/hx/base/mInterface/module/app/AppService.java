package com.hx.base.mInterface.module.app;


import com.hx.base.mInterface.provider.IAppProvider;
import com.hx.base.mInterface.router.ModuleManager;

/**
 * description：
 * update by:
 * update day:
 */
public class AppService {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IAppProvider.APP_MAIN_SERVICE);
    }

}
