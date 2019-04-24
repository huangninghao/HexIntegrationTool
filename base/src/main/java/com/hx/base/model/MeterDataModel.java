package com.hx.base.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HEC271
 * on 2018/7/13.
 * 表动态配置 数据模型
 */

public class MeterDataModel implements Serializable {

    @JSONField(name = "DataModel")
    public DataModelBean DataModel;

    public static class DataModelBean implements Serializable {

        @JSONField(name = "-Protocol")
        public String Protocol;
        @JSONField(name = "-DateTime")
        public String DateTime;
        @JSONField(name = "-Version")
        public String Version;
        @JSONField(name = "-AccessRight")
        public String AccessRight;
        @JSONField(name = "OBIS")
        public List<OBISBean> OBIS;

        public static class OBISBean implements Serializable {

            @JSONField(name = "-functionid")
            public String functionid;
            @JSONField(name = "-obis")
            public String obis;
            @JSONField(name = "-classid")
            public String classid;
            @JSONField(name = "-version")
            public String version;
            @JSONField(name = "-name")
            public String name;
            @JSONField(name = "method")
            public MethodBean method;
            @JSONField(name = "attribute")
            public List<AttributeBean> attribute;

            public static class MethodBean implements Serializable {
                /**
                 * -id : 1
                 * -name : execute
                 * -accessright : Public=\,Reading=\,Pre.Est.=\,Management=A
                 * item : {"-name":"execute","-type":"long_unsigned","-size":"2","-coding":"Hex"}
                 */

                @JSONField(name = "-id")
                public String id;
                @JSONField(name = "-name")
                public String name;
                @JSONField(name = "-accessright")
                public String accessright;
                @JSONField(name = "item")
                public ItemBean item;
                public static class ItemBean implements Serializable {
                    /**
                     * -name : execute
                     * -type : long_unsigned
                     * -size : 2
                     * -coding : Hex
                     */

                    @JSONField(name = "-name")
                    public String name;
                    @JSONField(name = "-type")
                    public String type;
                    @JSONField(name = "-size")
                    public String size;
                    @JSONField(name = "-coding")
                    public String coding;
                }
            }

            public static class AttributeBean implements Serializable {
                /**
                 * -id : 2
                 * -name : value
                 * -accessright : Public=\,Reading=\,Pre.Est.=\,Management=R
                 * item : {"-name":"value","-type":"long_unsigned","-size":"2","-unit":"%","-scaler":"-2","-coding":"Hex"}
                 */
                @SerializedName("-id")
                public String id;
                @SerializedName("-name")
                public String nameX;
                @SerializedName("-accessright")
                public String accessrightX;
                @SerializedName("item")
                public ItemBean item;
                public static class ItemBean implements Serializable{
                    /**
                     * -name : value
                     * -type : long_unsigned
                     * -size : 2
                     * -unit : %
                     * -scaler : -2
                     * -coding : Hex
                     */
                    @SerializedName("-name")
                    public String name;
                    @SerializedName("-type")
                    public String type;
                    @SerializedName("-size")
                    public String size;
                    @SerializedName("-unit")
                    public String unit;
                    @SerializedName("-scaler")
                    public String scaler;
                    @SerializedName("-coding")
                    public String coding;
                    @SerializedName("-comments")
                    public String comments;
                }
            }
        }
    }
}
