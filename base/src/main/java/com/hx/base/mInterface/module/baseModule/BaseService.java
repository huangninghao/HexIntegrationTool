package com.hx.base.mInterface.module.baseModule;

import com.hx.base.mInterface.provider.IHomeProvider;
import com.hx.base.mInterface.router.ModuleManager;
import com.hx.base.model.DisplayMeterBean;
import com.hx.base.model.FreezeMeterBean;
import com.hx.base.model.MeterDataModel;
import com.hx.base.util.JsonFileUtil;

import java.util.List;


/**
 * description：
 * update by:
 * update day:
 */
public class BaseService {

    private static boolean hasModule() {
        return ModuleManager.getInstance().hasModule(IHomeProvider.HOME_MAIN_SERVICE);
    }

    public static List<FreezeMeterBean> getIndonesiaJson() {
        String key = "IndonesiaHXE320";
        String path = "indonesia/HXE320_DLMS.json";
        return JsonFileUtil.initJson(path, key);
    }

    public static List<DisplayMeterBean> getIndonesiaDisplayJson() {
        String key = "IndonesiaDisplayHXE320";
        String path = "indonesia/HXE320_DISPLAY_ITEMS.json";
        return JsonFileUtil.initDisplayJson(path, key);
    }

    public static MeterDataModel getNorthElectricHXE110Json() {
        String key = "NorthElectricHXE110";
        String path = "nortel/HXE110.json";
        return JsonFileUtil.initNorthElectricJson(path, key);
    }

    public static MeterDataModel getNorthElectricHXE310Json() {
        String key = "NorthElectricHXE310";
        String path = "nortel/HXE310.json";
        return JsonFileUtil.initNorthElectricJson(path, key);
    }

    public static MeterDataModel getNorthElectricHXF300Json() {
        String key = "NorthElectricHXF300";
        String path = "nortel/HXF300.json";
        return JsonFileUtil.initNorthElectricJson(path, key);
    }

    public static List<DisplayMeterBean> getBangladeshDisplayJson() {
        String key = "BangladeshDisplay";
        String path = "bangladesh/HXE310_KP/DISPLAY_ITEMS_DLMSALL.json";
        return JsonFileUtil.initDisplayJson(path, key);
    }

}
