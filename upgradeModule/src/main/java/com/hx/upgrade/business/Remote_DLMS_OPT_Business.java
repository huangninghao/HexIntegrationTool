package com.hx.upgrade.business;

import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;

import com.hexing.libhexbase.log.HexLog;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.StringUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.upgrade.R;
import com.hx.upgrade.UpgradeApplication;
import com.hx.upgrade.eventBus.UIRefresh;
import com.hx.upgrade.model.UpgradeFile;
import com.hx.upgrade.util.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cn.hexing.HexAction;
import cn.hexing.HexDevice;
import cn.hexing.ParaConfig;
import cn.hexing.dlms.HexClientAPI;
import cn.hexing.dlms.HexDataFormat;
import cn.hexing.model.HXFramePara;
import cn.hexing.model.TranXADRAssist;

/**
 * 远程DLMS光电升级
 */
public class Remote_DLMS_OPT_Business {
    private String checkCode; //校验码
    private String firVersion; //固件版本号
    private String meterNumber; //表号
    private File file = null; //升级文件
    private File cfgFile = null;//配置文件
    private List<TranXADRAssist> tranXADRAssistList;
    private HashMap<String, String> cfgMap = new HashMap<>();
    private boolean Tag = true; //文件是否存在
    private boolean mark = false; //升级标志
    private String len;//每帧数据长度
    private int totalNum;
    private static int RETRY_COUNT = 5;//重发次数
    private boolean repeatResult = false; //重发结果
    private int index = 0;  //当前发送的文件数据下标
    private int fileLen = 0;
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "HexLog";

    private int failCount = RETRY_COUNT;

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
     * 一般选择一个.bin文件和一个.cfg文件
     */
    private void selectNextFile() {
        for (UpgradeFile item : Constant.upgradeFiles) {
            if (item.getFile().getName().contains(".bin")) {
                file = item.getFile();
            } else if (item.getFile().getName().contains(".cfg")) {
                cfgFile = item.getFile();
            }
        }
        if (file == null && cfgFile == null) { //文件不存在
            HexLog.writeFile(sdPath, "I/System.out", "====文件不存在====");
            Tag = false;
            if (!mark) { //未找到文件，未进行升级
                String toast = UpgradeApplication.getInstance().getString(R.string.no_file);
                EventBus.getDefault().post(new UIRefresh(toast, "", false));
            } else { //已经升级完所有所选文件，没有后续升级文件
                String toast = UpgradeApplication.getInstance().getString(R.string.succeed);
                EventBus.getDefault().post(new UIRefresh(toast, "", false));
            }
            return;
        }
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtil.getBytesFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileLen = bytes.length;
        TranXADRAssist tranXADRAssist;
        tranXADRAssist = new TranXADRAssist();
        HexClientAPI.getInstance().openSerial();
        tranXADRAssist.obis = Constant.FIRMWARE_VERSION; //读取固件版本号
        tranXADRAssist.dataType = HexDataFormat.VISIBLE_STRING;
        tranXADRAssist.actionType = HexAction.ACTION_READ;
        while (failCount > 0) {
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            firVersion = tranXADRAssist.value;
            HexLog.writeFile(sdPath, "I/System.out", "====读取固件版本号====" + firVersion);
            if (!StringUtil.isEmpty(firVersion)) {
                failCount = RETRY_COUNT;
                HexClientAPI.getInstance().setIsFirstFrame(false);
                break;
            }
            failCount--;
        }
        while (failCount > 0) {    //有固件版本号回复，再读校验码
            tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.obis = Constant.CHECK_CODE; //读取校验码
            tranXADRAssist.dataType = HexDataFormat.VISIBLE_STRING;
            tranXADRAssist.actionType = HexAction.ACTION_READ;
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            checkCode = tranXADRAssist.value;
            HexLog.writeFile(sdPath, "I/System.out", "====读取校验码====" + checkCode);
            if (!TextUtils.isEmpty(checkCode)) {
                failCount = RETRY_COUNT;
                HexClientAPI.getInstance().setIsFirstFrame(false);
                break;
            }
            failCount--;
        }
        while (failCount > 0) { //读表号
            tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.obis = Constant.READ_METER_NUMBER;
            tranXADRAssist.dataType = HexDataFormat.VISIBLE_STRING;
            tranXADRAssist.actionType = HexAction.ACTION_READ;
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            meterNumber = tranXADRAssist.value;
            HexLog.writeFile(sdPath, "I/System.out", "====读表号====" + meterNumber);
            if (!TextUtils.isEmpty(meterNumber)) { //读表号成功
                failCount = RETRY_COUNT;
                HexClientAPI.getInstance().setIsFirstFrame(false);
                break;
            }
            failCount--;
        }
        while (failCount > 0) {
            tranXADRAssist.obis = Constant.WRITE_REMOTELY_UPGRADE_MODE; //升级模式开启
            tranXADRAssist.dataType = HexDataFormat.BOOL;
            tranXADRAssist.writeData = "01";
            tranXADRAssist.actionType = HexAction.ACTION_WRITE;
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            HexLog.writeFile(sdPath, "I/System.out", "====升级模式开启====" + tranXADRAssist.aResult);
            if (tranXADRAssist.aResult) { //成功
                failCount = RETRY_COUNT;
                HexClientAPI.getInstance().setIsFirstFrame(false);
                break;
            }
            failCount--;
        }
        while (failCount > 0) {
            tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.obis = Constant.READ_PRE_FRAME; //每帧数据长度
            tranXADRAssist.dataType = HexDataFormat.DOUBLE_LONG_UNSIGNED;
            tranXADRAssist.actionType = HexAction.ACTION_READ;
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            len = tranXADRAssist.value;
            HexLog.writeFile(sdPath, "", "====读取每帧数据长度====" + len);
            if (!TextUtils.isEmpty(len)) { //读取成功
                failCount = RETRY_COUNT;
                HexClientAPI.getInstance().setIsFirstFrame(false);
                break;
            }
            failCount--;
        }
        while (failCount > 0) {         //激活升级
            tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.obis = Constant.EXECUTE_INIT;
            tranXADRAssist.dataType = HexDataFormat.BOOL;
            String[] str;
            List<String> cfgList = FileUtil.readFileOnLine(cfgFile);
            for (int i = 0; i < cfgList.size(); i++) {
                if (cfgList.get(i).length() > 0) {
                    str = cfgList.get(i).split("=");
                    cfgMap.put(str[0], str[1]);
                }
            }
            byte[] tempBytes = cfgMap.get("VerType").getBytes();
            String verType = StringUtil.bytesToHexString(tempBytes);
            verType = StringUtil.padLeft(verType, 40, '0');
            String fileType = cfgMap.get("FileType");
            String enType = cfgMap.get("EnType");
            String checkType = cfgMap.get("CheckType");
            String verification = StringUtil.padLeft(cfgMap.get("Verification"), 32, '0');
            int lens = (fileType + enType + cfgMap.get("CheckType") + verification).length() / 2 + verType.length() / 2;
            String strDataLength = "06" + StringUtil.padRight(Integer.toHexString(fileLen), 8, '0');
            String writeStr = "020209" + StringUtil.padRight(Integer.toHexString(lens), 2, '0') + fileType + enType + checkType + verType + verification + strDataLength;
            tranXADRAssist.writeData = writeStr;
            tranXADRAssist.actionType = HexAction.ACTION_EXECUTE;
            tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
            HexLog.writeFile(sdPath, "I/System.out", "===初始化升级===result=" + tranXADRAssist.aResult + "||write=" + writeStr);
            if (tranXADRAssist.aResult) {
                failCount = RETRY_COUNT;
                HexClientAPI.getInstance().setIsFirstFrame(false);
                break;
            }
            failCount--;
        }
        if (failCount != RETRY_COUNT) { //重发失败
            String toast = UpgradeApplication.getInstance().getString(R.string.failed);
            EventBus.getDefault().post(new UIRefresh(toast, "", false));
            HexClientAPI.getInstance().closeSerial();
            return;
        }
        List<TranXADRAssist> assists = readFileOnLine(file, len); //解析文件
        if (assists != null && assists.size() > 0) {
            HexClientAPI.getInstance().setDataFrameWaitTime(3000);
            index = 0;
            totalNum = assists.size();
            tranXADRAssistList = assists;
            sendDlmsOPT();
        } else {
            HexLog.writeFile(sdPath, "I/System.out", "====升级文件解析失败====");
            String toast = UpgradeApplication.getInstance().getString(R.string.failed);
            EventBus.getDefault().post(new UIRefresh(toast, "", false));
        }
    }

    /**
     * 文件内容分割，组帧
     *
     * @return 文件内容 String
     */
    private List<TranXADRAssist> readFileOnLine(File file, String readFramLen) {
        List<String> dataList = new ArrayList<>();
        byte[] bytes = new byte[0];
        try {
            bytes = FileUtil.getBytesFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int iDataSize = Integer.parseInt(readFramLen);
        int totalSize = fileLen / iDataSize;
        if (fileLen % iDataSize != 0) {
            totalSize++;
        }
        for (int i = 0; i < totalSize; i++) {
            int from = i * iDataSize;
            int to = (i + 1) * iDataSize;
            if (to >= fileLen) {
                to = fileLen;
            }
            String data = StringUtil.bytesToHexString(Arrays.copyOfRange(bytes, from, to));
            dataList.add(data);
        }
        List<TranXADRAssist> tranXADRAssists = new ArrayList<>();
        String strData;
        if (iDataSize > 128) {
            if (iDataSize < 256) {
                strData = "81" + StringUtil.padRight(Integer.toHexString(iDataSize), 2, '0');
            } else if (iDataSize < 65536) {
                strData = String.format("82%1s%2s", (iDataSize / 256) & 0xFF, (iDataSize % 256) & 0xFF);
            } else {
                strData = String.format("83%1s%2s%3s", (iDataSize >> 16) & 0xFF, (iDataSize >> 8) & 0xFF, iDataSize % 256);
            }
        } else {
            strData = StringUtil.padRight(Integer.toHexString(iDataSize), 2, '0');
        }
        int count = 0;
        while (dataList.size() > 0) {
            StringBuilder stringBuilder;
            stringBuilder = new StringBuilder();
            stringBuilder.append("020206");
            stringBuilder.append(StringUtil.padRight(Integer.toHexString(count), 8, '0'));
            stringBuilder.append("09");
            stringBuilder.append(strData.toUpperCase());
            stringBuilder.append(dataList.get(0));
            TranXADRAssist tranXADRAssist = new TranXADRAssist();
            tranXADRAssist.writeData = stringBuilder.toString();
            tranXADRAssists.add(tranXADRAssist);
            count++;
            dataList.remove(0);
        }
        return tranXADRAssists;
    }

    /**
     * 递归发送文件内容
     * <p>
     * tranXADRAssistList 文件内容，如果为空，必定为发送完成；
     * 第一次调用时，如果文件内容为空，不会进入此方法
     */
    private void sendDlmsOPT() {
        for (TranXADRAssist item : tranXADRAssistList) {
            TranXADRAssist tranXADRAssist = new TranXADRAssist();
            item.obis = Constant.EXECUTE_SEND_DATA;
            item.actionType = HexAction.ACTION_EXECUTE;
            item.dataType = HexDataFormat.BOOL;
            tranXADRAssist = HexClientAPI.getInstance().execute(item);
            // HexLog.writeFile(sdPath,"I/System.out", "===发送数据包" + index + "||发送结果" + tranXADRAssist.aResult + "===" + item.writeData);
            if (tranXADRAssist.aResult) { //回复成功
                index++;
                String toast = "";
                String progress = (new DecimalFormat("0.000")).format((index * 1.0f / totalNum) * 100) + "%";
                EventBus.getDefault().post(new UIRefresh(toast, progress, true));
                if (item == tranXADRAssistList.get(totalNum - 1)) { //最后一帧
                    tranXADRAssist = new TranXADRAssist();
                    tranXADRAssist.obis = Constant.READ_UPGRADE_RESULT;
                    tranXADRAssist.dataType = HexDataFormat.BIT_STRING;
                    tranXADRAssist.actionType = HexAction.ACTION_READ;
                    tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
                    //HexLog.writeFile(sdPath,"I/System.out", "===所有块传输状态===" + tranXADRAssist.value);
                    while (failCount > 0) {
                        tranXADRAssist = new TranXADRAssist();
                        tranXADRAssist.obis = Constant.EXECUTE_UPGRADE_PACKAGE_OVER;
                        tranXADRAssist.dataType = HexDataFormat.UNSIGNED;
                        tranXADRAssist.actionType = HexAction.ACTION_EXECUTE;
                        tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
                        // HexLog.writeFile(sdPath,"I/System.out", "===检查升级包是否传输完毕===" + tranXADRAssist.aResult);
                        if (tranXADRAssist.aResult) {
                            failCount = RETRY_COUNT;
                            HexClientAPI.getInstance().setIsFirstFrame(false);
                            break;
                        }
                        failCount--;
                        //升级程序 重启需要较长时间
                        SystemClock.sleep(5000);
                        HexClientAPI.getInstance().closeSerial();
                        HexClientAPI.getInstance().openSerial();
                    }
                    HexClientAPI.getInstance().setIsFirstFrame(false);
                    tranXADRAssist = new TranXADRAssist();
                    tranXADRAssist.dataType = HexDataFormat.UNSIGNED;
                    tranXADRAssist.obis = Constant.READ_VERIFICATION_MIRROR;
                    tranXADRAssist.actionType = HexAction.ACTION_READ;
                    tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
                    //HexLog.writeFile(sdPath,"I/System.out", "===镜像信息 验证===" + tranXADRAssist.value);
                    failCount = RETRY_COUNT;
                    while (failCount > 0) {
                        tranXADRAssist = new TranXADRAssist();
                        tranXADRAssist.obis = Constant.EXECUTE_ACTIVATION_TIME;
                        tranXADRAssist.dataType = HexDataFormat.BOOL;
                        tranXADRAssist.actionType = HexAction.ACTION_EXECUTE;
                        tranXADRAssist = HexClientAPI.getInstance().execute(tranXADRAssist);
                        HexLog.writeFile(sdPath, "I/System.out", "===立即激活系统===" + tranXADRAssist.aResult);
                        if (tranXADRAssist.aResult) {
                            failCount = RETRY_COUNT;
                            toast = UpgradeApplication.getInstance().getString(R.string.upgrade_success);
                            HexLog.writeFile(sdPath, "I/System.out", "===激活成功===");
                            EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                            HexClientAPI.getInstance().closeSerial();
                            break;
                        }
                        failCount--;
                        SystemClock.sleep(1000);
                        HexClientAPI.getInstance().setIsFirstFrame(true);
                    }
                    if (failCount != RETRY_COUNT) {
                        toast = UpgradeApplication.getInstance().getString(R.string.failed);
                        EventBus.getDefault().post(new UIRefresh(toast, progress, false));
                        HexClientAPI.getInstance().closeSerial();
                        return;
                    }
                }
            } else { //回复失败，开始重发
                repeatResult = repeatSend(item);
                if (!repeatResult) { //重发失败，结束
                    HexClientAPI.getInstance().closeSerial();
                    String toast = UpgradeApplication.getInstance().getString(R.string.repeat_failed);
                    EventBus.getDefault().post(new UIRefresh(toast, "", false));
                    break;
                }
            }
        }
    }

    /**
     * 错误重发
     *
     * @param item 重发数据
     */
    private boolean repeatSend(TranXADRAssist item) {
        if (failCount > 0) {
            item.actionType = HexAction.ACTION_EXECUTE;
            item.obis = Constant.EXECUTE_SEND_DATA;
            item.dataType = HexDataFormat.BOOL;
            TranXADRAssist tranXADRAssist = HexClientAPI.getInstance().execute(item);
            HexLog.writeFile(sdPath, "", "===发送数据包" + index + "||发送结果" + tranXADRAssist.aResult + "===" + item.writeData);
            if (tranXADRAssist.aResult) { //回复成功
                index = index + 1;
                String toast = "";
                String progress = (new DecimalFormat("0.000")).format((index * 1.0f / totalNum) * 100) + "%";
                EventBus.getDefault().post(new UIRefresh(toast, progress, true));
                if (item == tranXADRAssistList.get(totalNum - 1)) { //最后一帧关闭串口
                    EventBus.getDefault().post(new UIRefresh(toast, "", false));
                    HexClientAPI.getInstance().closeSerial();
                }
                return true;
            } else { //回复失败，开始重发
                failCount--;
                repeatResult = repeatSend(item);
                return repeatResult;
            }
        } else {
            return false;
        }
    }
}
