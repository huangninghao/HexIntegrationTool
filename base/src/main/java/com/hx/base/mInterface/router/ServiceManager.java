package com.hx.base.mInterface.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hx.base.mInterface.provider.IHomeProvider;
import com.hx.base.mInterface.provider.ISetProvider;
import com.hx.base.mInterface.provider.IUpgradeProvider;


/**
 * description：注意，这不是一个完全的单例模式，不能私有化构造器以及属性
 * update by:
 * update day:
 */
public class ServiceManager {
    //服务注入看自己的具体实现
    //自动注入
    @Autowired
    IHomeProvider homeProvider;
    //可以不使用@Autowired，手动发现服务
    private ISetProvider module1Provider;
    private IUpgradeProvider module2Provider;

    public ServiceManager() {
        ARouter.getInstance().inject(this);
    }

    private static final class ServiceManagerHolder {
        private static final ServiceManager instance = new ServiceManager();
    }

    public static ServiceManager getInstance() {
        return ServiceManagerHolder.instance;
    }

    /**
     * @return IHomeProvider
     */
    public IHomeProvider getHomeProvider() {
        return homeProvider;
    }


    public ISetProvider getSetProvider() {
        return module1Provider != null ? module1Provider : (module1Provider = ((ISetProvider) HexRouter.newInstance(ISetProvider.SET_MODULE_MAIN_SERVICE).navigation()));
    }

    public IUpgradeProvider getUpgradeProvider() {
        return module2Provider != null ? module2Provider : (module2Provider = ((IUpgradeProvider) HexRouter.newInstance(IUpgradeProvider.UPGRADE_MODULE_MAIN_SERVICE).navigation()));
    }

}
