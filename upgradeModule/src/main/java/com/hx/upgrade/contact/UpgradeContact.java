package com.hx.upgrade.contact;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.upgrade.model.UpgradeFile;

import java.util.List;

/**
 * @author by HEN022
 * on 2018/11/19.
 */
public interface UpgradeContact {
    interface Presenter extends RxBasePresenter {
        void upgrade();
    }

    interface View extends HexBaseView {
        void showProgress(String progress);
        void hideProgress();
        void showData(String string);
    }
}
