package com.hx.read.view.bangladesh.read.HXE110_KP;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.bangladesh.HXE110.PrepaymentContact;
import com.hx.read.presenter.bangladesh.HXE110.PrepaymentPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;

public class PrepaymentActivity extends RxMvpBaseActivity<PrepaymentContact.Presenter> implements PrepaymentContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist.StructBean> adapter;
    private List<TranXADRAssist.StructBean> insList = new ArrayList<>();
    private int readNum = 0;
    private TextView tvRead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepayment);
        adapter = new HexRVBaseAdapter<TranXADRAssist.StructBean>(this, insList, R.layout.item_instantaneous_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist.StructBean item) {
                if (item.visible) {
                    holder.getView(R.id.layoutItem).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tvItemTitle, item.name);
                    holder.setText(R.id.tvAmount, item.value + " " + item.unit);
                } else {
                    holder.getView(R.id.layoutItem).setVisibility(View.GONE);
                }
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(PrepaymentActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
    }

    @Override
    public PrepaymentContact.Presenter createPresenter() {
        return new PrepaymentPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvRead = findViewById(R.id.tvRead);
        headerLayout.showTitle(getString(R.string.read_function_prepayment));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listView = findViewById(R.id.recyclerView);
    }


    @Override
    public void showData(List<TranXADRAssist> list) {

    }

    @Override
    public void showData(TranXADRAssist item) {

    }
}
