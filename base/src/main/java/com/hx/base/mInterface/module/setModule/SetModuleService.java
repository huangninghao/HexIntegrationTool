package com.hx.base.mInterface.module.setModule;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.hx.base.mInterface.provider.ISetProvider;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.mInterface.router.ServiceManager;

/**
 * description：
 * update by:
 * update day:
 */
public class SetModuleService {
    private static boolean hasSetModule() {
        return ModuleManager.getInstance().hasModule(ISetProvider.SET_MODULE_MAIN_SERVICE);
    }

    public static Fragment getModuleFragment(Object... args) {
        if (!hasSetModule()) return null;
        return ServiceManager.getInstance().getSetProvider().newInstance(args);
    }

    public static Activity getModuleActivity(Object... args) {
        if (!hasSetModule()) return null;
        return ServiceManager.getInstance().getSetProvider().getInstance(args);
    }
}
