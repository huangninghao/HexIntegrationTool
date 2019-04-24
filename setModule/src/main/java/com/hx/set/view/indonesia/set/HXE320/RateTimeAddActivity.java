package com.hx.set.view.indonesia.set.HXE320;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.cbl.timepicker.TimePickerDialog;
import com.cbl.timepicker.data.Type;
import com.cbl.timepicker.listener.OnDateSetListener;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.TimeUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.App;
import com.hx.set.R;
import com.hx.set.contact.RateTimeContact;
import com.hx.set.presenter.RateTimePresenter;

import static com.hexing.libhexbase.tools.TimeUtil.HOUR;

public class RateTimeAddActivity extends RxMvpBaseActivity<RateTimeContact.Presenter> implements RateTimeContact.View, OnDateSetListener {
    private TimePickerDialog mDialogYearMonth;
    private HeaderLayout headerLayout;
    private TextView comfirmButton;
    private TextView timeSet;
    private String rtnDate;
   private Spinner spn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_time_add);

    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        comfirmButton = findViewById(R.id.tvRead);
        timeSet = findViewById(R.id.timeset);
        headerLayout.showTitle(getString(R.string.set_rote_effective));
        headerLayout.showLeftBackButton();
        spn = findViewById(R.id.spinner2);
        timeSet.setText(TimeUtil.getDateToString(0, HOUR));
        rtnDate =  TimeUtil.getDateToString(0, HOUR);
        mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)

                .setHourText(getString(R.string.hour))
                .setMinuteText(getString(R.string.min))
                .setCancelStringId(getString(R.string.cancel))
                .setSureStringId(getString(R.string.sure))
                .setTitleStringId(getString(R.string.time_picker))
                .setCallBack(this)
                .setThemeColor(ContextCompat.getColor(App.getInstance(), R.color.colorPrimary))
                .build();

        comfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent();
                intent.putExtra("return_date",rtnDate);
                intent.putExtra("return_fee",String.valueOf(spn.getSelectedItemId()+1));
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        timeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialogYearMonth.show(getSupportFragmentManager(), Type.HOURS_MINS.toString());
            }
        });
    }

    @Override
    public RateTimeContact.Presenter createPresenter() {
        return new RateTimePresenter(this);
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

        rtnDate  = TimeUtil.getDateToString(millseconds, HOUR);
        timeSet.setText(TimeUtil.getDateToString(millseconds, HOUR));

    }
}
