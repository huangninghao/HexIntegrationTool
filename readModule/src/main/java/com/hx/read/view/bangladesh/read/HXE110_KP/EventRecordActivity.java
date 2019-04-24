package com.hx.read.view.bangladesh.read.HXE110_KP;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cbl.timepicker.TimePickerDialog;
import com.cbl.timepicker.data.Type;
import com.cbl.timepicker.listener.OnDateSetListener;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.bangladesh.HXE110.EventRecordContact;
import com.hx.read.presenter.bangladesh.HXE110.EventRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DAY_FORMAT;

public class EventRecordActivity extends RxMvpBaseActivity<EventRecordContact.Presenter> implements EventRecordContact.View, OnDateSetListener {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> freezeList = new ArrayList<>();
    private TimePickerDialog timePickerDialog;
    private TextView tvQuery;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private boolean isStartTime = false;
    private TextView tvInfo1;
    private TextView tvInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_record);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvQuery = findViewById(R.id.tvQuery);
        tvInfo1 = findViewById(R.id.tvInfo1);
        tvInfo2 = findViewById(R.id.tvInfo2);
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
        headerLayout.showTitle(getString(R.string.read_function_event_record));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.exportFreezeData(freezeList);
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
                TranXADRAssist assist = new TranXADRAssist();
                assist.startTime = tvStartTime.getText().toString();
                assist.endTime = tvEndTime.getText().toString();
                assist.obis = "";
                mvpPresenter.getShowList(assist);
            }
        });
    }

    @Override
    public EventRecordContact.Presenter createPresenter() {
        return new EventRecordPresenter(this);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

    }

    @Override
    public void showData(List<TranXADRAssist> list) {

    }
}
