package com.hx.read.view.kyrgyzstan.northElectric.HXF300;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
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
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXF300.MonthFreezeContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXF300.MonthFreezePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DATE_NO_SEPRATOR_FORMAT;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DAY_FORMAT;

public class MonthFreezeActivity extends RxMvpBaseActivity<MonthFreezeContact.Presenter> implements MonthFreezeContact.View, OnDateSetListener {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> freezeList = new ArrayList<>();
    private TimePickerDialog timePickerDialog;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private boolean isStartTime = false;
    private String startTime;
    private String endTime;
    private TextView tvQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hxe110_activity_freeze_day);
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, freezeList, R.layout.dayfreeze_listview_item_view) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.setText(R.id.tvDate, item.name);
                holder.setText(R.id.tvPValue, item.value + (StringUtil.isEmpty(item.unit) ? "" : item.unit));
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(MonthFreezeActivity.this);
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
        tvQuery = findViewById(R.id.tvQuery);
        startTime = TimeUtil.getNowTime(DEFAULT_DAY_FORMAT).substring(0,7);
        endTime = TimeUtil.getNowTime(DEFAULT_DAY_FORMAT).substring(0,7);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setYearText(getString(R.string.year))
                .setMonthText(getString(R.string.month))
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.confirm))
                .setTitleStringId(getString(R.string.time_picker))
                .setCallBack(this)
                .setThemeColor(ContextCompat.getColor(getInstance(), R.color.read_main_color))
                .build();
        headerLayout.showTitle(getString(R.string.read_function_monthly));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (freezeList != null && freezeList.size() > 0) {
                    if (mvpPresenter.saveData(freezeList, getResources().getString(R.string.read_function_monthly))) {
                        showToast(R.string.export_success);
                    } else {
                        showToast(R.string.export_failure);
                    }
                } else {
                    showToast(R.string.emptyData);
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
                startTime = tvStartTime.getText().toString();
                endTime = tvEndTime.getText().toString();
                mvpPresenter.getShowList(startTime, endTime);
            }
        });
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
            startTime = TimeUtil.getDateToString(millseconds, DEFAULT_DATE_NO_SEPRATOR_FORMAT).substring(2);
            tvStartTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 7));
        } else {
            endTime = TimeUtil.getDateToString(millseconds, DEFAULT_DATE_NO_SEPRATOR_FORMAT).substring(2);
            tvEndTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 7));
        }
    }

    @Override
    public MonthFreezeContact.Presenter createPresenter() {
        return new MonthFreezePresenter(this);
    }
}
