package com.hx.upgrade.business;

import android.os.Environment;
import android.text.TextUtils;

import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hx.upgrade.R;
import com.hx.upgrade.UpgradeApplication;
import com.hx.upgrade.eventBus.UIRefresh;
import com.hx.upgrade.presenter.UpgradePresenter;
import com.hx.upgrade.util.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.HexProtocol;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

public class Local_DLMS_OPTBusiness {
    private String checkCode; //校验码
    private String firVersion; //固件版本号
    private String meterNumber; //表号

    private List<TranXADRAssist> tranXADRAssistList;
    private boolean Tag = true; //文件是否存在
    private boolean mark = false; //升级标志
    private int totalNum;
    private boolean repeatResult = false; //重发结果
    private int index = 0;  //当前发送的文件数据下标
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HexLog"; //日志文件路径

    /**
     * 本地DLMS光电
     */
    public void upgrade() {
        String toast = "";
        String progress = UpgradeApplication.getInstance().getString(R.string.initialization_upgrading);
        EventBus.getDefault().post(new UIRefresh(toast, progress, true));
        HexThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                mark = false;
                selectNextFile();
            }
        });
    }

    /**
     * 根据表当前固件版本号和校验码选择文件
     * 循环选择下一个文件(现在的表只支持单个文件)
     */
    private void selectNextFile() {
        String writeData; //根据前面逻辑判断是激活升级或者擦除数据
        TranXADRAssist tranXADRAssist;
        tranXADRAssist = new TranXADRAssist();
        tranXADRAssist.obis = Constant.FIRMWARE_VERSION; //读取固件版本号
        tranXADRAssist.dataType = HexDataFormat.VISIBLE_STRING;
        tranXADRAssist.actionType = HexAction.ACTION_READ;
        HexClientAPI.getInstance().openSerial();
        tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
        firVersion = tranXADRAssist.value; //固件版本号，用于选择一个升级文件（升级文件名中，带有固件版本号)
        HexLog.writeFile(sdPath, "", "====固件版本号读取====" + firVersion);
        if (TextUtils.isEmpty(firVersion)) { //固件版本号没有回复，开始过程升级，擦除数据
            HexLog.writeFile(sdPath, "", "====固件版本号读取失败====");
            writeData = Constant.CLEAR;
            String toast = UpgradeApplication.getInstance().getString(R.string.FIRMWARE_VERSION);
            String progress = UpgradeApplication.getInstance().getString(R.string.process_upgrading);
            EventBus.getDefault().post(new UIRefresh(toast, progress, true));
        } else {    //有固件版本号回复，再读校验码
            HexClientAPI.getInstance().setIsFirstFrame(false);
            tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.obis = Constant.CHECK_CODE; //读取校验码
            tranXADRAssist.dataType = HexDataFormat.VISIBLE_STRING;
            tranXADRAssist.actionType = HexAction.ACTION_READ;
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            checkCode = tranXADRAssist.value;
            HexLog.writeFile(sdPath, "", "====读取校验码====" + checkCode);
            if (TextUtils.isEmpty(checkCode)) { //校验码没有回复，结束
                HexLog.writeFile(sdPath, "", "====校验码读取失败====");
                String toast = UpgradeApplication.getInstance().getString(R.string.CHECK_CODE);
                EventBus.getDefault().post(new UIRefresh(toast, "", false));
                HexClientAPI.getInstance().closeSerial();
                return;
            } else {
                File file = Constant.upgradeFiles.get(0).getFile();
                if (file != null && file.exists()) { //文件存在
                    tranXADRAssist = new TranXADRAssist();
                    tranXADRAssist.obis = Constant.READ_METER_NUMBER;
                    tranXADRAssist.dataType = HexDataFormat.VISIBLE_STRING;
                    tranXADRAssist.actionType = HexAction.ACTION_READ;
                    tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
                    meterNumber = tranXADRAssist.value;
                    if (!TextUtils.isEmpty(meterNumber)) { //读表号成功
                        HexLog.writeFile(sdPath, "", "====表号读取成功====meterNumber：" + meterNumber);
                        writeData = Constant.ACTIVATION; //激活升级
                    } else {
                        HexLog.writeFile(sdPath, "", "====表号读取失败====");
                        String toast = UpgradeApplication.getInstance().getString(R.string.READ_METER_NUMBER);
                        String progress = "";
                        EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                        HexClientAPI.getInstance().closeSerial();
                        return;
                    }
                } else { //文件未找到
                    Tag = false;
                    if (!mark) { //未找到文件，未进行升级
                        HexLog.writeFile(sdPath, "", "====未找到文件====");
                        String toast = UpgradeApplication.getInstance().getString(R.string.no_file);
                        String progress = "";
                        EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                    } else { //已经升级完所有所选文件，没有后续升级文件
                        HexLog.writeFile(sdPath, "", "====升级成功====");
                        String toast = UpgradeApplication.getInstance().getString(R.string.succeed);
                        String progress = "";
                        EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                    }
                    HexClientAPI.getInstance().closeSerial();
                    return;
                }
            }
        }
        if (Tag) {
            List<TranXADRAssist> assists = readFileOnLine(Constant.upgradeFiles.get(0).getFile()); //解析文件
            List<TranXADRAssist> tranXADRAssists = new ArrayList<>();
            tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.writeData = Constant.FIRSTFILEDATA; //手动加入文件第一帧
            tranXADRAssists.add(tranXADRAssist);
            if (writeData.equals(Constant.ACTIVATION)) { //激活表计进入直接通讯式BootLoader状态 本地升级
                tranXADRAssist = new TranXADRAssist();
                tranXADRAssist.obis = Constant.WRITE_LOCAL_METER_MODE; //激活升级失败
                tranXADRAssist.dataType = HexDataFormat.UNSIGNED;
                tranXADRAssist.writeData = "00";
                tranXADRAssist.actionType = HexAction.ACTION_WRITE;
                tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
                if (!tranXADRAssist.aResult) {
                    HexLog.writeFile(sdPath, "", "====激活升级失败====");
                    String toast = UpgradeApplication.getInstance().getString(R.string.WRITE_LOCAL_METER_MODE);
                    String progress = "";
                    EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                    HexClientAPI.getInstance().closeSerial();
                    return;
                }
            } else {
                tranXADRAssist = new TranXADRAssist();
                tranXADRAssist.writeData = writeData;  //根据前面逻辑判断是激活升级或者擦除数据
                tranXADRAssists.add(0, tranXADRAssist);
            }
            if (assists != null && assists.size() > 0) {
//                assists.subList(1984, 2049).clear();//F800-FFE0移除
                int beginIndex = -1, endIndex = -1;
                for (int i = 0; i < assists.size(); i++) { //找到boot区地址
                    if (assists.get(i).writeData.contains(UpgradePresenter.beginStr)) {
                        beginIndex = i;
                    }
                    if (assists.get(i).writeData.contains(UpgradePresenter.endStr)) {
                        endIndex = i;
                        break;
                    }
                }
                if (beginIndex != endIndex && beginIndex != -1 && endIndex != -1) {
                    assists.subList(beginIndex, endIndex).clear();//移除此区间数据
                    HexLog.writeFile(sdPath, "", "====删除boot去数据====" + beginIndex + "-" + endIndex);
                }
                tranXADRAssists.addAll(assists);
                HexClientAPI.getInstance().openSerial();
                HexClientAPI.getInstance().setDataFrameWaitTime(10 * 1000);//擦除数据需要一段时间
                HexClientAPI.getInstance().setBaudRate(9600);
                index = 0;
                totalNum = tranXADRAssists.size();
                tranXADRAssistList = tranXADRAssists;
                sendDlmsOPT();
            } else {  //文件解析失败
                HexLog.writeFile(sdPath, "", "====文件解析失败====");
                String toast = UpgradeApplication.getInstance().getString(R.string.file_err);
                String progress = "";
                EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                HexClientAPI.getInstance().closeSerial();
            }
        }
    }

    /**
     * 读取指定文件
     *
     * @return 文件内容 String
     */
    private List<TranXADRAssist> readFileOnLine(File file) {
        List<TranXADRAssist> dataList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
//            inputStream = UpgradeApplication.getInstance().getAssets().open(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            HexLog.writeFile(sdPath, "", "读取文本内容,文件解析失败");
            return dataList;
        }
        StringBuilder strBuilder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String strLine;
        try {
            //通过read line按行读取
            while ((strLine = bufferedReader.readLine()) != null) {
                //strLine就是一行的内容
                if (!TextUtils.isEmpty(strLine)) {
                    TranXADRAssist tranXADRAssist = new TranXADRAssist();
                    strBuilder.append(strLine);
                    tranXADRAssist.writeData = strBuilder.toString();
                    dataList.add(tranXADRAssist);
                    strBuilder = new StringBuilder();
                }
            }
            bufferedReader.close();
            streamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 递归发送文件内容
     * <p>
     * tranXADRAssistList 文件内容，如果为空，必定为发送完成；
     * 第一次调用时，如果文件内容为空，不会进入此方法
     */
    private void sendDlmsOPT() {
        if (tranXADRAssistList != null && tranXADRAssistList.size() > 0) {
            for (TranXADRAssist item : tranXADRAssistList) {
                //TranXADRAssist assist = tranXADRAssistList.get(0);
                item.protocol = HexProtocol.PRO_21;
                item.needMoveData = true;
                item.needMoveAnalysis = true;
                if (index == 0) { // 第一帧接收19字节
                    item.byteLen = 19;
                } else {
                    item.byteLen = 14;
                }
                TranXADRAssist tranXADRAssist = HexClientAPI.getInstance().sendUpgrade(item);
                if (tranXADRAssist.aResult) { //回复成功
                    index = index + 1;
                    String toast = "";
                    String progress = (new DecimalFormat("0.000")).format((index * 1.0f / totalNum) * 100) + "%";
                    EventBus.getDefault().post(new UIRefresh(toast, progress, true));
                    if (item == tranXADRAssistList.get(totalNum - 1)) { //最后一帧关闭串口
                        HexLog.writeFile(sdPath, "", "====升级成功====");
                        EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                        HexClientAPI.getInstance().closeSerial();
                    }
                } else if (!item.writeData.substring(3, 9).equals("000005")) { //回复失败，开始重发(几内亚单项表，升级文件bug，000005的报文表不能识别，但不直接
                    // 影响升级)
                    failedCount = 0;
                    repeatResult = repeatSend(item);
                    if (!repeatResult) { //重发失败，结束
                        HexClientAPI.getInstance().closeSerial();
                        String toast = UpgradeApplication.getInstance().getString(R.string.repeat_failed);
                        String progress = "";
                        EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                        HexClientAPI.getInstance().closeSerial();
                        break;
                    }
                }
            }
        } else { //当前文件内容全部发完
            String toast = UpgradeApplication.getInstance().getString(R.string.succeed);
            String progress = "";
            EventBus.getDefault().post(new UIRefresh(toast, progress, false));
            HexClientAPI.getInstance().closeSerial();
        }
    }

    private int failedCount = 0;

    /**
     * 错误重发
     *
     * @param item 重发数据
     */
    private boolean repeatSend(TranXADRAssist item) {
        if (failedCount < 5) {
            item.protocol = HexProtocol.PRO_21;
            item.needMoveData = true;
            item.needMoveAnalysis = true;
            if (index == 0) { // 第一帧接收19字节
                item.byteLen = 19;
            } else {
                item.byteLen = 14;
            }
            TranXADRAssist tranXADRAssist = HexClientAPI.getInstance().sendUpgrade(item);
            HexLog.writeFile(sdPath, "", index + "");
            HexLog.writeFile(sdPath, "", item.writeData);
            if (tranXADRAssist.aResult) { //回复成功
                index = index + 1;
                String toast = "";
                String progress = (new DecimalFormat("0.000")).format((index * 1.0f / totalNum) * 100) + "%";
                EventBus.getDefault().post(new UIRefresh(toast, progress, true));
                if (item == tranXADRAssistList.get(totalNum - 1)) { //最后一帧关闭串口
                    HexLog.writeFile(sdPath, "", "====升级成功====");
                    EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                    HexClientAPI.getInstance().closeSerial();
                }
                return true;
            } else { //回复失败，开始重发
                failedCount++;
                repeatResult = repeatSend(item);
                return repeatResult;
            }
        } else {
            return false;
        }
    }
}
