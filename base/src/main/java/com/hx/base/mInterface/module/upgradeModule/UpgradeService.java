package com.hx.base.mInterface.module.upgradeModule;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.hx.base.mInterface.provider.IUpgradeProvider;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.mInterface.router.ServiceManager;


/**
 * description：
 * update by:
 * update day:
 */
public class UpgradeService {
    private static boolean hasModule2() {
        return ModuleManager.getInstance().hasModule(IUpgradeProvider.UPGRADE_MODULE_MAIN_SERVICE);
    }

    public static Fragment getModuleFragment(Object... args) {
        if (!hasModule2()) return null;
        return ServiceManager.getInstance().getUpgradeProvider().newInstance(args);
    }

    public static Activity getModuleActivity(Object... args) {
        if (!hasModule2()) return null;
        return ServiceManager.getInstance().getUpgradeProvider().getInstance(args);
    }
}
