package com.hx.set.view.indonesia.set.HXE320;

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
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.FunctionContact;
import com.hx.set.model.FunctionItem;
import com.hx.set.presenter.indonesia.HXE320.FunctionPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/INDONESIA" + BaseConstant.APP_SET + "/HXE320")
public class FunctionActivity extends RxMvpBaseActivity<FunctionContact.Presenter> implements FunctionContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<FunctionItem> adapter;
    private List<FunctionItem> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        adapter = new HexRVBaseAdapter<FunctionItem>(this, menuList, R.layout.item_function_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, FunctionItem item) {
                holder.setImageUrl(R.id.imgItem, item.imgId);
                holder.setText(R.id.tvItemTitle, item.itemTitle);
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                if (position == 0) {
                    toActivity(RateSetActivity.class, bundle);
                } else if (position == 1) {
                    bundle.putString("title", getResources().getString(R.string.set_function_settlement));
                    toActivity(ParameterSetActivity.class, bundle);
                } else if (position == 2) {
                    toActivity(DisplaySetActivity.class, bundle);
                } else if (position == 3) {
                    toActivity(BillingTimeActivity.class, bundle);
                }

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
        headerLayout.showTitle(getString(R.string.set_function_title));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public FunctionContact.Presenter createPresenter() {
        return new FunctionPresenter(this);
    }

    @Override
    public void showData(List<FunctionItem> list) {
        menuList = list;
        adapter.setData(menuList);
        adapter.notifyDataSetChanged();
    }
}
