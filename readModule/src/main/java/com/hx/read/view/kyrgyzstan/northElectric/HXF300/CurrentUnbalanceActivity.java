package com.hx.read.view.kyrgyzstan.northElectric.HXF300;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXF300.CurrentUnbalanceContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXF300.CurrentUnbalancePresenter;

import cn.hexing.model.TranXADRAssist;

public class CurrentUnbalanceActivity extends RxMvpBaseActivity<CurrentUnbalanceContact.Presenter> implements CurrentUnbalanceContact.View {

    private HeaderLayout headerLayout;
    private EditText editThreshold;
    private EditText editTime;
    private TextView tvRead1;
    private TextView tvSet1;
    private TextView tvRead2;
    private TextView tvSet2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_unbalance_threshold);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        editThreshold = findViewById(R.id.editThreshold);
        editTime = findViewById(R.id.editTime);
        tvRead1 = findViewById(R.id.tvRead1);
        tvSet1 = findViewById(R.id.tvSet1);
        tvRead2 = findViewById(R.id.tvRead2);
        tvSet2 = findViewById(R.id.tvSet2);
        headerLayout.showTitle(getString(R.string.read_function_current_unbalance_threshold));
        headerLayout.showLeftBackButton();
        editTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (!StringUtil.isEmpty(str)) {
                    if (Integer.parseInt(str) > 65535)
                        s.delete(s.length() - 1, s.length());
                }
            }
        });
        editThreshold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (!StringUtil.isEmpty(str)) {
                    if (Float.parseFloat(str) < 299.0) {
                        if (str.contains(".")) {
                            int len = str.substring(str.indexOf(".") + 1).length();
                            if (len > 1) {
                                s.delete(s.length() - 1, s.length());
                            }
                        }
                    } else
                        s.delete(s.length() - 1, s.length());
                }
            }
        });
        tvRead1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readThreshold();
            }
        });
        tvSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editThreshold.getText().toString().length()>0)
                mvpPresenter.setThreshold(editThreshold.getText().toString());
            }
        });
        tvRead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readTime();
            }
        });
        tvSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTime.getText().toString().length()>0)
                mvpPresenter.setTime(editTime.getText().toString());
            }
        });
    }

    @Override
    public CurrentUnbalanceContact.Presenter createPresenter() {
        return new CurrentUnbalancePresenter(this);
    }

    @Override
    public void showData(TranXADRAssist item) {
        if (item.obis.equals("3#1-0:11.41.0.255#02")) {
            editTime.setText(item.value);
        } else {
            editThreshold.setText(item.value);
        }
    }
}
