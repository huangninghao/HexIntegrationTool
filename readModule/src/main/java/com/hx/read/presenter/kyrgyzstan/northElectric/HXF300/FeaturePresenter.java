package com.hx.read.presenter.kyrgyzstan.northElectric.HXF300;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.FeaturesMenuBean;
import com.hx.read.R;
import com.hx.read.contact.kyrgyzstan.northElectric.HXF300.FeaturesContact;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.BypassThresholdActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.CTPTActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.CurrentUnbalanceActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.GPRSActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.GPRSModularActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.LowPowerThesholdActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.MeterParameterActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.OverVoltageActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.OverloadThresholdActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.PLCActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.UnderVoltageActivity;
import com.hx.read.view.kyrgyzstan.northElectric.HXF300.VoltageUnbalanceActivity;

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
        item.setCls(com.hx.read.view.kyrgyzstan.northElectric.HXF300.InstantaneousActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_daily);
        item.setSort(2);
        item.imgResId = R.mipmap.img_day_freeze;
        item.setCls(com.hx.read.view.kyrgyzstan.northElectric.HXF300.DayFreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_monthly);
        item.setSort(3);
        item.imgResId = R.mipmap.img_month_freeze;
        item.setCls(com.hx.read.view.kyrgyzstan.northElectric.HXF300.MonthFreezeActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_time_set);
        item.setSort(4);
        item.imgResId = R.mipmap.img_time_set;
        item.setCls(com.hx.read.view.kyrgyzstan.northElectric.HXF300.TimeSetActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_relay_status);
        item.setSort(5);
        item.imgResId = R.mipmap.img_relay;
        item.setCls(com.hx.read.view.kyrgyzstan.northElectric.HXE110.RelayActivity.class);
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
        item.setResourceIdName(R.string.read_function_current_unbalance_threshold);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_current_unbalance;
        item.setCls(CurrentUnbalanceActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_voltage_unbalance_threshold);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_voltage_unbalance;
        item.setCls(VoltageUnbalanceActivity.class);
        dataList.add(item);

        item = new FeaturesMenuBean();
        item.setResourceIdName(R.string.read_function_ctpt);
        item.setSort(5);
        item.imgResId = R.mipmap.img_func_ctpt;
        item.setCls(CTPTActivity.class);
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
