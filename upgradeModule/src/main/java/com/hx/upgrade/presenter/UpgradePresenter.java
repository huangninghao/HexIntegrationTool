package com.hx.upgrade.presenter;


import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;

import com.hx.upgrade.R;
import com.hx.upgrade.business.Local_21_OPTBusiness;
import com.hx.upgrade.business.Local_DLMS_OPTBusiness;
import com.hx.upgrade.business.Remote_DLMS_OPT_Business;
import com.hx.upgrade.contact.UpgradeContact;
import com.hx.upgrade.model.CommBean;
import com.hx.upgrade.util.Constant;

/**
 * @author by HEN022
 * on 2018/11/19.
 */
public class UpgradePresenter extends RxBasePresenterImpl<UpgradeContact.View> implements UpgradeContact.Presenter {
    public static String beginStr, endStr; //Bootloader区开始结束地址 （根据设备芯片MCU决定）
    public UpgradePresenter(UpgradeContact.View view) {
        super(view);
    }

    @Override
    public void upgrade() {
        if (Constant.upgradeFiles == null || Constant.upgradeFiles.size() == 0) {
            if (getView() != null) {
                getView().hideProgress();
                getView().showToast(R.string.tips_select_file);
                return;
            }
        }
        CommBean commBean = StringCache.getJavaBean("commBean");
        int MCU = commBean.getMeterMCU();
        if (MCU == 0) {  //TDK 芯片
            beginStr = ":207800"; // boot区起始地址
            endStr = ":208000";// boot区起始地址
        } else { //RN8213 芯片
            beginStr = ":200000";
            endStr = ":202000";
        }
        if (commBean.getUpgradeType() == 0) { //本地
            if (commBean.getCommAgr() == 1) { //DLMS协议
                if (commBean.getCommType() == 0) { //光电
                    Local_DLMS_OPTBusiness local_dlms_optBusiness = new Local_DLMS_OPTBusiness();
                    local_dlms_optBusiness.upgrade();
                }
            }else if(commBean.getCommAgr() == 2){ //21 协议
                if (commBean.getCommType() == 0) { //光电
                    Local_21_OPTBusiness local_21_optBusiness = new Local_21_OPTBusiness();
                    local_21_optBusiness.upgrade();
                }
            }
        }else{ // 远程
            if (commBean.getCommAgr() == 1){ //DLMS
                if (commBean.getCommType() == 0) {
                    Remote_DLMS_OPT_Business remote_dlms_opt_business = new Remote_DLMS_OPT_Business();
                    remote_dlms_opt_business.upgrade();
                }
            }
        }
    }
}
