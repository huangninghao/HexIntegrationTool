package com.hx.set.view.indonesia.set.HXE320;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.cbl.timepicker.TimePickerDialog;
import com.cbl.timepicker.data.Type;
import com.cbl.timepicker.listener.OnDateSetListener;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.App;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.RateTimeContact;
import com.hx.set.presenter.indonesia.HXE320.RateTimePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.tools.TimeUtil.DEFAULT_DAY_FORMAT;

public class RateTimeActivity extends RxMvpBaseActivity<RateTimeContact.Presenter> implements RateTimeContact.View ,OnDateSetListener {

    private HeaderLayout headerLayout;
    private TextView timeSet;
    private TimePickerDialog mDialogYearMonth;
    private TextView tvread;
     private List<TranXADRAssist> writedata;
    private List<TranXADRAssist> tp_writedata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_time);
        Intent intent = getIntent();
        writedata = new ArrayList<>();
        tp_writedata = (List<TranXADRAssist>) this.getIntent().getSerializableExtra("writeData");
        for(int i=0;i<tp_writedata.size();i++)
        {
            writedata.add(tp_writedata.get(i).clone());

        }
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_rote_effective));
        headerLayout.showLeftBackButton();
        tvread = findViewById(R.id.tvRead);
        tvread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.set(writedata,rtnDate);
            }
        });

        timeSet = findViewById(R.id.tvTime);
        timeSet.setText(TimeUtil.getDateToString(System.currentTimeMillis(), DEFAULT_DAY_FORMAT));
        rtnDate =TimeUtil.getDateToString(System.currentTimeMillis(), DEFAULT_DAY_FORMAT);
        timeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogYearMonth.show(getSupportFragmentManager(), Type.YEAR_MONTH_DAY.toString());
            }
        });
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setYearText(getString(R.string.year))
                .setMonthText(getString(R.string.month))
                .setDayText(getString(R.string.day))

                .setHourText(getString(R.string.hour))
                .setMinuteText(getString(R.string.min))

                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.sure))
                .setTitleStringId(getString(R.string.time_picker))
                .setCallBack(this)
                .setThemeColor(ContextCompat.getColor(App.getInstance(), R.color.colorPrimary))
                .build();
    }
    String  rtnDate="";
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

        rtnDate  = TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT);
        timeSet.setText(TimeUtil.getDateToString(millseconds, DEFAULT_DAY_FORMAT));

    }
    @Override
    public RateTimeContact.Presenter createPresenter() {
        return new RateTimePresenter(this);
    }
    @Override
    public  void  ShowTime ( String dateTime) {
        rtnDate = dateTime;
        timeSet.setText(dateTime);
    }
}
