package com.hx.upgrade.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;

import java.util.List;

import com.hx.upgrade.model.UpgradeFile;

/**
 * @author by HEN022
 * on 2018/11/19.
 */
public interface USelectFileContact {
    interface Presenter extends RxBasePresenter {
        void getAllFile();
    }

    interface View extends HexBaseView {

        void showData(List<UpgradeFile> upgradeFileList);
    }
}
