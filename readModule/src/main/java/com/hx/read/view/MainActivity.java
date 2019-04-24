package com.hx.read.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.BaseConstant;
import com.hx.base.mInterface.config.HexBundle;
import com.hx.base.mInterface.module.home.HomeService;
import com.hx.base.mInterface.provider.IReadProvider;
import com.hx.base.mInterface.router.HexRouter;
import com.hx.base.model.DeviceBean;
import com.hx.base.model.UserInfoEntity;
import com.hx.read.R;

import java.util.ArrayList;
import java.util.List;

import com.hx.read.contact.ReadMianContact;
import com.hx.read.presenter.ReadMainPresenter;

/**
 * @author caibinglong
 * date 2018/11/6.
 * desc desc
 */

@Route(path = IReadProvider.READ_ACT_HOME)
public class MainActivity extends RxMvpBaseActivity<ReadMianContact.Presenter> implements ReadMianContact.View {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<DeviceBean> adapter;
    private List<DeviceBean> deviceBeanList = new ArrayList<>();
    UserInfoEntity userInfoEntity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_activity_main);
        if (getIntent().getExtras() != null) {
            userInfoEntity = (UserInfoEntity) getIntent().getExtras().getSerializable(UserInfoEntity.class.getName());
        }
        if (userInfoEntity == null) {
            return;
        }

        deviceBeanList = HomeService.getDeviceList(userInfoEntity);
        adapter = new HexRVBaseAdapter<DeviceBean>(this, deviceBeanList, R.layout.read_item_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, DeviceBean item) {
                holder.setText(R.id.tvMeterType, item.nameEn);
                holder.setImageUrl(R.id.imgMeter, item.imgUrl);
                holder.setText(R.id.tvMeterIoType, getResources().getText(R.string.read_comm_method) + ":" + HomeService.getCommMethod(item.commMethod));
                holder.setText(R.id.tvMeterProtocol, HomeService.getProtocolMethod(item.protocol));
            }

        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                String countryName = userInfoEntity.getEn_name().toUpperCase();
                String deviceName = deviceBeanList.get(position).nameEn.toUpperCase();
                String electricity = userInfoEntity.getElectricity();
                HexBundle bundle = new HexBundle();
                String path;
                if (!TextUtils.isEmpty(electricity)) {
                    path = "/" + countryName + "/" + electricity.toUpperCase() + "/" + BaseConstant.APP_READ + "/" + deviceName;
                } else {
                    path = "/" + countryName + "/" + BaseConstant.APP_READ + "/" + deviceName;
                }
                HexRouter.newInstance(path)
                        .withBundle(bundle)
                        .navigation();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
    }

    @Override
    public ReadMianContact.Presenter createPresenter() {
        return new ReadMainPresenter(this);
    }


    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.read_home_title));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
    }
}
