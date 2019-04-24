package com.hx.read.contact.bangladesh;

import android.support.annotation.StringRes;

import com.hexing.libhexbase.inter.HexBaseView;
import com.hexing.libhexbase.inter.RxBasePresenter;
import com.hx.base.model.FeaturesMenuBean;

import java.util.List;

import cn.hexing.model.TranXADRAssist;

/**
 * Created by HEN022
 * on 2018/10/22.
 */

public interface HXE310KPContact {

    interface Presenter extends RxBasePresenter {

        List<TranXADRAssist> getTranXAD();

        /**
         * 获取日冻结
         *
         * @param startTime 格式为 20170102
         * @param endTime   20170103
         */
        void getDayFreeze(String startTime, String endTime, int type);

        /**
         * 获取 月冻结数据
         *
         * @param startTime 格式为  201801
         * @param endTime   格式为  201803
         */
        void getMonthFreeze(String startTime, String endTime, int type);

        /**
         * 读取时间
         */
        void readTime();

        /**
         * 读表号
         */
        void readMeterNumber();



        /**
         * 导出冻结数据
         */
        boolean exportFreezeData(List<TranXADRAssist> obisListData, String meterNo);

        /**
         * 设置时间
         *
         * @param year   年
         * @param month  月
         * @param day    日
         * @param hour   小时
         * @param minute 分
         * @param second 秒
         */
        void setTime(int year, int month, int day, int hour, int minute, int second);



        /**
         * 获取 功能菜单列表
         *
         * @return list
         */
        List<FeaturesMenuBean> getFeaturesList();
    }

    interface View extends HexBaseView {
        /**
         * 显示 loading
         *
         * @param progress 进度
         */
        void showProgress(float progress);

        /**
         * 隐藏loading
         */
        void hideProgress();

        /**
         * 展示数据
         *
         * @param object o
         */
        void showData(Object object);

        void showInitData(List<TranXADRAssist> dataList);

        /**
         * 显示 toast
         *
         * @param resourceId 资源id
         */
        void showMessage(@StringRes int resourceId);
    }
}
