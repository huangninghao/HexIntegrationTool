package com.hx.read.view.ethiopia.read.HXE12_DL;

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
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.ethiopia.HXE12_DL.InstantaneousContact;
import com.hx.read.presenter.ethiopia.HXE12_DL.InstantaneousPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;

public class InstantaneousActivity extends RxMvpBaseActivity<InstantaneousContact.Presenter> implements InstantaneousContact.View {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> insList = new ArrayList<>();
    private int readNum = 0;
    private TextView tvRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        insList = mvpPresenter.getShowList();
        setContentView(R.layout.activity_instantaneous);
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, insList, R.layout.item_instantaneous_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.setText(R.id.tvItemTitle, item.name);
                holder.setText(R.id.tvAmount, item.value + " " + item.unit);
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(InstantaneousActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
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
                if (insList != null && insList.size() > 0) {
                    if (mvpPresenter.saveData(insList, getResources().getString(R.string.read_function_instantaneous_amount))) {
                        showToast(getResources().getString(R.string.export_success));
                    } else {
                        showToast(getResources().getString(R.string.export_failure));
                    }
                } else {
                    showToast(getResources().getString(R.string.emptyData));
                }
            }
        });
        listView = findViewById(R.id.recyclerView);
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
            insList = mvpPresenter.getShowList();
            adapter.setData(insList);
            adapter.notifyDataSetChanged();
            readNum = insList.size();
            tvRead.setEnabled(false);
            tvRead.setBackground(ContextCompat.getDrawable(ReadApp.getInstance(), R.drawable.read_btn_gray));
            mvpPresenter.read();
        }
    }

    @Override
    public void showData(List<TranXADRAssist> list) {

    }

    @Override
    public void showData(TranXADRAssist item) {
        for (TranXADRAssist assist : insList) {
            if (assist.obis.equals(item.obis)) {
                String value = item.value;
                if (item.obis.equals("901F")){
                    value = value.substring(0, 12);
                    value = value.substring(0, 9) + "." + value.substring(9);
                }
                if (value.contains(".")) {
                    value = Integer.parseInt(value.substring(0, value.indexOf("."))) + value.substring(value.indexOf("."));
                }
                assist.value = value;
                readNum--;
                break;
            }
        }
        adapter.setData(insList);
        adapter.notifyDataSetChanged();
        if (readNum <= 0) {
            tvRead.setEnabled(true);
            tvRead.setBackground(ContextCompat.getDrawable(ReadApp.getInstance(), R.drawable.btn_blue));
        }
    }

    @Override
    public void showToast(@StringRes int resourceId) {
        tvRead.setEnabled(true);
        tvRead.setBackground(ContextCompat.getDrawable(ReadApp.getInstance(), R.drawable.btn_blue));
    }
}
