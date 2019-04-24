package com.hx.read.view.kyrgyzstan.northElectric.HXE310;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.BaseConstant;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE310.FeaturesContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXE310.FeaturePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 几内亚 功能目录
 * HEN022 on 2018/3/5.
 */
@Route(path = "/KYRGYZSTAN/NORTHELECTRIC/" + BaseConstant.APP_READ + "/HXE310")
public class FeaturesActivity extends RxMvpBaseActivity<FeaturesContact.Presenter> implements FeaturesContact.View {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<FeaturesMenuBean> adapter;
    private List<FeaturesMenuBean> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        adapter = new HexRVBaseAdapter<FeaturesMenuBean>(this, menuList, R.layout.item_function_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, FeaturesMenuBean item) {
                holder.setText(R.id.tvItemTitle, getString(item.getResourceIdName()));
                holder.setImageUrl(R.id.imgItem, item.imgResId);
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                FeaturesMenuBean item = menuList.get(position);
                toActivity(item.getCls());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(FeaturesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
        mvpPresenter.getShowList();
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.read_function_title));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public FeaturesContact.Presenter createPresenter() {
        return new FeaturePresenter(this);
    }

    @Override
    public void showData(List<FeaturesMenuBean> list) {
        menuList = list;
        adapter.setData(menuList);
        adapter.notifyDataSetChanged();
    }
}

