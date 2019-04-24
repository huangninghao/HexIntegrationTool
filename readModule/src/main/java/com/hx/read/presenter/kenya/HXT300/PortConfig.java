package com.hx.read.presenter.kenya.HXT300;

import com.hexing.libhexbase.cache.StringCache;
import com.hx.read.utils.Constant;

import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.model.HXFramePara;

public class PortConfig {
    private static PortConfig instance;

    public static PortConfig getInstance() {
        if (instance == null) {
            instance = new PortConfig();
        }
        return instance;
    }

    public HexClientAPI getClientAPI() {
        ParaConfig.Builder builder = new ParaConfig.Builder();
        return builder.setCommMethod(HexDevice.METHOD_RF)
                .setComName(HexDevice.COMM_NAME_RF2)
                .setIsFirstFrame(false)
                .setIsHands(true)
                .setBaudRate(4800)
                .setDevice(HexDevice.KT50)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setStrMeterNo(StringCache.get(Constant.METER_NUMBER))
                .setIsFixedChannel(0)
                .setDataFrameWaitTime(5500)
                .setDebugMode(true)
                .setDataBit(8)
                .setStopBit(1)
                .setVerify("N").build();
    }
}
