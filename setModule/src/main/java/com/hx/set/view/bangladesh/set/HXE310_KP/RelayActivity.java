package com.hx.set.view.bangladesh.set.HXE310_KP;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.set.R;
import com.hx.set.contact.bangladesh.HXE310_KP.RelayContact;
import com.hx.set.presenter.bangladesh.HXE310_KP.RelayPresenter;

import cn.hexing.model.TranXADRAssist;

public class RelayActivity extends RxMvpBaseActivity<RelayContact.Presenter> implements RelayContact.View {

    private HeaderLayout headerLayout;
    private TextView tvStatus;
    private ImageView imgReload;
    private ImageView imgStatus;
    private TextView tvOpen;
    private TextView tvClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relay);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        tvStatus = findViewById(R.id.tvStatus);
        imgReload = findViewById(R.id.imgReload);
        imgStatus = findViewById(R.id.imgStatus);
        tvOpen = findViewById(R.id.tvOpen);
        tvClose = findViewById(R.id.tvClose);
        headerLayout.showTitle(getString(R.string.set_function_relay_status));
        headerLayout.showLeftBackButton();

        imgReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.reload();
            }
        });
        tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.openRelay();
            }
        });
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.closeRelay();
            }
        });
    }

    @Override
    public RelayContact.Presenter createPresenter() {
        return new RelayPresenter(this);
    }

    @Override
    public void showData(TranXADRAssist item) {
        if (item.value.equals("00")){
            imgStatus.setImageResource(R.mipmap.img_relay_open);
            tvStatus.setText(R.string.read_relay_open);
        }else  if (item.value.equals("01")){
            imgStatus.setImageResource(R.mipmap.img_relay_close);
            tvStatus.setText(R.string.read_relay_close);
        }
    }

    @Override
    public void showLoading() {
        LoadingDialog.showSysLoadingDialog(this,"",false);
    }
}
