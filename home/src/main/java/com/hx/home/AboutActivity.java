package com.hx.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hexing.libhexbase.activity.HexBaseActivity;
import com.hexing.libhexbase.tools.PackageUtils;
import com.hexing.libhexbase.view.HeaderLayout;

/**
 * @author caibinglong
 * date 2018/7/26.
 * desc desc
 */

public class AboutActivity extends HexBaseActivity {

    private HeaderLayout headerAbout;
    private TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initView() {
        super.initView();
        tvVersion = findViewById(R.id.tvVersion);
        headerAbout = findViewById(R.id.headerLayout);
    }

    @Override
    public void initData() {
        super.initData();
        headerAbout.showTitle(R.string.home_menu_about);
        headerAbout.showLeftBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvVersion.setText(getString(R.string.app_version, PackageUtils.getVersionName(HomeApplication.getInstance())));
    }
}
