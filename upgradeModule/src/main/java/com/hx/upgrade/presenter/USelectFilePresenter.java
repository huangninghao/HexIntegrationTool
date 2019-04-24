package com.hx.upgrade.presenter;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hx.base.BaseApplication;
import com.hx.upgrade.contact.USelectFileContact;
import com.hx.upgrade.model.UpgradeFile;
/**
 * @author by HEN022
 * on 2018/11/19.
 */
public class USelectFilePresenter extends RxBasePresenterImpl<USelectFileContact.View> implements USelectFileContact.Presenter {

    public USelectFilePresenter(USelectFileContact.View view) {
        super(view);
    }

    @Override
    public void getAllFile() {
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                final List<UpgradeFile> upgradeFiles = new ArrayList<>();
                String path = BaseApplication.FILEPATH_RECORD;//文件路径
                File file = new File(path);
                File[] files = file.listFiles();
                if (null != files){
                    for (File fi : files){

                            UpgradeFile upgradeFile = new UpgradeFile();
                            upgradeFile.setFile(fi);
                            upgradeFiles.add(upgradeFile);

                    }
                }else{
                    UpgradeFile upgradeFile = new UpgradeFile();
                    upgradeFile.setFile(file);
                    upgradeFiles.add(upgradeFile);
                }
                HexThreadManager.runTaskOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().showData(upgradeFiles);
                    }
                });
            }
        });
    }
}
