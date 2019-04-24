package com.hx.set.view.bangladesh.set.HXE310_KP;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.model.GPRSBean;
import com.hx.base.util.InputFilterMinMax;
import com.hx.set.R;
import com.hx.set.contact.bangladesh.HXE310_KP.GPRSContact;
import com.hx.set.presenter.bangladesh.HXE310_KP.GPRSPresenter;

public class GPRSActivity extends RxMvpBaseActivity<GPRSContact.Presenter> implements GPRSContact.View {
    private HeaderLayout headerLayout;
    private EditText etApn;
    private EditText etIp1, etIp2, etIp3, etIp4, etPort;
    private EditText etPdpUsername, etPdpPassword;
    private TextView tvRead, tvSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_activity_gprs);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvRead = findViewById(R.id.tvRead);
        tvSet = findViewById(R.id.tvSet);
        etApn = findViewById(R.id.etApn);
        etIp1 = findViewById(R.id.etIp1);
        etIp2 = findViewById(R.id.etIp2);
        etIp3 = findViewById(R.id.etIp3);
        etIp4 = findViewById(R.id.etIp4);
        etPort = findViewById(R.id.etPort);
        etPdpUsername = findViewById(R.id.etPdpUsername);
        etPdpPassword = findViewById(R.id.etPdpPassword);

        headerLayout.showTitle(R.string.set_function_gprs);

        etIp1.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etIp2.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etIp3.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etIp4.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255", "3")});
        etPort.setFilters(new InputFilter[]{new InputFilterMinMax("0", "65535", "5")});
    }

    @Override
    public void initListener() {
        super.initListener();
        tvRead.setOnClickListener(this);
        tvSet.setOnClickListener(this);
        headerLayout.showLeftBackButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public GPRSContact.Presenter createPresenter() {
        return new GPRSPresenter(this);
    }

    @Override
    public void showData(GPRSBean gprsBean) {
        String[] str = gprsBean.getIp().split("\\.");
        etIp1.setText(str[0]);
        etIp2.setText(str[1]);
        etIp3.setText(str[2]);
        etIp4.setText(str[3]);
        etPort.setText(gprsBean.getPort());
        etApn.setText(gprsBean.getApn());
        etPdpUsername.setText(gprsBean.getPdpName());
        etPdpPassword.setText(gprsBean.getPdpPassword());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tvRead) {
            mvpPresenter.readGprs();
        } else if (view.getId() == R.id.tvSet) {
            GPRSBean bean = new GPRSBean();
            bean.setApn(etApn.getText().toString());
            bean.setIp(etIp1.getText().toString() + "." + etIp2.getText().toString() + "." + etIp3.getText().toString() + "." + etIp4.getText().toString());
            bean.setPort(etPort.getText().toString());
            bean.setPdpName(etPdpUsername.getText().toString());
            bean.setPdpPassword(etPdpPassword.getText().toString());
            mvpPresenter.writeGprs(bean);
        }
    }
}
