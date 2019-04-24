package com.hx.read.view.guinea.read.HXE100DIP;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.cbl.timepicker.TimePickerDialog;
import com.cbl.timepicker.data.Type;
import com.cbl.timepicker.listener.OnDateSetListener;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.read.R;
import com.hx.read.contact.guinea.HXE100DIP.FreezeMonthContact;
import com.hx.read.presenter.guinea.HXE100DIP.FreezeMonthPresenter;
import com.hx.read.utils.ObisUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_MONTH_FORMAT;

public class FreezeMonthActivity extends RxMvpBaseActivity<FreezeMonthContact.Presenter> implements FreezeMonthContact.View, OnDateSetListener {

    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> freezeList = new ArrayList<>();
    private TimePickerDialog timePickerDialog;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private boolean isStartTime = false;
    private String obis = ObisUtil.METER_MONTHLY_FREEZE_POWER_INFORMATION;
    private Spinner spFreezeType;
    private TextView tvQuery;
    private TextView tvInfo1;
    private TextView tvInfo2;
    private TextView tvInfo3;
    private LinearLayout LayTitleInfo;
    private TranXADRAssist assist = new TranXADRAssist();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeze_month);
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, freezeList, R.layout.item_freeze_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                TextView tv3 = holder.getTextView(R.id.tvInfo3);
                if (obis.equals(ObisUtil.HXE115KP_METER_MONTH_FREEZE_PREPAID)) {
                    tv3.setVisibility(View.GONE);
                    holder.setText(R.id.tvInfo1, item.structList.get(0).value.substring(0, 10));
                    holder.setText(R.id.tvInfo2, item.structList.get(1).value + item.structList.get(1).unit);
                } else {
                    tv3.setVisibility(View.VISIBLE);
                    holder.setText(R.id.tvInfo1, item.structList.get(0).value.substring(0, 10));
                    holder.setText(R.id.tvInfo2, item.structList.get(1).value + item.structList.get(1).unit);
                    holder.setText(R.id.tvInfo3, item.structList.get(2).value + item.structList.get(2).unit);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(FreezeMonthActivity.this);
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
        tvInfo1 = findViewById(R.id.tvInfo1);
        tvInfo2 = findViewById(R.id.tvInfo2);
        tvInfo3 = findViewById(R.id.tvInfo3);
        listView = findViewById(R.id.recyclerView);
        LayTitleInfo = findViewById(R.id.LayTitleInfo);
        LayTitleInfo.setVisibility(View.INVISIBLE);
        headerLayout.showTitle(getString(R.string.read_function_monthly));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.exportFreezeData(freezeList);
            }
        });

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
    }

    @Override
    public void initData() {
        tvStartTime.setText(TimeUtil.getNowTime(DEFAULT_MONTH_FORMAT));
        tvEndTime.setText(TimeUtil.getNowTime(DEFAULT_MONTH_FORMAT));
    }

    @Override
    public void initListener() {
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
                int selectCount = spFreezeType.getSelectedItemPosition();
                if (selectCount == 0) {
                    obis = ObisUtil.METER_MONTHLY_FREEZE_POWER_INFORMATION;
                } else if (selectCount == 1) {
                    obis = ObisUtil.HXE115KP_METER_MONTH_FREEZE_PREPAID;
                }
                assist.startTime = tvStartTime.getText().toString();
                assist.endTime = tvEndTime.getText().toString();
                assist.obis = obis;
                mvpPresenter.getShowList(assist);
            }
        });
    }

    @Override
    public FreezeMonthContact.Presenter createPresenter() {
        return new FreezeMonthPresenter(this);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (isStartTime) {
            tvStartTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_MONTH_FORMAT));
        } else {
            tvEndTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_MONTH_FORMAT));
        }
    }

    @Override
    public void showData(List<TranXADRAssist> list) {
        freezeList = list;
        if (freezeList.size() > 0) {
            LayTitleInfo.setVisibility(View.VISIBLE);
            TranXADRAssist assist = list.get(0);
            if (obis.equals(ObisUtil.METER_MONTH_FREEZE_PREPAID)) {
                tvInfo1.setVisibility(View.VISIBLE);
                tvInfo2.setVisibility(View.VISIBLE);
                tvInfo3.setVisibility(View.GONE);
                tvInfo1.setText(assist.structList.get(0).name);
                tvInfo2.setText(assist.structList.get(1).name);
            } else {
                tvInfo3.setVisibility(View.VISIBLE);
                tvInfo1.setVisibility(View.VISIBLE);
                tvInfo2.setVisibility(View.VISIBLE);
                tvInfo1.setText(assist.structList.get(0).name);
                tvInfo2.setText(assist.structList.get(1).name);
                tvInfo3.setText(assist.structList.get(2).name);
            }
        }
        adapter.setData(freezeList);
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this, "", false);
    }
}
