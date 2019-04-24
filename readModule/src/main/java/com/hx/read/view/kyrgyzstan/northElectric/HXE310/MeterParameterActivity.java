package com.hx.read.view.kyrgyzstan.northElectric.HXE310;

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
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE310.MeterParameterContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXE310.MeterParameterPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;

public class MeterParameterActivity extends RxMvpBaseActivity<MeterParameterContact.Presenter> implements MeterParameterContact.View {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> insList = new ArrayList<>();
    private int readNum = 0;
    private TextView tvRead,tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instantaneous);
        insList = mvpPresenter.getShowList();
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, insList, R.layout.item_instantaneous_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.setText(R.id.tvItemTitle, item.name);
                holder.setText(R.id.tvAmount, item.value + " " + item.unit);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(MeterParameterActivity.this);
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
        tvInfo = findViewById(R.id.tvInfo);
        tvInfo.setText(R.string.meter_parameter);
        headerLayout.showTitle(getString(R.string.meter_parameter));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        listView = findViewById(R.id.recyclerView);
    }

    @Override
    public MeterParameterContact.Presenter createPresenter() {
        return new MeterParameterPresenter(this);
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
            adapter.notifyDataSetChanged();
            readNum = insList.size();
            tvRead.setEnabled(false);
            tvRead.setBackground(ContextCompat.getDrawable(ReadApp.getInstance(), R.drawable.read_btn_gray));
            mvpPresenter.read();
        }
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
    public void showData(TranXADRAssist item) {
        for (TranXADRAssist assist : insList) {
            if (assist.obis.equals(item.obis)) {
                String value = item.value;
                if (item.value.contains(".")) {
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
}
