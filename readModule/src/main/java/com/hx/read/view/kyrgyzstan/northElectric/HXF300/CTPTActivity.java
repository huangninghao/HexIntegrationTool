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
import com.hx.read.contact.kyrgyzstan.northElectric.HXF300.CTPTContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXF300.CTPTPresenter;

import java.text.DecimalFormat;

import cn.hexing.model.TranXADRAssist;

public class CTPTActivity extends RxMvpBaseActivity<CTPTContact.Presenter> implements CTPTContact.View {

    private HeaderLayout headerLayout;
    private EditText edit1, edit2, edit3, edit4;
    private TextView tvTotal1, tvTotal2;
    private TextView tvRead1;
    private TextView tvSet1;
    private TextView tvRead2;
    private TextView tvSet2;

    private String ct1, ct2, pt1, pt2;

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            ct1 = edit1.getText().toString();
            ct2 = edit2.getText().toString();
            pt1 = edit3.getText().toString();
            pt2 = edit4.getText().toString();
            double result;
            DecimalFormat df = new DecimalFormat("0.0000");//保留4位小数
            if (!StringUtil.isEmpty(ct1) && !StringUtil.isEmpty(ct2)) {
                result = (Integer.parseInt(ct1) * 1.0 / Integer.parseInt(ct2));
                String string = df.format(result);
                tvTotal1.setText(string);
            }
            if (!StringUtil.isEmpty(pt1) && !StringUtil.isEmpty(pt2)) {
                result = (Integer.parseInt(pt1) * 1.0 / Integer.parseInt(pt2));
                String string = df.format(result);
                tvTotal2.setText(string);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctpt);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);
        edit4 = findViewById(R.id.edit4);
        tvTotal1 = findViewById(R.id.tvTotal1);
        tvTotal2 = findViewById(R.id.tvTotal2);
        tvRead1 = findViewById(R.id.tvRead1);
        tvSet1 = findViewById(R.id.tvSet1);
        tvRead2 = findViewById(R.id.tvRead2);
        tvSet2 = findViewById(R.id.tvSet2);
        headerLayout.showTitle(getString(R.string.read_function_ctpt));
        headerLayout.showLeftBackButton();

        edit1.addTextChangedListener(textWatcher);
        edit2.addTextChangedListener(textWatcher);
        edit3.addTextChangedListener(textWatcher);
        edit4.addTextChangedListener(textWatcher);
        tvRead1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readCT();
            }
        });
        tvSet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.setCT(ct1, ct2);
            }
        });
        tvRead2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readPT();
            }
        });
        tvSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.setPT(pt1, pt2);
            }
        });
    }

    @Override
    public CTPTContact.Presenter createPresenter() {
        return new CTPTPresenter(this);
    }

    @Override
    public void showData(TranXADRAssist item) {
        if (item.obis.equals("01#1-0:0.4.2.255#02")) {
            ct1 = item.value;
            edit1.setText(ct1);
        } else if (item.obis.equals("01#1-0:0.4.5.255#02")) {
            ct2 = item.value;
            edit2.setText(ct2);
            double result = (Integer.parseInt(ct1) * 1.0 / Integer.parseInt(ct2));
            DecimalFormat df = new DecimalFormat("0.0000");//保留4位小数
            String s = df.format(result);
            tvTotal1.setText(s);
        } else if (item.obis.equals("01#1-0:0.4.3.255#02")) {
            pt1 = item.value;
            edit3.setText(pt1);
        } else if (item.obis.equals("01#1-0:0.4.6.255#02")) {
            pt2 = item.value;
            edit4.setText(pt2);
            double result = (Integer.parseInt(pt1) * 1.0 / Integer.parseInt(pt2));
            DecimalFormat df = new DecimalFormat("0.0000");//保留4位小数
            String s = df.format(result);
            tvTotal2.setText(s);
        }
    }
}
