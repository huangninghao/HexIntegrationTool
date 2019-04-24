package com.hx.home;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cbl.dialog.StyledDialog;
import com.cbl.dialog.interfaces.DialogListener;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.adapter.RecyclerViewDivider;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.BaseConstant;
import com.hx.base.mInterface.module.readModule.ReadIntent;
import com.hx.base.mInterface.module.setModule.SetModuleIntent;
import com.hx.base.mInterface.module.upgradeModule.UpgradeIntent;
import com.hx.base.mInterface.provider.IHomeProvider;
import com.hx.base.model.HomeMenu;
import com.hx.base.model.UserInfoEntity;
import com.hx.home.contact.HomeContract;
import com.hx.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


@Route(path = IHomeProvider.HOME_ACT_HOME)
public class HomeActivity extends RxMvpBaseActivity<HomeContract.Presenter> implements HomeContract.View {

    private LinearLayout layoutUpgrade, layoutRead, layoutSet;
    private HeaderLayout headerLayout;
    private DrawerLayout drawerLayout;
    private XRecyclerView leftDrawer;
    private TextView tvUsername;
    private TextView tvLogout;
    private HexRVBaseAdapter<HomeMenu> adapter;
    private List<HomeMenu> menuList = new ArrayList<>();
    private UserInfoEntity entity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public HomeContract.Presenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        layoutUpgrade = findViewById(R.id.layoutUpgrade);
        layoutSet = findViewById(R.id.layoutSet);
        layoutRead = findViewById(R.id.layoutRead);
        drawerLayout = findViewById(R.id.drawerLayout);
        leftDrawer = findViewById(R.id.leftDrawer);
        tvUsername = findViewById(R.id.tvUsername);
        tvLogout = findViewById(R.id.tvLogout);
        entity = StringCache.getJavaBean(Constant.USER_INFO);
        adapter = new HexRVBaseAdapter<HomeMenu>(this, menuList, R.layout.left_item_menu) {
            @Override
            public void convert(HexRVBaseViewHolder holder, HomeMenu item) {
                holder.setText(R.id.tvMenuName, item.name);
                holder.setImageUrl(R.id.ivMenuIcon, item.resourceId);
            }
        };

        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (menuList.get(position).cls != null) {
                    toActivity(menuList.get(position).cls);
                }
            }
        });
        leftDrawer.setLayoutManager(new LinearLayoutManager(HomeApplication.getInstance()));
        leftDrawer.setAdapter(adapter);
        leftDrawer.addItemDecoration(new RecyclerViewDivider(this, RecyclerViewDivider.HORIZONTAL));
        leftDrawer.setPullRefreshEnabled(false);
        leftDrawer.setLoadingMoreEnabled(false);

        String homeMenu = entity.getHomeMenu();
        if (!homeMenu.contains(BaseConstant.HOME_MENU_UPGRADE)) {
            layoutUpgrade.setVisibility(View.GONE);
        }
        if (!homeMenu.contains(BaseConstant.HOME_MENU_READ)) {
            layoutRead.setVisibility(View.GONE);
        }
        if (!homeMenu.contains(BaseConstant.HOME_MENU_SETTING)) {
            layoutSet.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        super.initData();
        headerLayout.showTitle(getString(R.string.home_main));
        headerLayout.showLeftImageButton(R.mipmap.home_left, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        tvUsername.setText(entity.getUsername());
        mvpPresenter.getLeftMenu();
    }

    @Override
    public void initListener() {
        layoutUpgrade.setOnClickListener(this);
        layoutSet.setOnClickListener(this);
        layoutRead.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.layoutUpgrade) {
            UpgradeIntent.launchUpgrade(entity);
        } else if (view.getId() == R.id.layoutSet) {
            SetModuleIntent.launchSet(entity);
        } else if (view.getId() == R.id.layoutRead) {
            ReadIntent.launchRead(entity);
        } else if (view.getId() == R.id.tvLogout) {
            StyledDialog.buildMdAlert(getString(R.string.home_tip), getString(R.string.home_quint_msg), new DialogListener() {
                @Override
                public void onFirst() {
                    StringCache.remove(Constant.USER_INFO);
                    toActivityWithFinish(LoginActivity.class, null);
                }

                @Override
                public void onSecond() {
                    StyledDialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    public void showLeftMenu(List<HomeMenu> menus) {
        menuList = menus;
        adapter.setData(menuList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
