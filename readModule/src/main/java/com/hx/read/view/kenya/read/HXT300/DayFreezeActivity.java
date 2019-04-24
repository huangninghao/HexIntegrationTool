package com.hx.read.view.kenya.read.HXT300;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cbl.timepicker.TimePickerDialog;
import com.cbl.timepicker.data.Type;
import com.cbl.timepicker.listener.OnDateSetListener;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.DayFreezeContact;
import com.hx.read.presenter.kenya.HXT300.DayFreezePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DAY_FORMAT;

/**
 * Created by HEN022
 * on 2018/3/5.
 * 几内亚 抄表目录
 */


public class DayFreezeActivity extends RxMvpBaseActivity<DayFreezeContact.Presenter> implements DayFreezeContact.View, OnDateSetListener {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> freezeList = new ArrayList<>();
    private TimePickerDialog timePickerDialog;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private boolean isStartTime = false;
    private Spinner spFreezeType;
    private TextView tvQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bangladesh_activity_freeze_daily_hxe110_kp);
        String[] arrModel = ReadApp.getInstance().getResources().getStringArray(R.array.kenya_daily_freeze);
        ArrayAdapter<String> spAdapter;//数组 配置器 下拉菜单赋值用
        //将可选内容与ArrayAdapter连接起来
        spAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrModel);
        //设置下拉列表的风格
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFreezeType.setAdapter(spAdapter);//将adapter 添加到spinner中
        spFreezeType.setVisibility(View.VISIBLE);//设置默认值
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, freezeList, R.layout.dayfreeze_listview_item_view) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.setText(R.id.tvDate, item.name);
                holder.setText(R.id.tvPValue, item.value + item.unit);
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(DayFreezeActivity.this);
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
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        spFreezeType = findViewById(R.id.spFreezeType);
        tvQuery = findViewById(R.id.tvQuery);
        tvStartTime.setText(TimeUtil.getNowTime(DEFAULT_DAY_FORMAT));
        tvEndTime.setText(TimeUtil.getNowTime(DEFAULT_DAY_FORMAT));
        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setYearText(getString(R.string.year))
                .setMonthText(getString(R.string.month))
                .setDayText(getString(R.string.day))
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.confirm))
                .setTitleStringId(getString(R.string.time_picker))
                .setCallBack(this)
                .setThemeColor(ContextCompat.getColor(getInstance(), R.color.read_main_color))
                .build();
        headerLayout.showTitle(getString(R.string.read_function_daily));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freezeList.size() > 0 && !StringUtil.isEmpty(freezeList.get(0).value)) {
                    if (mvpPresenter.saveData(freezeList, getString(R.string.read_function_daily))) {
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
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = true;
                timePickerDialog.show(getSupportFragmentManager(), Type.YEAR_MONTH_DAY.toString());
            }
        });
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = false;
                timePickerDialog.show(getSupportFragmentManager(), Type.YEAR_MONTH_DAY.toString());
            }
        });
        tvQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freezeList.clear();
                adapter.setData(freezeList);
                adapter.notifyDataSetChanged();
                int selectCount = spFreezeType.getSelectedItemPosition();
                mvpPresenter.getDayFreeze(tvStartTime.getText().toString(), tvEndTime.getText().toString(), selectCount);
            }
        });

    }

    @Override
    public DayFreezeContact.Presenter createPresenter() {
        return new DayFreezePresenter(this);
    }

    @Override
    public void showData(List<TranXADRAssist> list) {
        if (list == null || list.size() == 0) {
            showToast(R.string.emptyData);
            return;
        }
        TranXADRAssist tranXADRAssist;
        List<TranXADRAssist> assists = new ArrayList<>();
        for (TranXADRAssist assist : list) {
            for (TranXADRAssist.StructBean structBean : assist.structList) {
                tranXADRAssist = new TranXADRAssist();
                tranXADRAssist.value = structBean.value;
                tranXADRAssist.name = structBean.name;
                tranXADRAssist.unit = structBean.unit;
                assists.add(tranXADRAssist);
            }
        }
        freezeList = assists;
        adapter.setData(freezeList);
    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (isStartTime) {
            tvStartTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 10));
        } else {
            tvEndTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 10));
        }
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this, "", false);
    }
}
