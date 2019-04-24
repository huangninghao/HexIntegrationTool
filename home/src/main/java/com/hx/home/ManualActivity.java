package com.hx.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.tools.PackageUtils;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.model.HomeMenu;
import com.hx.home.contact.ManualContract;

import java.util.List;

public class ManualActivity extends RxMvpBaseActivity<ManualContract.Presenter> implements ManualContract.View {

    private HeaderLayout headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
    }

    @Override
    public void initData() {
        super.initData();
        headerLayout.showTitle(R.string.home_instruction_manual);
        headerLayout.showLeftBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public ManualContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public void showList(List<HomeMenu> menus) {

    }
}
