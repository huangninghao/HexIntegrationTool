package com.hx.set.view.indonesia.set.HXE320;

import android.content.Intent;
import android.os.Bundle;
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
import com.hx.set.contact.indonesia.HXE320.ParameterSetContact;
import com.hx.set.model.ParameterItem;
import com.hx.set.presenter.indonesia.HXE320.ParameterSetPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParameterSetActivity extends RxMvpBaseActivity<ParameterSetContact.Presenter> implements ParameterSetContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<ParameterItem> adapter;
    private List<ParameterItem> paramList = new ArrayList<>();
    private TextView tvReade;
    private TextView tvNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_set);
        adapter = new HexRVBaseAdapter<ParameterItem>(this, paramList, R.layout.item_parameter_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, ParameterItem item) {
                holder.setText(R.id.tvItemTitle, item.paramName);
                ImageView circleImage = holder.getImageView(R.id.imgItem);
                TextView textView = holder.getTextView(R.id.tvItemTitle);
                if (item.isSelect == false) {
                    textView.setTextColor(getResources().getColor(R.color.black));
                    circleImage.setVisibility(View.GONE);
                }else {
                    textView.setTextColor(getResources().getColor(R.color.set_main_color));
                    circleImage.setVisibility(View.VISIBLE);
                }
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {


            }
        });

        tvReade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.getShowList();
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.set(paramList);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getInstance());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);

        //mvpPresenter.getShowList();
    }

    @Override
    public void initView() {
        super.initView();
        String title = getBundle().getString("title","");
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(title);
        headerLayout.showLeftBackButton();
        headerLayout.showRightImageButton(R.mipmap.btn_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("paralist",(Serializable)paramList);
                bundle.putString("type","0");
                toActivityForResult(ParameterSelectActivity.class, 1000,bundle);
            }
        });
        listView = findViewById(R.id.recyclerView);
        tvReade = findViewById(R.id.tvRead);
        tvNext = findViewById(R.id.tvNext);
    }

    @Override
    public ParameterSetContact.Presenter createPresenter() {
        return new ParameterSetPresenter(this);
    }

    @Override
    public void showData(List<ParameterItem> list) {
        paramList = list;
        adapter.setData(paramList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && data != null) {
            Serializable serializable = (Serializable) data.getSerializableExtra("list");
            if (serializable == null) {
                return;
            }
            paramList=((List<ParameterItem>)serializable);
            adapter.setData(paramList);
            adapter.notifyDataSetChanged();
        }
    }
}
