package com.hx.base.mInterface.module.readModule;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.hx.base.mInterface.provider.IReadProvider;
import com.hx.base.mInterface.provider.IUpgradeProvider;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.mInterface.router.ServiceManager;


/**
 * description：
 * update by:
 * update day:
 */
public class ReadService {
    private static boolean hasModule2() {
        return ModuleManager.getInstance().hasModule(IReadProvider.READ_MODULE_MAIN_SERVICE);
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
