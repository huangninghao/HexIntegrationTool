package com.hx.read.view.ethiopia.read.HXE12_DL;

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
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.read.R;
import com.hx.read.contact.ethiopia.HXE12_DL.FreezeContact;
import com.hx.read.presenter.ethiopia.HXE12_DL.FreezePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DATE_NO_SEPRATOR_FORMAT;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DAY_FORMAT;

public class FreezeActivity extends RxMvpBaseActivity<FreezeContact.Presenter> implements FreezeContact.View ,OnDateSetListener {

    private HeaderLayout headerLayout;
    private int freezeType;
    private XRecyclerView listView;
    private TextView tvStartTime, tvEndTime, tvQuery;
    private Spinner spFreezeType;
    private HexRVBaseAdapter<TranXADRAssist> adapter;
    private List<TranXADRAssist> freezeList = new ArrayList<>();
    private TimePickerDialog timePickerDialog;
    private boolean isStartTime = false;
    private String startTime;
    private String endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hxe21_dl_activity_freeze_month);
        adapter = new HexRVBaseAdapter<TranXADRAssist>(this, freezeList, R.layout.dayfreeze_listview_item_view) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, TranXADRAssist item) {
                if (item.visible) {
                    holder.setText(R.id.tvDate, item.name);
                    holder.setText(R.id.tvPValue, item.value + item.unit);
                }
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(FreezeActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
//        mvpPresenter.getShowList();
    }

    @Override
    public void initView() {
        super.initView();
        //String title = getBundle().getString("title","");
        // freezeType = getBundle().getInt("type",0);
        headerLayout = findViewById(R.id.headerLayout);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        tvQuery = findViewById(R.id.tvQuery);
        spFreezeType = findViewById(R.id.spFreezeType);
        tvStartTime.setText(mvpPresenter.getLastMonth());
        tvEndTime.setText(tvStartTime.getText());
        tvStartTime.setBackground(getResources().getDrawable(R.drawable.tv_gray_backgrond));
        tvEndTime.setBackground(getResources().getDrawable(R.drawable.tv_gray_backgrond));
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
        headerLayout.showTitle(R.string.read_function_monthly);
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
//        tvStartTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isStartTime = true;
//                timePickerDialog.show(getSupportFragmentManager(), Type.YEAR_MONTH_DAY.toString());
//            }
//        });
//        tvEndTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isStartTime = false;
//                timePickerDialog.show(getSupportFragmentManager(), Type.YEAR_MONTH_DAY.toString());
//            }
//        });
        tvQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freezeList.clear();
                adapter.setData(freezeList);

                mvpPresenter.getShowList();
            }
        });
    }

    @Override
    public FreezeContact.Presenter createPresenter() {
        return new FreezePresenter(this);
    }

    @Override
    public void showData(Object object) {
//        View headView = LayoutInflater.from(this).inflate(R.layout.freeze_header, listView);
//        listView.addHeaderView(headView);
        freezeList.clear();
        freezeList.add((TranXADRAssist)object);
        adapter.setData(freezeList);
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this,getString(R.string.loading),false);
    }


    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        if (isStartTime) {
            startTime = TimeUtil.getDateToString(millseconds, DEFAULT_DATE_NO_SEPRATOR_FORMAT).substring(2);
            tvStartTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 10));
        } else {
            endTime = TimeUtil.getDateToString(millseconds, DEFAULT_DATE_NO_SEPRATOR_FORMAT).substring(2);
            tvEndTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT).substring(0, 10));
        }
    }
}
