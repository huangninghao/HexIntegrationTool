package com.hx.home.presenter;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.UserInfoEntity;
import com.hx.home.AboutActivity;
import com.hx.home.Constant;
import com.hx.home.HomeApplication;
import com.hx.home.ManualActivity;
import com.hx.home.R;
import com.hx.home.contact.HomeContract;
import com.hx.base.model.HomeMenu;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends RxBasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {
    public HomePresenter(HomeContract.View view) {
        super(view);
    }

    /**
     * 获取左侧显示菜单
     */
    @Override
    public void getLeftMenu() {
        List<HomeMenu> menus = new ArrayList<>();
        HomeMenu menu = new HomeMenu();
        menu.name = ((UserInfoEntity) StringCache.getJavaBean(Constant.USER_INFO)).getEn_name();
        menu.resourceId = R.mipmap.icon_country;
        menus.add(menu);

        menu = new HomeMenu();
        menu.name = HomeApplication.getInstance().getString(R.string.home_menu_about);
        menu.resourceId = R.mipmap.icon_about;
        menu.cls = AboutActivity.class;
        menus.add(menu);

        //Instruction manual
        menu = new HomeMenu();
        menu.name = HomeApplication.getInstance().getString(R.string.home_instruction_manual);
        menu.resourceId = R.mipmap.icon_instruction_manual;
        menu.cls = ManualActivity.class;
        menus.add(menu);

        menu = new HomeMenu();
        menu.name = StringCache.get(Constant.PRE_KEY_HHU_ID);
        menu.resourceId = R.mipmap.icon_serial;
        menus.add(menu);


        getView().showLeftMenu(menus);
    }
}
