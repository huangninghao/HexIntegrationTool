package com.hx.read.view.kenya.read.HXT300;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.DemandContact;
import com.hx.read.presenter.kenya.HXT300.DemandPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;

public class DemandActivity extends RxMvpBaseActivity<DemandContact.Presenter> implements DemandContact.View {


    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> insList = new ArrayList<>();
    private int readNum = 0;
    private TextView tvRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand);
        insList = mvpPresenter.getShowList();
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, insList, R.layout.item_instantaneous_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.getView(R.id.layoutItem).setVisibility(View.VISIBLE);
                holder.setText(R.id.tvItemTitle, item.name);
                holder.setText(R.id.tvAmount, item.value + " " + item.unit);
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(DemandActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
    }

    @Override
    public void initListener() {
        super.initListener();
        tvRead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tvRead) {
            insList = mvpPresenter.getShowList();
            adapter.setData(insList);
            readNum = insList.size() - 1;
            tvRead.setEnabled(false);
            tvRead.setBackground(ContextCompat.getDrawable(ReadApp.getInstance(), R.drawable.read_btn_gray));
            mvpPresenter.read();
        }
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvRead = findViewById(R.id.tvRead);
        headerLayout.showTitle(getString(R.string.read_function_demand));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insList.size() > 0 && !StringUtil.isEmpty(insList.get(0).value)) {
                    if (mvpPresenter.saveData(insList, getString(R.string.read_function_demand))) {
                        showToast(getInstance().getString(R.string.file_success));
                    } else {
                        showToast(getInstance().getString(R.string.failed));
                    }
                } else {
                    showToast(getInstance().getString(R.string.emptyData));
                }
            }
        });
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public DemandContact.Presenter createPresenter() {
        return new DemandPresenter(this);
    }


    @Override
    public void showToast(@StringRes int resourceId) {
        readNum--;
        if (readNum <= 0) {
            tvRead.setEnabled(true);
            tvRead.setBackground(ContextCompat.getDrawable(getInstance(), R.drawable.btn_blue));
        }
    }

    @Override
    public void showToast(String resourceId) {
        ToastUtils.showToast(this, resourceId);
        tvRead.setEnabled(true);
        tvRead.setBackground(ContextCompat.getDrawable(getInstance(), R.drawable.btn_blue));
    }

    @Override
    public void showData(TranXADRAssist assist) {
        for (TranXADRAssist item : insList) {
            if (item.obis.equals(assist.obis)) {
                if (!"occurring time".equals(item.name)) {
                    int i = assist.value.indexOf(".");
                    item.value = Integer.parseInt(assist.value.substring(0, i)) + "." + assist.value.substring(i + 1);
                } else {
                    item.value = assist.value;
                }
                readNum--;
                break;
            }
        }
        adapter.setData(insList);
        adapter.notifyDataSetChanged();
        if (readNum <= 0) {
            tvRead.setEnabled(true);
            tvRead.setBackground(ContextCompat.getDrawable(getInstance(), R.drawable.btn_blue));
        }
    }
}
