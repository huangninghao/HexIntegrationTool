package com.hx.read.view.kyrgyzstan.northElectric.HXE310;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.util.InputFilterMinMax;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE310.GPRSContact;
import com.hx.read.presenter.kyrgyzstan.northElectric.HXE310.GPRSPresenter;

import cn.hexing.model.TranXADRAssist;

public class GPRSActivity extends RxMvpBaseActivity<GPRSContact.Presenter> implements GPRSContact.View {

    private HeaderLayout headerLayout;
    private TextView tvRead;
    private TextView tvSet;
    private EditText edit2;
    private EditText etip1, etip2, etip3, etip4;
    private EditText edit5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gprs);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        edit2 = findViewById(R.id.edit2);
        edit5 = findViewById(R.id.edit5);
        etip1 = findViewById(R.id.etip1);
        etip2 = findViewById(R.id.etip2);
        etip3 = findViewById(R.id.etip3);
        etip4 = findViewById(R.id.etip4);
        etip1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etip2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etip3.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etip4.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        edit2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "65535", "5")});
        tvRead = findViewById(R.id.tvRead);
        tvSet = findViewById(R.id.tvSet);
        headerLayout.showTitle(getString(R.string.read_function_gprs));
        headerLayout.showLeftBackButton();
        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip1 = etip1.getText().toString();
                String ip2 = etip2.getText().toString();
                String ip3 = etip3.getText().toString();
                String ip4 = etip4.getText().toString();
                String port = edit2.getText().toString();
                if (StringUtil.isEmpty(port) || StringUtil.isEmpty(ip1) || StringUtil.isEmpty(ip2) || StringUtil.isEmpty(ip3) || StringUtil.isEmpty(ip4)) {
                    showToast(R.string.invalid_data);
                    return;
                }
                String ip = ip1 + "." + ip2 + "." + ip3 + "." + ip4;
                mvpPresenter.setGPRS(ip, port, edit5.getText().toString());
            }
        });
        tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.readGPRS();
            }
        });
    }

    @Override
    public GPRSContact.Presenter createPresenter() {
        return new GPRSPresenter(this);
    }

    @Override
    public void showData(TranXADRAssist assist) {
        if (assist.structList.size() > 0) {
            for (TranXADRAssist.StructBean structBean : assist.structList) {
                if (structBean.name.equals("ip")) {
                    String[] item = structBean.value.split("\\.");
                    etip1.setText(item[0]);
                    etip2.setText(item[1]);
                    etip3.setText(item[2]);
                    etip4.setText(item[3]);
                } else if (structBean.name.equals("port")) {
                    edit2.setText(structBean.value);
                } else {
                    hideLoading();
                    edit5.setText(structBean.value);
                }
            }
        }
    }
}
