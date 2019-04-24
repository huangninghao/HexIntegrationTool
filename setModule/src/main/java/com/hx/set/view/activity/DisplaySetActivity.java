package com.hx.set.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.R;
import com.hx.set.contact.DisplaySetContact;
import com.hx.set.model.FunctionItem;
import com.hx.set.presenter.DisplaySetPresenter;

import java.util.ArrayList;
import java.util.List;

public class DisplaySetActivity extends RxMvpBaseActivity<DisplaySetContact.Presenter> implements DisplaySetContact.View  {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<FunctionItem> adapter;
    private List<FunctionItem> menuList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_set);
        adapter = new HexRVBaseAdapter<FunctionItem>(this, menuList, R.layout.item_display_set_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, FunctionItem item) {
                holder.setText(R.id.tvItemTitle, item.itemTitle);
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                switch (position){
                    case 0:
                        bundle.putString("title",getResources().getString(R.string.set_display_rotate));
                        toActivity(ParameterSetActivity.class, bundle);
                        break;
                    case 1:
                        bundle.putString("title",getResources().getString(R.string.set_display_button_1));
                        toActivity(ParameterSetActivity.class, bundle);
                        break;
                    case 2:
                        bundle.putString("title",getResources().getString(R.string.set_display_button_2));
                        toActivity(ParameterSetActivity.class, bundle);
                        break;
                    case 3:

                        toActivity(DisplayIntervalActivity.class, bundle);
                        break;
                }

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(DisplaySetActivity.this);
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
        headerLayout.showTitle(getString(R.string.set_function_display));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public DisplaySetContact.Presenter createPresenter() {
        return new DisplaySetPresenter(this);
    }

    @Override
    public void showData(List<FunctionItem> list) {
        menuList = list;
        adapter.setData(menuList);
        adapter.notifyDataSetChanged();
    }
}
