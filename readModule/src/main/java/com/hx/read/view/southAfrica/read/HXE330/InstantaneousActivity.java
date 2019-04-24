package com.hx.read.view.southAfrica.read.HXE330;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.senegal.HXE310_KP.InstantaneousContact;
import com.hx.read.presenter.senegal.HXE310_KP.InstantaneousPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;

public class InstantaneousActivity extends RxMvpBaseActivity<InstantaneousContact.Presenter> implements InstantaneousContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist.StructBean> adapter;
    private List<TranXADRAssist.StructBean> insList = new ArrayList<>();
    private int readNum = 0;
    private TextView tvRead;
    private TranXADRAssist temAssit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantaneous);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(InstantaneousActivity.this);
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
        tvRead = findViewById(R.id.tvRead);
        headerLayout.showTitle(getString(R.string.read_function_instantaneous_amount));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.exportInstantaneousData(temAssit);
            }
        });
        listView = findViewById(R.id.recyclerView);

        temAssit = new TranXADRAssist();
    }

    @Override
    public void initListener() {
        super.initListener();
        tvRead.setOnClickListener(this);
    }

    @Override
    public InstantaneousContact.Presenter createPresenter() {
        return new InstantaneousPresenter(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tvRead) {
            insList = new ArrayList<>();
            mvpPresenter.read();
        }
    }

    @Override
    public void showData(List<TranXADRAssist> list) {
        for (TranXADRAssist item : list) {
            insList.addAll(item.structList);
        }

        adapter.setData(insList);
    }

    @Override
    public void showData(TranXADRAssist item) {
        HexLog.e("打印接受数据", JSON.toJSONString(item.structList));
        insList.addAll(item.structList);
        temAssit.structList = insList;
        adapter.setData(insList);
    }

    @Override
    public void showToast(@StringRes int resourceId) {
        readNum--;
        if (readNum <= 0) {
            tvRead.setEnabled(true);
            tvRead.setBackground(ContextCompat.getDrawable(ReadApp.getInstance(), R.drawable.btn_blue));
        }
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this, "", false);
    }
}
