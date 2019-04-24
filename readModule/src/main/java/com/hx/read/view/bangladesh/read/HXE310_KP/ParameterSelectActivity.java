package com.hx.read.view.bangladesh.read.HXE310_KP;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.bangladesh.HXE310.ParameterSelectContact;
import com.hx.read.model.ParameterItem;
import com.hx.read.presenter.bangladesh.HXE310.ParameterSelectPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParameterSelectActivity extends RxMvpBaseActivity<ParameterSelectContact.Presenter> implements ParameterSelectContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<ParameterItem> adapter;
    private List<ParameterItem> paramList = new ArrayList<>();
    private List<ParameterItem> paramList_tp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_select);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.read_parameter_list));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(ReadApp.getInstance(), R.color.white), R.string.confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ParameterItem> selectList = new ArrayList<>();
                for (ParameterItem parameterItem : paramList) {
                    if (parameterItem.isSelect) {
                        selectList.add(parameterItem);
                    }
                }
                Intent bundle = new Intent();
                bundle.putExtra("list", (Serializable) selectList);
                setResult(1000, bundle);
                finish();
            }
        });
        listView = findViewById(R.id.recyclerView);
        adapter = new HexRVBaseAdapter<ParameterItem>(this, paramList, R.layout.item_parameter_select_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, final ParameterItem item) {
                holder.setText(R.id.tvItemTitle, item.paramName);
                ImageView circleImage = holder.getImageView(R.id.imgItem);
                TextView textView = holder.getTextView(R.id.tvItemTitle);
                if (!item.isSelect) {
                    holder.setImageUrl(R.id.imgItem, R.mipmap.img_check_no);
                } else {
                    holder.setImageUrl(R.id.imgItem, R.mipmap.img_check_yes);
                }
                circleImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.isSelect = !item.isSelect;
                        adapter.setData(paramList);
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                paramList.get(position).isSelect = !paramList.get(position).isSelect;
                adapter.setData(paramList);
            }
        });
        paramList_tp = (List<ParameterItem>) this.getIntent().getSerializableExtra("paralist");
        String tp = this.getIntent().getStringExtra("type");
        paramList.addAll(paramList_tp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReadApp.getInstance());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
        mvpPresenter.setReadData(paramList, tp.equals("0") ? 0 : 1);
    }

    @Override
    public ParameterSelectContact.Presenter createPresenter() {
        return new ParameterSelectPresenter(this);
    }

    @Override
    public void showData(List<ParameterItem> list) {
        paramList = list;
        adapter.setData(paramList);
    }

}
