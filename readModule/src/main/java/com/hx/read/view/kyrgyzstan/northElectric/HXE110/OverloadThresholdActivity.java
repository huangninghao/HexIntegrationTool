package com.hx.read.view.kyrgyzstan.northElectric.HXE110;

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
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.OverloadThresholdContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXE110.OverloadThresholdPresenter;

import cn.hexing.model.TranXADRAssist;

public class OverloadThresholdActivity extends RxMvpBaseActivity<OverloadThresholdContact.Presenter> implements OverloadThresholdContact.View {

    private HeaderLayout headerLayout;
    private EditText editThreshold;
    private EditText editTime;
    private EditText edit;
    private TextView tvRead1;
    private TextView tvSet1;
    private TextView tvRead2;
    private TextView tvSet2;
    private TextView tvRead3;
    private TextView tvSet3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overload_threshold);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        editThreshold = findViewById(R.id.editThreshold);
        editTime = findViewById(R.id.editTime);
        edit = findViewById(R.id.edit);
        tvRead1 = findViewById(R.id.tvRead1);
        tvSet1 = findViewById(R.id.tvSet1);
        tvRead2 = findViewById(R.id.tvRead2);
        tvSet2 = findViewById(R.id.tvSet2);
        tvRead3 = findViewById(R.id.tvRead3);
        tvSet3 = findViewById(R.id.tvSet3);
        headerLayout.showTitle(getString(R.string.read_function_overload));
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
                    if (str.contains(".")) {
                        int len = str.substring(str.indexOf(".") + 1).length();
                        if (len > 1) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
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
                    if (str.contains(".")) {
                        int len = str.substring(str.indexOf(".") + 1).length();
                        if (len > 1) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
                }
            }
        });
        edit.addTextChangedListener(new TextWatcher() {
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
                    if (str.contains(".")) {
                        int len = str.substring(str.indexOf(".") + 1).length();
                        if (len > 1) {
                            s.delete(s.length() - 1, s.length());
                        }
                    }
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
                if (editThreshold.getText().toString().length()>0) {
                    mvpPresenter.setThreshold(editThreshold.getText().toString());
                }
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
                if (editTime.getText().toString().length()>0) {
                    mvpPresenter.setTime(editTime.getText().toString());
                }

            }
        });
        tvRead3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.read();
            }
        });
        tvSet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().length()>0) {
                    mvpPresenter.set(edit.getText().toString());
                }
            }
        });
    }

    @Override
    public OverloadThresholdContact.Presenter createPresenter() {
        return new OverloadThresholdPresenter(this);
    }

    @Override
    public void showData(TranXADRAssist item) {
        if (item.obis.equals("71#0-0:17.0.0.255#03")) {
            editThreshold.setText(item.value);
        } else if (item.obis.equals("71#0-0:17.0.0.255#04")) {
            editTime.setText(item.value);
        } else {
            edit.setText(item.value);
        }
    }
}
