package com.hx.base.mInterface.module.readModule;

import com.hx.base.mInterface.config.HexBundle;
import com.hx.base.mInterface.provider.IReadProvider;
import com.hx.base.mInterface.router.HexRouter;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.model.UserInfoEntity;

/**
 * description：
 * update by:
 * update day:
 */
public class ReadIntent {
    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IReadProvider.READ_MODULE_MAIN_SERVICE);
    }

    public static void launchRead(UserInfoEntity userInfoEntity) {
        //跳转抄表app
        HexBundle bundle = new HexBundle();
        bundle.put(UserInfoEntity.class.getName(), userInfoEntity);
        HexRouter.newInstance(IReadProvider.READ_ACT_HOME)
                .withBundle(bundle)
                .navigation();
    }
}
