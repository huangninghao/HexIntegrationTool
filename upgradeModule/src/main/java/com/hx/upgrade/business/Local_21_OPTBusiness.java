package com.hx.upgrade.business;

import com.hexing.libhexbase.thread.HexThreadManager;

import java.util.List;

import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class Local_21_OPTBusiness {
    private String checkCode; //校验码
    private String firVersion; //固件版本号
    private String meterNumber; //表号

    private List<TranXADRAssist> tranXADRAssistList;
    private boolean Tag = true; //文件是否存在
    private boolean mark = false; //升级标志
    private int totalNum;
    private boolean repeatResult = false; //重发结果
    private int index = 0;  //当前发送的文件数据下标
    /**
     * 本地21光电
     */
    public void upgrade() {
        HexThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                ParaConfig.Builder builder = new ParaConfig.Builder();
                builder.setCommMethod(HexDevice.METHOD_OPTICAL)
                        .setComName(HexDevice.COMM_NAME_USB)
                        .setBaudRate(4800)
                        .setDevice(HexDevice.KT50)
                        .setIsHands(true)
                        .setAuthMode(HXFramePara.AuthMode.HLS)
                        .setDataFrameWaitTime(1500)
                        .setDebugMode(true)
                        .setIsFirstFrame(true).build();
                mark = false;
//                selectNextFile();
            }
        });
    }
}
