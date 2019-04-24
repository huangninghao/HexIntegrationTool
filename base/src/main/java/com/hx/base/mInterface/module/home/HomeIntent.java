package com.hx.base.mInterface.module.home;

import com.hx.base.mInterface.config.HexBundle;
import com.hx.base.mInterface.provider.IHomeProvider;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.mInterface.router.HexRouter;

/**
 * description：
 * update by:
 * update day:
 */
public class HomeIntent {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }
    public static void launchHome(int tabType) {
        //HomeActivity
        HexBundle bundle = new HexBundle();
        bundle.put(IHomeProvider.HOME_TABTYPE, tabType);
        HexRouter.newInstance(IHomeProvider.HOME_ACT_HOME)
                .withBundle(bundle)
                .navigation();
    }


}
