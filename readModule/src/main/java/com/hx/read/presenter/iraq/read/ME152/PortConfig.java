package com.hx.read.presenter.iraq.read.ME152;

import com.hexing.libhexbase.cache.StringCache;
import com.hx.read.utils.Constant;

import cn.hexing.HexDevice;
import cn.hexing.HexHandType;
import cn.hexing.MeterType;
import cn.hexing.ParaConfig;
import cn.hexing.iec21.HexClient21API;
import cn.hexing.model.HXFramePara;

public class PortConfig {
    private static PortConfig instance;

    public static PortConfig getInstance() {
        if (instance == null) {
            instance = new PortConfig();
        }
        return instance;
    }

    public HexClient21API getClientAPI() {
        ParaConfig.Builder builder = new ParaConfig.Builder();
        return builder.setCommMethod(HexDevice.METHOD_RF)
                .setComName(HexDevice.COMM_NAME_RF2)
                .setDevice(HexDevice.KT50)
                .setIsHands(false)
                .setBaudRate(4800)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setDataFrameWaitTime(1500)
                .setDebugMode(true)
                .setStrMeterNo(StringCache.get(Constant.METER_NUMBER))//99999999
                .setStrMeterPwd("456123")
                .setHandType(HexHandType.IRAQ)
                .setMeterType(MeterType.IRAQ_SINGLE)
                .setVerify("N").build21();
    }
}
