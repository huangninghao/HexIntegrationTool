package com.hx.base;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.cbl.dialog.ActivityStackManager;
import com.cbl.dialog.DialogsMaintainer;
import com.cbl.dialog.StyledDialog;
import com.hexing.libhexbase.application.HexApplication;
import com.hexing.libhexbase.log.HexLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author caibinglong
 * date 2018/11/5.
 * desc desc
 */

public class BaseApplication extends HexApplication {
    public static boolean isDebug = true;
    //系统初始化生成目录名字常量
    public static final String FILEPATH_ROOT_NAME = "HexTools";
    public static final String FILEPATH_CACHE_NAME = "cache";
    public static final String FILEPATH_BASE_CONFIG_NAME = "baseConfig";
    public static final String FILEPATH_UPAPK_NAME = "upapk";
    public static final String FILEPATH_CAMERA_NAME = "camera";//照片存储
    public static final String FILEPATH_FIRMWARE_UPGRADE = "upgradePatch";//固件升级包目录
    public static final String FILEPATH_CAMERA_NAME_ORIPICTURE = "OriPicture";//原图
    public static final String FILEPATH_CAMERA_NAME_CROPPICTURE = "CropPicture";//裁剪
    public static final String FILEPATH_RECORD_NAME = "record";//历史记录
    public static String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String FILEPATH_ROOT = SDCARD_ROOT + File.separator + FILEPATH_ROOT_NAME;
    public static final String FILEPATH_CACHE = FILEPATH_ROOT + File.separator + FILEPATH_CACHE_NAME;
    public static final String FILEPATH_BASE_CONFIG = FILEPATH_ROOT + File.separator + FILEPATH_BASE_CONFIG_NAME;
    public static final String FILEPATH_UPDATE_APK = FILEPATH_ROOT + File.separator + FILEPATH_UPAPK_NAME;
    public static final String FILEPATH_UPGRADE = FILEPATH_ROOT + File.separator + FILEPATH_FIRMWARE_UPGRADE;
    public static final String FILEPATH_CAMERA = FILEPATH_ROOT + File.separator + FILEPATH_CAMERA_NAME;
    public static final String FILEPATH_RECORD = FILEPATH_ROOT + File.separator + FILEPATH_RECORD_NAME;
    public static final String FILEPATH_CAMERA_ORIPICTURE = FILEPATH_CAMERA + File.separator + FILEPATH_CAMERA_NAME_ORIPICTURE;
    public static final String FILEPATH_CAMERA_CROPPICTURE = FILEPATH_CAMERA + File.separator + FILEPATH_CAMERA_NAME_CROPPICTURE;

    @Override
    public void onCreate() {
        super.onCreate();
        List<String> dirs = new ArrayList<>();
        {
            dirs.add(FILEPATH_CACHE);
            dirs.add(FILEPATH_BASE_CONFIG);
            dirs.add(FILEPATH_UPDATE_APK);
            dirs.add(FILEPATH_UPGRADE);
            dirs.add(FILEPATH_CAMERA);
            dirs.add(FILEPATH_RECORD);
            dirs.add(FILEPATH_CAMERA_ORIPICTURE);
            dirs.add(FILEPATH_CAMERA_CROPPICTURE);
        }
        HexApplication.getInstance().createDir(dirs);
        HexLog.setLogPathSdcardDir(FILEPATH_RECORD);
        StyledDialog.init(getApplicationContext());
        registerCallback();
    }

    private void registerCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityStackManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
                DialogsMaintainer.onPause(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityStackManager.getInstance().removeActivity(activity);
            }
        });
    }
}
