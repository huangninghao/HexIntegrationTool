package com.hx.read.view.ethiopia.read.HXE12_DL;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.cbl.timepicker.TimePickerDialog;
import com.cbl.timepicker.data.Type;
import com.cbl.timepicker.listener.OnDateSetListener;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.read.R;
import com.hx.read.contact.ethiopia.HXE12_DL.TimeSetContact;
import com.hx.read.presenter.ethiopia.HXE12_DL.TimeSetPresenter;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;
import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DATE_FORMAT;

public class TimeSetActivity extends RxMvpBaseActivity<TimeSetContact.Presenter> implements TimeSetContact.View,OnDateSetListener {

    private HeaderLayout headerLayout;
    private TextView tvTime;
    private TimePickerDialog timePickerDialog;
    private TextView tvRead;
    private TextView tvWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvTime = findViewById(R.id.tvTime);
        tvRead = findViewById(R.id.tvRead);
        tvWrite = findViewById(R.id.tvWrite);
        headerLayout.showTitle(getString(R.string.read_function_time_set));
        headerLayout.showLeftBackButton();
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(getSupportFragmentManager(), Type.ALL.toString());
            }
        });
        tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readTime();
            }
        });
        tvWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.writeTime(tvTime.getText().toString());
            }
        });
        timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.ALL)
                .setYearText(getString(R.string.year))
                .setMonthText(getString(R.string.month))
                .setDayText(getString(R.string.day))
                .setHourText(getString(R.string.hour))
                .setMinuteText(getString(R.string.minute))
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.confirm))
                .setTitleStringId(getString(R.string.time_picker))
                .setCallBack(this)
                .setThemeColor(ContextCompat.getColor(getInstance(), R.color.read_main_color))
                .build();
        mvpPresenter.readTime();
    }

    @Override
    public TimeSetContact.Presenter createPresenter() {
        return new TimeSetPresenter(this);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        tvTime.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DATE_FORMAT));
    }

    @Override
    public void showData(TranXADRAssist item) {
        String value = item.value;
        value = "20"+value.substring(0,2)+"-"+value.substring(2,4)+"-"+
                value.substring(4,6)+" "+value.substring(8,10)+":"+
                value.substring(10,12)+":"+value.substring(12,14);
        tvTime.setText(value);
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this,getString(R.string.loading),false);
    }
}
