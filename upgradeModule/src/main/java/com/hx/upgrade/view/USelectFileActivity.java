package com.hx.upgrade.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hexing.libhexbase.activity.RxMvpBaseActivity;
import com.hexing.libhexbase.adapter.HexCommonAdapter;
import com.hexing.libhexbase.adapter.HexViewHolder;
import com.hexing.libhexbase.application.HexApplication;
import com.hexing.libhexbase.view.HeaderLayout;
import com.hexing.libhexbase.view.LoadingDialog;
import com.hx.upgrade.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.hx.upgrade.contact.USelectFileContact;
import com.hx.upgrade.model.UpgradeFile;
import com.hx.upgrade.presenter.USelectFilePresenter;
import com.hx.upgrade.util.Constant;

public class USelectFileActivity extends RxMvpBaseActivity<USelectFileContact.Presenter> implements USelectFileContact.View {
    private HexCommonAdapter<UpgradeFile> adapter;
    List<UpgradeFile> data = new ArrayList<>();

    boolean selectAll = false;
    private ImageView ivAll;
    private ListView listView;
    private HeaderLayout headerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.up_activity_select_file);
    }

    @Override
    public USelectFileContact.Presenter createPresenter() {
        return new USelectFilePresenter(this);
    }

    @Override
    public void initView() {
        super.initView();
        headerLayout = findViewById(R.id.headerLayout);
        listView = findViewById(R.id.viewData);
        ivAll = findViewById(R.id.ivAll);
    }

    @Override
    public void initListener() {
        ivAll.setOnClickListener(this);
    }

    @Override
    public void initData() {
        headerLayout.showTitle(R.string.upgrade_select_file);
        headerLayout.showLeftBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<UpgradeFile> upgradeFiles = new ArrayList<>();
                if (data != null) {
                    for (UpgradeFile file : data) {
                        if (file.isSelect()) {
                            upgradeFiles.add(file);
                        }
                    }
                    Constant.upgradeFiles = upgradeFiles;
                } else {
                    Constant.upgradeFiles.clear();
                }
                finish();
            }
        });
        adapter = new HexCommonAdapter<UpgradeFile>(this, data, R.layout.select_file_listview) {
            @Override
            public void convert(final HexViewHolder holder, final UpgradeFile item) {
                holder.setText(R.id.tvName, item.getFile().getName());
                if (item.isSelect()) {
                    holder.setImageResource(R.id.ivSelect, R.mipmap.icon_select_file);
                } else {
                    holder.setImageResource(R.id.ivSelect, R.mipmap.icon_un_select_file);
                }
                holder.getView(R.id.ivSelect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageView imageView = (ImageView) v;
                        if (!item.isSelect()) {
                            imageView.setImageResource(R.mipmap.icon_select_file);
                        } else {
                            imageView.setImageResource(R.mipmap.icon_un_select_file);
                        }
                        item.setSelect(!item.isSelect());
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        mvpPresenter.getAllFile();
    }

    @Override
    public void showData(List<UpgradeFile> upgradeFileList) {
        /*去除已选中的文件*/
//        List<UpgradeFile> upgradeFiles = Constant.upgradeFiles;
//        if (upgradeFiles != null) {
//            if (upgradeFiles.size() == 0) {
//                data.addAll(upgradeFileList);
//            } else {
//                for (UpgradeFile upgradeFile : upgradeFileList) {
//                    boolean tag = false;//已选入标识符
//                    for (UpgradeFile file : upgradeFiles) {
//                        if (upgradeFile.getFile().getName().equals(file.getFile().getName())) {
//                            tag = true;
//                            break;
//                        }
//                    }
//                    if (!tag) {
//                        data.add(upgradeFile);
//                    }
//                }
//            }
//        }
        data = upgradeFileList;
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ivAll) {
            ImageView imageView = (ImageView) view;
            if (!selectAll) {
                imageView.setImageResource(R.mipmap.icon_select_file);
            } else {
                imageView.setImageResource(R.mipmap.icon_un_select_file);
            }
            if (data != null) {
                for (UpgradeFile file : data) {
                    file.setSelect(!selectAll);
                }
            }
            selectAll = !selectAll;
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
        LoadingDialog.showSysLoadingDialog(this, msg);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        LoadingDialog.cancelLoadingDialog();
    }
}
