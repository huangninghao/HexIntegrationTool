package com.hx.read.view.bangladesh.read.HXE110_KP;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.bangladesh.HXE110.SetContact;
import com.hx.read.presenter.bangladesh.HXE110.SetPresenter;

import cn.hexing.HexAction;
import cn.hexing.model.TranXADRAssist;

public class SettingActivity extends RxMvpBaseActivity<SetContact.Presenter> implements SetContact.View {

    private HeaderLayout headerLayout;
    private EditText edit1;
    private TextView edit2;
    private TextView tvRead1;
    private TextView tvSet1;
    private TextView tvRead2;
    private TextView tvSet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public SetContact.Presenter createPresenter() {
        return new SetPresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        tvRead1 = findViewById(R.id.tvRead1);
        tvSet1 = findViewById(R.id.tvSet1);
        tvRead2 = findViewById(R.id.tvRead2);
        tvSet2 = findViewById(R.id.tvSet2);
        headerLayout.showTitle(getString(R.string.read_set));
        headerLayout.showLeftBackButton();
        tvRead1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readOverdraft();
            }
        });
        tvSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.setOverdraft(edit1.getText().toString());
            }
        });
        tvRead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readAlarm();
            }
        });
        tvSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.setAlarm(edit2.getText().toString());
            }
        });
    }

    @Override
    public void showData(TranXADRAssist tranXADRAssist) {
        if (tranXADRAssist.aResult) {
            if (tranXADRAssist.actionType == HexAction.ACTION_READ) {
                String value = tranXADRAssist.value;
                if (value.contains(".")) {
                    value = value.substring(0, value.indexOf("."));
                }
                if (tranXADRAssist.obis.equals("1#1.0.134.129.4.255#2")) {
                    edit1.setText(value);
                } else {
                    edit2.setText(value);
                }
            } else {
                ToastUtils.showToast(this, R.string.success);
            }
        } else {
            ToastUtils.showToast(this, R.string.failed);
        }
    }
}
