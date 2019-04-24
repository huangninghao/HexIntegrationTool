package com.hx.set.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.App;
import com.hx.set.R;
import com.hx.set.contact.RateSetContact;
import com.hx.set.model.DTItem;
import com.hx.set.model.TimeItem;
import com.hx.set.presenter.RateSetPresenter;

import java.util.ArrayList;
import java.util.List;

public class RateSetActivity extends RxMvpBaseActivity<RateSetContact.Presenter> implements RateSetContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private XRecyclerView listView2;
    private TextView tvReade;
    private TextView tvNext;
    private HexRVBaseAdapter<DTItem> adapter;
    private List<DTItem> DTList = new ArrayList<>();
    private HexRVBaseAdapter<TimeItem> adapter2;
    private List<TimeItem> timeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_set);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                toActivity(RateTimeActivity.class, bundle);
            }
        });

        adapter = new HexRVBaseAdapter<DTItem>(App.getInstance(),DTList, R.layout.item_rate_dt_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, DTItem item) {
                if (item.isSelect == false) {
                    holder.setImageUrl(R.id.tvItemImage, R.mipmap.btn_select);
                }else {
                    holder.setImageUrl(R.id.tvItemImage, R.mipmap.btn_select_yes);
                }
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                for (int i = 0; i < DTList.size(); i++) {
                    DTItem item = DTList.get(i);
                    if (position == i) {
                        item.isSelect = true;
                    }else {
                        item.isSelect = false;
                    }
                }
                adapter.setData(DTList);
                adapter.notifyDataSetChanged();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(App.getInstance(),4);
        listView.setLayoutManager(gridLayoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
        mvpPresenter.getShowDTList();


        adapter2 = new HexRVBaseAdapter<TimeItem>(App.getInstance(), timeList, R.layout.item_rate_time_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TimeItem item) {
                TextView textView = holder.getTextView(R.id.tvItemTitle);
                ImageView imageView = holder.getImageView(R.id.tvItemImage);
                if (item.isAdd == false){
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    holder.setText(R.id.tvItemTitle, item.time+" "+item.type);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getInstance());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView2.setLayoutManager(layoutManager);
        listView2.setAdapter(adapter2);
        listView2.setPullRefreshEnabled(false);
        listView2.setNoMore(true);
        mvpPresenter.getShowTimeList();

    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_function_rate));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
        listView2 = findViewById(R.id.recyclerView2);
        tvNext = findViewById(R.id.tvNext);
        tvReade = findViewById(R.id.tvRead);
    }

    @Override
    public RateSetContact.Presenter createPresenter() {
        return new RateSetPresenter(this);
    }

    @Override
    public void showDTData(List<DTItem> list) {
        DTList = list;
        adapter.setData(DTList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showTimeData(List<TimeItem> list) {
        timeList = list;
        adapter2.setData(timeList);
        adapter2.notifyDataSetChanged();
    }
}
