package com.hx.read.presenter.iraq.read.ME152;

import android.text.TextUtils;

import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.inter.RxBasePresenterImpl;
import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.read.R;
import com.hx.read.ReadApp;
import com.hx.read.contact.iraq.read.HXE12_DL.InstantaneousContact;
import com.hx.read.utils.Constant;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.hexing.HexAction;
import cn.hexing.iec21.HexClient21API;
import cn.hexing.model.TranXADRAssist;

public class InstantaneousPresenter extends RxBasePresenterImpl<InstantaneousContact.View> implements InstantaneousContact.Presenter {
    private HexClient21API clientAPI;
    private String meterNumber = "";

    public InstantaneousPresenter(InstantaneousContact.View view) {
        super(view);
        clientAPI = PortConfig.getInstance().getClientAPI();
    }

    @Override
    public List<TranXADRAssist> getShowList() {
        List<TranXADRAssist> list = new ArrayList<>();
        TranXADRAssist item;

        item = new TranXADRAssist();
        item.obis = "F070";
        item.unit = "V";
        item.name = "Voltage";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "F073";
        item.unit = "A";
        item.name = "Current A Phase Current";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "F074";
        item.unit = "";
        item.name = "Power factor";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);

        item = new TranXADRAssist();
        item.obis = "F076";
        item.unit = "Hz";
        item.name = "Frequency";
        item.actionType = HexAction.ACTION_READ;
        list.add(item);
        return list;
    }

    @Override
    public void read() {
        if (getView() != null) {
            getView().showLoading();
        }
        HexThreadManager.singleThreadPoolRun(new Runnable() {
            @Override
            public void run() {
                clientAPI.addListener(listener);
                clientAPI.action(getShowList());
            }
        });
    }

    private cn.hexing.IHexListener listener = new cn.hexing.IHexListener() {
        @Override
        public void onSuccess(TranXADRAssist data, int pos) {
            noticeResult(data, data.aResult);
        }

        @Override
        public void onFailure(String msg) {
            noticeResult(msg, false);
        }
    };

    /**
     * 通知结果
     *
     * @param object object
     */
    private void noticeResult(final Object object, final boolean isSuccess) {
        HexThreadManager.runTaskOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (getView() != null) {
                    getView().hideLoading();
                    if (isSuccess) {
                        getView().showData((TranXADRAssist) object);
                    } else {
                        getView().showToast(R.string.read_failed);
                    }
                }
            }
        });
    }

    /**
     * 保存读取数据
     * <p>
     * 所有的数据保存都走这里
     */
    @Override
    public boolean saveData(List<TranXADRAssist> obisListData, String type) {
        if (TextUtils.isEmpty(StringCache.get(Constant.METER_NUMBER))) {
            return false;
        }
        meterNumber = StringCache.get(Constant.METER_NUMBER);
        HexLog.d("saveData", "....");
        StringBuilder mStringBuilder = new StringBuilder();
        boolean result = true;
        try {
            File insDtaFile = new File(ReadApp.FILEPATH_CACHE + File.separator + meterNumber + ".csv");
            if (!insDtaFile.exists()) {
                result = insDtaFile.createNewFile();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm:ss", Locale.getDefault());
            Date curDate = new Date(System.currentTimeMillis());
            String str = formatter.format(curDate);
            mStringBuilder.append(str);
            mStringBuilder.append(",");
            mStringBuilder.append(type);
            mStringBuilder.append("\n");
            for (TranXADRAssist data : obisListData) {
                mStringBuilder.append(data.name);
                mStringBuilder.append(",");
                mStringBuilder.append(data.value);
                mStringBuilder.append(data.unit);
                mStringBuilder.append("\n");
            }
            ByteArrayInputStream istr = new ByteArrayInputStream(mStringBuilder.toString().getBytes());
            FileUtil.inputStreamToFile(istr, insDtaFile);
        } catch (Exception e) {
            HexLog.e("保存瞬时量数据出错", e.getMessage());
            result = false;
        }
        return result;
    }
}
