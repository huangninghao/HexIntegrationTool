package com.hx.read.presenter.kyrgyzstan.northElectric.HXE110;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXE110.FeaturesContact;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.BypassThresholdActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.CurrentUnbalanceActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.DayFreezeActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.GPRSActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.GPRSModularActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.InstantaneousActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.LowPowerThesholdActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.MeterParameterActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.MonthFreezeActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.OverVoltageActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.OverloadThresholdActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.PLCActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.RelayActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.TimeSetActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.UnderVoltageActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXE110.VoltageUnbalanceActivity;

import java.util.ArrayList;
import java.util.List;

public class FeaturePresenter extends RxBasePresenterImpl<FeaturesContact.View> implements FeaturesContact.Presenter{
    public FeaturePresenter(FeaturesContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {
        List<FeaturesMenuBean> dataList = new ArrayList<>();
        FeaturesMenuBean item = new FeaturesMenuBean();
        item.imgResId = R.mipmap.img_instanteous_amount;
        item.setResourceIdName(R.string.read_function_instantaneous_amount);
        item.setSort(1);
        item.setCls(InstantaneousActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_daily);
        item.setSort(2);
        item.imgResId = R.mipmap.img_day_freeze;
        item.setCls(DayFreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_monthly);
        item.setSort(3);
        item.imgResId = R.mipmap.img_month_freeze;
        item.setCls(MonthFreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_time_set);
        item.setSort(4);
        item.imgResId = R.mipmap.img_time_set;
        item.setCls(TimeSetActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_relay_status);
        item.setSort(5);
        item.imgResId = R.mipmap.img_relay;
        item.setCls(RelayActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.meter_parameter);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_meter_param;
        item.setCls(MeterParameterActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_plc);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_plc;
        item.setCls(PLCActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_gprs_modular);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_gprs_modular;
        item.setCls(GPRSModularActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_undervoltage);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_under_voltage;
        item.setCls(UnderVoltageActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_overvoltage);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_over_voltage;
        item.setCls(OverVoltageActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_bypass);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_bypass;
        item.setCls(BypassThresholdActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_overload);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_overload;
        item.setCls(OverloadThresholdActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_lowpower);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_lowpowe;
        item.setCls(LowPowerThesholdActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_gprs);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_gprs_param;
        item.setCls(GPRSActivity.class);
        dataList.add(item);


        getView().showData(dataList);
    }
}
