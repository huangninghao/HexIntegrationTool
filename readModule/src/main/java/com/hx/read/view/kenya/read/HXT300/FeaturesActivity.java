package com.hx.read.view.kenya.read.HXT300;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cbl.dialog.StyledDialog;
import com.cbl.dialog.config.ConfigBean;
import com.cbl.dialog.interfaces.DialogListener;
import com.cblong.xrecyclerview.XRecyclerView;
import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexRVBaseAdapter;
import com.hexing.libhexbase.adapter.HexRVBaseViewHolder;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hx.base.BaseConstant;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.kenya.HXT300.FeaturesContact;
import com.hx.read.presenter.kenya.HXT300.FeaturePresenter;
import com.hx.read.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 几内亚 功能目录
 * HEN022 on 2018/3/5.
 */
@Route(path = "/KENYA/" + BaseConstant.APP_READ + "/HXT300")
public class FeaturesActivity extends RxMvpBaseActivity<FeaturesContact.Presenter> implements FeaturesContact.View {
    private HeaderLayout headerLayout;
    private XRecyclerView listView;
    private String meterNumber = "";
    private HexRVBaseAdapter<FeaturesMenuBean> adapter;
    private List<FeaturesMenuBean> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        adapter = new HexRVBaseAdapter<FeaturesMenuBean>(this, menuList, R.layout.item_function_list) {
            @Override
            protected void convert(HexRVBaseViewHolder holder, FeaturesMenuBean item) {
                holder.setText(R.id.tvItemTitle, getString(item.getResourceIdName()));
                holder.setImageUrl(R.id.imgItem, item.imgResId);
            }
        };
        adapter.setOnItemClickListener(new HexRVBaseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                FeaturesMenuBean item = menuList.get(position);
                toActivity(item.getCls());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(FeaturesActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.setPullRefreshEnabled(false);
        listView.setNoMore(true);
        mvpPresenter.getShowList();
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.read_function_title));
        headerLayout.showLeftBackButton();
        listView = findViewById(R.id.recyclerView);
        final ConfigBean configBean = StyledDialog.buildMdInput(getString(R.string.please_input_meter), getString(R.string.meter_hint), "", new DialogListener() {
            @Override
            public void onFirst() {
                if (TextUtils.isEmpty(meterNumber)) {
                    finish();
                }
            }

            @Override
            public void onSecond() {
                HexLog.e("dialog 回调 onSecond");
                finish();
            }

            @Override
            public void onGetInput(CharSequence input1, CharSequence input2) {
                meterNumber = input1.toString();
                StringCache.put(Constant.METER_NUMBER, meterNumber);
            }
        });
        configBean.show();
    }

    @Override
    public FeaturesContact.Presenter createPresenter() {
        return new FeaturePresenter(this);
    }

    @Override
    public void showData(List<FeaturesMenuBean> list) {
        menuList = list;
        adapter.setData(menuList);
        adapter.notifyDataSetChanged();
    }
}

