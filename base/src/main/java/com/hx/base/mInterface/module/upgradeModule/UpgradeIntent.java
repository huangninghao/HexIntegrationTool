package com.hx.base.mInterface.module.upgradeModule;

import com.hx.base.mInterface.config.HexBundle;
import com.hx.base.mInterface.provider.IUpgradeProvider;
import com.hx.base.mInterface.router.HexRouter;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.model.UserInfoEntity;

/**
 * description：
 * update by:
 * update day:
 */
public class UpgradeIntent {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IUpgradeProvider.UPGRADE_MODULE_MAIN_SERVICE);
    }

    public static void launchUpgrade(UserInfoEntity userInfoEntity) {
        //跳转升级app
        HexBundle bundle = new HexBundle();
        bundle.put(UserInfoEntity.class.getName(), userInfoEntity);
        HexRouter.newInstance(IUpgradeProvider.UPGRADE_ACT_HOME)
                .withBundle(bundle)
                .navigation();
    }
}
