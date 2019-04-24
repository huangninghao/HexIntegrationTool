package com.hx.upgrade.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexCommonAdapter;
import com.hexing.libhexbase.adapter.HexViewHolder;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.ToastUtils;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.upgrade.R;

import java.util.ArrayList;
import java.util.List;

import com.hx.upgrade.contact.UpgradeContact;
import com.hx.upgrade.eventBus.UIRefresh;
import com.hx.upgrade.model.UpgradeFile;
import com.hx.upgrade.presenter.UpgradePresenter;
import com.hx.upgrade.util.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author niezhi
 * date 2018/11/19.
 * desc desc
 */
public class UpgradeActivity extends RxMvpBaseActivity<UpgradeContact.Presenter> implements UpgradeContact.View  {
    private TextView tvUpgrade,processBar,tvDesc;
    private HeaderLayout headerLayout;
    private FrameLayout layoutProgress;

    private HexCommonAdapter<UpgradeFile> adapter;
    List<UpgradeFile> data = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.up_activity_file);
    }

    @Override
    public UpgradeContact.Presenter createPresenter() {
        return new UpgradePresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        layoutProgress = findViewById(R.id.layoutProgress);
        tvDesc = findViewById(R.id.tvDesc);
        processBar = findViewById(R.id.processBar);
        listView = findViewById(R.id.viewData);
        tvUpgrade = findViewById(R.id.tvUpgrade);
    }
    @Override
    public void initData() {
        headerLayout.showTitle(R.string.upgrade_file);
        headerLayout.showLeftBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headerLayout.showRightImageButton(R.mipmap.icon_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(USelectFileActivity.class);
            }
        });

        adapter = new HexCommonAdapter<UpgradeFile>(this, data, R.layout.upgrade_file_listview) {
            @Override
            public void convert(HexViewHolder holder, UpgradeFile item) {
                holder.setText(R.id.tvName,item.getFile().getName());
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data = Constant.upgradeFiles;
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view){
        if (view.getId() == R.id.tvUpgrade){
            layoutProgress.setVisibility(View.VISIBLE);
            mvpPresenter.upgrade();
        }
    }
    @Override
    public void showData(String string) {

    }

    @Override
    public void initListener() {
        super.initListener();
        tvUpgrade.setOnClickListener(this);
    }

    @Override
    public void showToast(int resId) {
        super.showToast(resId);
        ToastUtils.showToast(this, resId);
    }
    @Override
    public void showProgress(final String progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                processBar.setText(progress);
                tvDesc.setText("");
            }
        });
    }
    @Override
    public void hideProgress() {
        layoutProgress.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshUI(UIRefresh uiRefresh) {
        if (!uiRefresh.isTag()){
            hideProgress();
        }
        if (!StringUtil.isEmpty(uiRefresh.getProgress())){
            showProgress(uiRefresh.getProgress());
        }
        if (!StringUtil.isEmpty(uiRefresh.getToast())){
            showToast(uiRefresh.getToast());
        }
    }
}
