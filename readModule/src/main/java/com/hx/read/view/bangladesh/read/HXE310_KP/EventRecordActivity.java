package com.hx.read.view.bangladesh.read.HXE310_KP;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
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
import com.hx.read.R;
import com.hx.read.contact.bangladesh.HXE310.EventRecordContact;
import com.hx.read.presenter.bangladesh.HXE310.EventRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DAY_FORMAT;

public class EventRecordActivity extends RxMvpBaseActivity<EventRecordContact.Presenter> implements EventRecordContact.View, OnDateSetListener {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> data = new ArrayList<>();
    private TimePickerDialog timePickerDialog;
    private Spinner spFreezeType;
    private TextView tvQuery;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private boolean isStartTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_record);
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, data, R.layout.event_listview_item_view) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.setText(R.id.tv_datetime, item.value + item.unit);
                if ("Date&&Time".equals(item.name)) {
                    holder.getView(R.id.line).setVisibility(View.INVISIBLE);
                } else {
                    holder.getView(R.id.line).setVisibility(View.VISIBLE);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(EventRecordActivity.this);
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
        spFreezeType = findViewById(R.id.spFreezeType);
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
        headerLayout.showTitle(getString(R.string.eventRecord));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() > 0 && !StringUtil.isEmpty(data.get(0).value)) {
                    if (mvpPresenter.saveData(data, getString(R.string.eventRecord))) {
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
                data.clear();
                adapter.setData(data);
                int selectCount = spFreezeType.getSelectedItemPosition();
                mvpPresenter.getEventRecord(tvStartTime.getText().toString(), tvEndTime.getText().toString(), selectCount);
            }
        });
    }

    @Override
    public EventRecordContact.Presenter createPresenter() {
        return new EventRecordPresenter(this);
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
        data = assists;
        adapter.setData(data);
    }
}
