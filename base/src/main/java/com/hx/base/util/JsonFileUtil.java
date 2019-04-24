package com.hx.base.util;

import com.alibaba.fastjson.JSON;
import com.hexing.libhexbase.cache.StringCache;
import com.hexing.libhexbase.thread.HexThreadManager;
import com.hexing.libhexbase.tools.file.FileUtil;
import com.hx.base.BaseApplication;
import com.hx.base.model.DisplayMeterBean;
import com.hx.base.model.FreezeMeterBean;
import com.hx.base.model.MeterDataModel;

import java.util.List;

/**
 * Created by HEC271
 * on 2018/8/8.
 * json 文件 预加载
 */

public class JsonFileUtil {
    /**
     * 加载json 文件
     */
   public static List<FreezeMeterBean> model0 = null;
    public static synchronized List<FreezeMeterBean> initJson(final String path, final String key) {

        if (model0 == null || model0.size() == 0) {
            model0 = StringCache.getJavaBeanList(key, FreezeMeterBean.class);
            String json = FileUtil.getJson(BaseApplication.getInstance().getContext(), path);
            model0 = JSON.parseArray(json, FreezeMeterBean.class);
            StringCache.putJavaBeanList(key, model0);
        }
        return model0;
    }

    /**
     * 加载轮显 json 文件
     */
    private  static    List<DisplayMeterBean> model=null;
    public static synchronized List<DisplayMeterBean> initDisplayJson(final String path, final String key) {

        if (model == null || model.size() == 0) {
            model = StringCache.getJavaBeanList(key, DisplayMeterBean.class);
            String json = FileUtil.getJson(BaseApplication.getInstance().getContext(), path);
            model = JSON.parseArray(json, DisplayMeterBean.class);
            StringCache.putJavaBeanList(key, model);
        }
        return model;
    }

    public static synchronized MeterDataModel initNorthElectricJson(final String path, final String key) {
        MeterDataModel model = StringCache.getJavaBean(key);
        if (model == null) {
            String json = FileUtil.getJson(BaseApplication.getInstance().getContext(), path);
            model = JSON.parseObject(json, MeterDataModel.class);
            StringCache.putJavaBean(key, model);
        }
        return model;
    }

}
