package com.hx.read.presenter.bangladesh.HXE310;

import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.base.model.DisplayMeterBean;
import com.hx.read.contact.bangladesh.HXE310.ParameterSelectContact;
import com.hx.read.model.ParameterItem;

import java.util.ArrayList;
import java.util.List;

import static com.hx.base.mInterface.module.baseModule.BaseService.getBangladeshDisplayJson;


public class ParameterSelectPresenter extends RxBasePresenterImpl<ParameterSelectContact.View> implements ParameterSelectContact.Presenter {
    private List<DisplayMeterBean> lst_DisplayMeterBean;
    private List<ParameterItem> list = new ArrayList<>();

    public ParameterSelectPresenter(ParameterSelectContact.View view) {
        super(view);
    }

    @Override
    public void getShowList() {

    }

    @Override
    public void setReadData(final List<ParameterItem> parameterItemList, int type) {
        getView().showLoading();
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                for (ParameterItem parameterItem : parameterItemList){
                    parameterItem.isSelect = true;
                }
                lst_DisplayMeterBean = getBangladeshDisplayJson();
                list.clear();
                int index = parameterItemList.size();
                list.addAll(parameterItemList);
                for (int i = 0; i < lst_DisplayMeterBean.size(); i++) {
                    boolean tag = true;
                    for (int j = 0; j < parameterItemList.size(); j++) {
                        if (parameterItemList.get(j).obis.replace(" ","").equals(lst_DisplayMeterBean.get(i).getPROTOCOL_OBIS())) {
                            tag = false;
                            break;
                        }
                    }
                    if (tag) {
                        list.add(new ParameterItem(lst_DisplayMeterBean.get(i).getEN_NAME(), false, index++, "", "", ""));
                    }
                }
                HexThreadManager.runTaskOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        getView().hideLoading();
                        getView().showData(list);
                    }
                });
            }
        });

    }
}
