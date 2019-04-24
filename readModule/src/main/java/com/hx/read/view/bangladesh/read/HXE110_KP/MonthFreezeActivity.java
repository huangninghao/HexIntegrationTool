package com.hx.read.view.bangladesh.read.HXE110_KP;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.read.R;
import com.hx.read.contact.bangladesh.HXE110.MonthFreezeContact;
import com.hx.read.presenter.bangladesh.HXE110.MonthFreezePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
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
    private Spinner spFreezeType;
    private TextView tvQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bangladesh_activity_freeze_monthly_hxe110_kp);
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, freezeList, R.layout.dayfreeze_listview_item_view) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                holder.setText(R.id.tvDate, item.name);
                holder.setText(R.id.tvPValue, item.value + item.unit);
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
        spFreezeType = findViewById(R.id.spFreezeType);
        tvQuery = findViewById(R.id.tvQuery);
        tvStartTime.setText(TimeUtil.getNowTime("yyyy-MM"));
        tvEndTime.setText(TimeUtil.getNowTime("yyyy-MM"));
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
                if (freezeList.size() > 0 && !StringUtil.isEmpty(freezeList.get(0).value)) {
                    if (mvpPresenter.saveData(freezeList, getString(R.string.read_function_monthly))) {
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
                int selectCount = spFreezeType.getSelectedItemPosition();
                mvpPresenter.getMonthFreeze(tvStartTime.getText().toString(), tvEndTime.getText().toString(), selectCount);
            }
        });
    }

    @Override
    public MonthFreezeContact.Presenter createPresenter() {
        return new MonthFreezePresenter(this);
    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (isStartTime) {
            tvStartTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 7));
        } else {
            tvEndTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 7));
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
                if (structBean.beanItems != null) {
                    for (TranXADRAssist.StructBean.BeanItem item : structBean.beanItems) {
                        String value = "";
                        if ("occurring time".equals(item.name)) {
                            value = item.value.substring(0, 2) + "-" + item.value.substring(2, 4) + "-"
                                    + item.value.substring(4, 6) + " " + item.value.substring(6, 8) + ":"
                                    + item.value.substring(8);
                        }else{
                            value = item.value;
                        }
                        tranXADRAssist = new TranXADRAssist();
                        tranXADRAssist.value = value;
                        tranXADRAssist.name = item.name;
                        tranXADRAssist.unit = item.unit;
                        assists.add(tranXADRAssist);
                    }
                }else {
                    tranXADRAssist = new TranXADRAssist();
                    tranXADRAssist.value = structBean.value;
                    tranXADRAssist.name = structBean.name;
                    tranXADRAssist.unit = structBean.unit;
                    assists.add(tranXADRAssist);
                }
            }
        }
        freezeList = assists;
        adapter.setData(freezeList);
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this, "", false);
    }
}
