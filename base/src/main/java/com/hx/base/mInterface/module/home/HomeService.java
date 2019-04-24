package com.hx.base.mInterface.module.home;

import android.app.Activity;

import com.hexing.libhexbase.tools.GJsonUtil;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.base.BaseApplication;
import com.hx.base.BaseConstant;
import com.hx.base.R;
import com.hx.base.mInterface.provider.IHomeProvider;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.mInterface.router.ServiceManager;
import com.hx.base.model.DeviceBean;
import com.hx.base.model.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * description：
 * update by:
 * update day:
 */
public class HomeService {

    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }

    public static void selectedTab(Activity activity, int position) {
        if (!hasModule()) return;
        ServiceManager.getInstance().getHomeProvider().selectedTab(activity, position);
    }

    /**
     * 获取登录账号的 设备列表
     *
     * @param userInfoEntity 用户信息
     * @return List<DeviceBean>
     */
    public static List<DeviceBean> getDeviceList(UserInfoEntity userInfoEntity) {
        String json = FileUtil.getJson(BaseApplication.getInstance().getContext(), "devices.json");
        List<DeviceBean> list = GJsonUtil.fromJsonList(json, DeviceBean.class);
        if (list == null) {
            list = new ArrayList<>();
        }
        String[] showDeviceNo = userInfoEntity.getRole().split("\\|");
        List<DeviceBean> showList = new ArrayList<>();
        for (DeviceBean item : list) {
            for (String deviceNo : showDeviceNo) {
                if (deviceNo.equals(item.deviceNo)) {
                    showList.add(item);
                }
            }
        }
        return showList;
    }

    /**
     * 获取通讯方式
     *
     * @param enumMethod int
     * @return string
     */
    public static String getCommMethod(String enumMethod) {
        if (enumMethod.equals(BaseConstant.METHOD_OPTICAL)) {
            return BaseApplication.getInstance().getString(R.string.base_method_optical);
        } else if (enumMethod.equals(BaseConstant.METHOD_RF)) {
            return BaseApplication.getInstance().getString(R.string.base_method_rf);
        } else if (enumMethod.equals(BaseConstant.METHOD_ZIGBEE)) {
            return BaseApplication.getInstance().getString(R.string.base_method_zigbee);
        }
        return BaseApplication.getInstance().getString(R.string.base_method_unknown);
    }

    /**
     * 获取通讯协议
     *
     * @param enumMethod int
     * @return string
     */
    public static String getProtocolMethod(String enumMethod) {
        if (enumMethod.equals(BaseConstant.PROTOCOL_DLMS)) {
            return BaseApplication.getInstance().getString(R.string.base_protocol_dlms);
        } else if (enumMethod.equals(BaseConstant.PROTOCOL_ZIGBEE_645)) {
            return BaseApplication.getInstance().getString(R.string.base_method_zigbee);
        } else if (enumMethod.equals(BaseConstant.PROTOCOL_21)) {
            return BaseApplication.getInstance().getString(R.string.base_protocol_21);
        }
        return BaseApplication.getInstance().getString(R.string.base_method_unknown);
    }

    /**
     * 获取芯片类型
     *
     * @param enumMethod int
     * @return string
     */
    public static String getMeterMCU(String enumMethod) {
        if (enumMethod.equals("0")) {
            return BaseApplication.getInstance().getString(R.string.base_MCU_TDK);
        } else if (enumMethod.equals("1")) {
            return BaseApplication.getInstance().getString(R.string.base_MCU_RN8213);
        }
        return BaseApplication.getInstance().getString(R.string.base_method_unknown);
    }
}
