package com.hx.set.view.indonesia.set.HXE320;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.set.R;
import com.hx.set.contact.indonesia.HXE320.DisplayIntervalContact;
import com.hx.set.presenter.indonesia.HXE320.DisplayIntervalPresenter;

public class DisplayIntervalActivity extends RxMvpBaseActivity<DisplayIntervalContact.Presenter> implements DisplayIntervalContact.View {

    private HeaderLayout headerLayout;
    private TextView tvReade;
    private TextView tvSet;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_interval);
        tvReade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.read();
            }
        });
        tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpPresenter.set(et.getText().toString());
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_display_interval));
        headerLayout.showLeftBackButton();
        tvReade = findViewById(R.id.tvRead);
        tvSet = findViewById(R.id.tvNext);
        et = findViewById(R.id.et_interval);
    }

    @Override
    public DisplayIntervalContact.Presenter createPresenter() {
        return new DisplayIntervalPresenter(this);
    }
    @Override
    public  void show (String interval) {
        et.setText(interval);
    }
}
