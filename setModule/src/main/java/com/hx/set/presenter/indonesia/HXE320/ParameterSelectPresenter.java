package com.hx.set.presenter.indonesia.HXE320;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hx.base.model.DisplayMeterBean;
import com.hx.base.model.FreezeMeterBean;
import com.hx.set.contact.indonesia.HXE320.ParameterSelectContact;
import com.hx.set.model.ParameterItem;

import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexStringUtil;

import static com.hx.base.mInterface.module.baseModule.BaseService.getIndonesiaDisplayJson;
import static com.hx.base.mInterface.module.baseModule.BaseService.getIndonesiaJson;

public class ParameterSelectPresenter extends RxBasePresenterImpl<ParameterSelectContact.View> implements ParameterSelectContact.Presenter {

    private List<FreezeMeterBean> lst_FreezeMeterBean;
    private List<DisplayMeterBean> lst_DisplayMeterBean;
    public ParameterSelectPresenter(ParameterSelectContact.View view) {
        super(view);


    }

    private List<ParameterItem> list = new ArrayList<>();

    @Override
    public void getShowList() {
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
//        list.add(new ParameterItem("正向有功",false));
        getView().showData(list);
    }

    @Override
    public void setReadData(List<ParameterItem> parameterItemList, int type) {
        if (type == 0) {
            lst_FreezeMeterBean = getIndonesiaJson();

            list.clear();
            int index = parameterItemList.size();
            for (int i = 0; i < parameterItemList.size(); i++) {

                list.add(parameterItemList.get(i));
            }


            for (int i = 0; i < lst_FreezeMeterBean.size(); i++) {
                boolean tag = true;
                for (int j = 0; j < parameterItemList.size(); j++) {
                    if (parameterItemList.get(j).obis.equals(lst_FreezeMeterBean.get(i).getPROTOCOL_OBIS())) {
                        tag = false;
                        break;
                    }

                }
                if (tag) {
                    list.add(new ParameterItem(lst_FreezeMeterBean.get(i).getEN_NAME(), false, index++, lst_FreezeMeterBean.get(i).getPROTOCOL_OBIS(), lst_FreezeMeterBean.get(i).getCLASS_ID(), lst_FreezeMeterBean.get(i).getATTRIBUTE()));
                }
            }
        }
        else
        {
            lst_DisplayMeterBean = getIndonesiaDisplayJson();

            list.clear();
            int index = parameterItemList.size();
            for (int i = 0; i < parameterItemList.size(); i++) {

                list.add(parameterItemList.get(i));
            }


            for (int i = 0; i < lst_DisplayMeterBean.size(); i++) {
                boolean tag = true;
                for (int j = 0; j < parameterItemList.size(); j++) {
                    if (parameterItemList.get(j).obis.replace(" ","").equals(lst_DisplayMeterBean.get(i).getPROTOCOL_OBIS())) {
                        tag = false;
                        break;
                    }

                }
                if (tag) {
                    list.add(new ParameterItem(lst_DisplayMeterBean.get(i).getEN_NAME(), false, index++, HexStringUtil.padRight( lst_DisplayMeterBean.get(i).getPROTOCOL_OBIS(),11,' '), Integer.valueOf(lst_DisplayMeterBean.get(i).getPROTOCOL_ID(),16).toString() , lst_DisplayMeterBean.get(i).getPROTOCOL_OBIS()));
                }
            }

        }

    }
}
