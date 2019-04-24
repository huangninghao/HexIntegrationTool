package com.hx.read.presenter.kyrgyzstan.northElectric;

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
        return builder.setCommMethod(HexDevice.METHOD_OPTICAL)
                .setComName(HexDevice.COMM_NAME_USB)
                .setDevice(HexDevice.KT50)
                .setIsHands(true)
                .setAuthMode(HXFramePara.AuthMode.HLS)
                .setDataFrameWaitTime(3000)
                .setDebugMode(true)
                .setSleepSendTime(20)
                .setDataBit(8)
                .setStopBit(1)
                .setVerify("N").build();
    }
}
