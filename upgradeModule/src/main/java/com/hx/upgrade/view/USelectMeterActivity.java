package com.hx.upgrade.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.mInterface.module.home.HomeService;
import com.hx.base.mInterface.provider.IUpgradeProvider;
import com.hx.base.model.DeviceBean;
import com.hx.base.model.UserInfoEntity;
import com.hx.upgrade.R;
import com.hx.upgrade.contact.USelectMeterContact;
import com.hx.upgrade.model.CommBean;
import com.hx.upgrade.presenter.USelectMeterPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = IUpgradeProvider.UPGRADE_ACT_HOME)
public class USelectMeterActivity extends RxMvpBaseActivity<USelectMeterContact.Presenter> implements USelectMeterContact.View {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<DeviceBean> adapter;
    private List<DeviceBean> deviceBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_activity_select_meter);

        UserInfoEntity userInfoEntity = null;
        if (getIntent().getExtras() != null) {
            userInfoEntity = (UserInfoEntity) getIntent().getExtras().getSerializable(UserInfoEntity.class.getName());
        }
        if (userInfoEntity == null) {
            return;
        }
        adapter = new HexRVBaseAdapter<DeviceBean>(this, deviceBeanList, R.layout.up_item_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, DeviceBean item) {
                holder.setText(R.id.tvMeterType, item.nameEn);
                holder.setText(R.id.tvMeterMCU, getString(R.string.upgrade_meter_MCU) +  HomeService.getMeterMCU(item.chip));
                holder.setText(R.id.tvMeterProtocol,getString(R.string.communication_agreement) + HomeService.getProtocolMethod(item.protocol));
                holder.setText(R.id.tvMeterIoType, getString(R.string.communication_type) + HomeService.getCommMethod(item.commMethod));
                holder.setImageUrl(R.id.imgMeter, item.imgUrl);
            }

        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                CommBean commBean = new CommBean();
                //通讯协议 3 = 645协议 2 = 21协议；1 = DLMS协议
                switch (deviceBeanList.get(position).protocol) {
                    case "1":
                        commBean.setCommAgr(1);
                        break;
                    case "2":
                        commBean.setCommAgr(0);
                        break;
                    case "3":
                        commBean.setCommAgr(2);
                        break;
                }
                commBean.setCommType(deviceBeanList.get(position).commMethod.equals("1") ? 0 : 1);//通讯方式 1 = 光电通讯； 3 = RF通讯
                commBean.setMeterMCU(Integer.parseInt(deviceBeanList.get(position).chip));//芯片类型 0 = TDK 1 = RN8213 （用于处理升级文件）
                StringCache.putJavaBean("commBean", commBean);
                toActivity(UMainActivity.class);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(USelectMeterActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
        mvpPresenter.getAllMeter(userInfoEntity);
    }

    @Override
    public USelectMeterContact.Presenter createPresenter() {
        return new USelectMeterPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.upgrade));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public void showData(List<DeviceBean> deviceBeans) {
        deviceBeanList = deviceBeans;
        adapter.setData(deviceBeanList);
        adapter.notifyDataSetChanged();

    }
}
