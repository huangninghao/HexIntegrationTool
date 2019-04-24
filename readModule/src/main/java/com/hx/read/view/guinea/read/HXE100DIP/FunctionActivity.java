package com.hx.read.view.guinea.read.HXE100DIP;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.base.BaseConstant;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.guinea.HXE100DIP.FunctionContact;
import com.hx.read.presenter.guinea.HXE100DIP.FunctionPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/GUINEA/" + BaseConstant.APP_READ + "/HXE100DIP")
public class FunctionActivity extends RxMvpBaseActivity<FunctionContact.Presenter> implements FunctionContact.View {

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(FunctionActivity.this);
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
    public FunctionContact.Presenter createPresenter() {
        return new FunctionPresenter(this);
    }

    @Override
    public void showData(List<FeaturesMenuBean> list) {
        menuList = list;
        adapter.setData(menuList);
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this, "", false);
    }
}
