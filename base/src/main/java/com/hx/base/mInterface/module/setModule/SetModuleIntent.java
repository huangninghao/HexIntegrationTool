package com.hx.base.mInterface.module.setModule;

import com.hx.base.mInterface.config.HexBundle;
import com.hx.base.mInterface.provider.ISetProvider;
import com.hx.base.mInterface.router.HexRouter;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.model.UserInfoEntity;

/**
 * description：
 * update by:
 * update day:
 */
public class SetModuleIntent {

    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(ISetProvider.SET_MODULE_MAIN_SERVICE);
    }

    public static void launchSet(UserInfoEntity userInfoEntity) {
        //跳转设置app
        HexBundle bundle = new HexBundle();
        bundle.put(UserInfoEntity.class.getName(), userInfoEntity);
        HexRouter.newInstance(ISetProvider.SET_ACT_HOME)
                .withBundle(bundle)
                .navigation();
    }
}
