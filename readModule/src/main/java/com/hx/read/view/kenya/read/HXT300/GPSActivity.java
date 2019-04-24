package com.hx.read.view.kenya.read.HXT300;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.kenya.HXT300.GPSContact;
import com.hx.read.presenter.kenya.HXT300.GPSPresenter;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.model.TranXADRAssist;

import static com.hexing.libhexbase.application.HexApplication.getInstance;

public class GPSActivity extends RxMvpBaseActivity<GPSContact.Presenter> implements GPSContact.View {
    private TextView tvRead;
    private HeaderLayout headerLayout;
    private EditText edLongitude, edLatitude;
    private List<TranXADRAssist> insList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvRead = findViewById(R.id.tvRead);
        edLongitude = findViewById(R.id.edit1);
        edLatitude = findViewById(R.id.edit2);
        headerLayout.showTitle(getString(R.string.read_function_gps));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(ContextCompat.getColor(getInstance(), R.color.white), R.string.read_instantaneous_export, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insList.size() > 0) {
                    if (mvpPresenter.saveData(insList, getString(R.string.read_function_gps))) {
                        showToast(getInstance().getString(R.string.file_success));
                    } else {
                        showToast(getInstance().getString(R.string.failed));
                    }
                } else {
                    showToast(getInstance().getString(R.string.emptyData));
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        tvRead.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tvRead) {
            mvpPresenter.readGPS();
        }
    }

    @Override
    public GPSContact.Presenter createPresenter() {
        return new GPSPresenter(this);
    }

    @Override
    public void showData(TranXADRAssist item) {
        int index = item.value.indexOf(';');
        edLongitude.setText(item.value.substring(0, index));
        edLatitude.setText(item.value.substring(index + 1));
        TranXADRAssist tranXADRAssist = new TranXADRAssist();
        tranXADRAssist.name = "Longitude";
        tranXADRAssist.value = edLongitude.getText().toString();
        insList.add(tranXADRAssist);
        tranXADRAssist = new TranXADRAssist();
        tranXADRAssist.name = "Latitude";
        tranXADRAssist.value = edLatitude.getText().toString();
        insList.add(tranXADRAssist);
    }
}
