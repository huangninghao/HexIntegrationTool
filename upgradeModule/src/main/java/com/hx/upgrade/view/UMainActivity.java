package com.hx.upgrade.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hexing.libhexbase.activity.HeaderBaseActivity;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.mInterface.module.home.HomeService;
import com.hx.base.mInterface.provider.IUpgradeProvider;
import com.hx.base.model.DeviceBean;
import com.hx.base.model.UserInfoEntity;
import com.hx.upgrade.R;

import java.util.List;

import com.hx.upgrade.model.CommBean;

/**
 * @author caibinglong
 * date 2018/11/6.
 * desc desc
 */


public class UMainActivity extends HeaderBaseActivity {
    private HeaderLayout headerLayout;
    private LinearLayout layoutDis, layoutLocal;
    private TextView tvDis, tvLocal;
    private TextView btnNext;
    private ImageView ivLocal;
    private ImageView ivDis;
    private Spinner spArg;
    private Spinner spType;

    private int upType = 0; //默认勾选了本地
    private int commType = 0; //0 = 光电，1 = RF
    private int commArg = 0; // 0 = 21协议，1 = DLMS协议
    private CommBean commBean = new CommBean();

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        layoutDis = findViewById(R.id.layoutDis);
        layoutLocal = findViewById(R.id.layoutLocal);
        btnNext = findViewById(R.id.btnNext);
        tvDis = findViewById(R.id.tvLocal);
        tvLocal = findViewById(R.id.tvDis);
        ivDis = findViewById(R.id.ivDis);
        ivLocal = findViewById(R.id.ivLocal);
        spArg = findViewById(R.id.spAgr);
        spType = findViewById(R.id.spType);
    }

    @Override
    public void initData() {
        commBean = StringCache.getJavaBean("commBean");
        headerLayout.showTitle(R.string.upgrade);
        headerLayout.showLeftBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spType.setEnabled(false);
        spArg.setEnabled(false);
        spType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                commType = position;
            }

            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spArg.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                commArg = position;
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (commBean != null) { //缓存中已经保存通讯配置，默认使用缓存中的通讯配置
            upType = commBean.getUpgradeType();
            commType = commBean.getCommType();
            commArg = commBean.getCommAgr();
            //修改控件显示缓存中的通讯配置
            if (commBean.getUpgradeType() == 0) {
                ivDis.setImageResource(R.mipmap.icon_check_un_sel);
                ivLocal.setImageResource(R.mipmap.icon_check_sel);
            } else {
                ivLocal.setImageResource(R.mipmap.icon_check_un_sel);
                ivDis.setImageResource(R.mipmap.icon_check_sel);
            }
            spArg.setSelection(commBean.getCommAgr());
            spType.setSelection(commBean.getCommType());
        }
    }

    @Override
    public void initListener() {
        layoutLocal.setOnClickListener(this);
        layoutDis.setOnClickListener(this);
        ivLocal.setOnClickListener(this);
        tvLocal.setOnClickListener(this);
        tvDis.setOnClickListener(this);
        ivDis.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_activity_main);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivLocal || view.getId() == R.id.tvLocal || view.getId() == R.id.layoutLocal) {
            if (upType == 1) {   //当前勾选了本地
                upType = 0;
                ivDis.setImageResource(R.mipmap.icon_check_un_sel);
                ivLocal.setImageResource(R.mipmap.icon_check_sel);
            }
        } else if (view.getId() == R.id.ivDis || view.getId() == R.id.tvDis || view.getId() == R.id.layoutDis) {
            if (upType == 0) {   //当前勾选了远程
                upType = 1;
                ivLocal.setImageResource(R.mipmap.icon_check_un_sel);
                ivDis.setImageResource(R.mipmap.icon_check_sel);
            }
        } else if (view.getId() == R.id.btnNext) {
            if (commArg != 1 || commType != 0) { //孟加拉单三相表提测限制选中默认配置
                showToast(R.string.default_config);
            } else {
                if (commType == 1) { //RF 通讯方式，要先输入表号
                    showInputDialog();
                } else {
                    commBean.setCommAgr(commArg);
                    commBean.setCommType(commType);
                    commBean.setUpgradeType(upType);
                    StringCache.putJavaBean("commBean", commBean);
                    toActivity(UpgradeActivity.class);
                }
            }
        }

    }

    private void showInputDialog() {
        /*@setView 装入一个EditView
         */
        final EditText editText = new EditText(UMainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(UMainActivity.this);
        inputDialog.setTitle(this.getString(R.string.upgrade_meter_number)).setView(editText);
        inputDialog.setPositiveButton(this.getString(R.string.upgrade_sure),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toActivity(UpgradeActivity.class);
                    }
                }).show();
    }
}
