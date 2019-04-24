package com.hx.base.model;

import java.io.Serializable;

public class DisplayMeterBean implements Serializable {

    /**
     * ID : 404564
     * ORDER_NO : 56
     * PROTOCOL_OBIS : 1.8.0
     * PROTOCOL_ID : 9010
     * CN_NAME : [当前] 正向有功总电能
     * EN_NAME : Active energy (+), current month
     * ITEM_TYPE : E.C
     * WRITE_IS_ENABLED : 1
     * DISPLAY_TYPE : null
     * REMARK : null
     */

    private int ID;
    private int ORDER_NO;
    private String PROTOCOL_OBIS;
    private String PROTOCOL_ID;
    private String CN_NAME;
    private String EN_NAME;
    private String ITEM_TYPE;
    private String WRITE_IS_ENABLED;
    private Object DISPLAY_TYPE;
    private Object REMARK;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getORDER_NO() {
        return ORDER_NO;
    }

    public void setORDER_NO(int ORDER_NO) {
        this.ORDER_NO = ORDER_NO;
    }

    public String getPROTOCOL_OBIS() {
        return PROTOCOL_OBIS;
    }

    public void setPROTOCOL_OBIS(String PROTOCOL_OBIS) {
        this.PROTOCOL_OBIS = PROTOCOL_OBIS;
    }

    public String getPROTOCOL_ID() {
        return PROTOCOL_ID;
    }

    public void setPROTOCOL_ID(String PROTOCOL_ID) {
        this.PROTOCOL_ID = PROTOCOL_ID;
    }

    public String getCN_NAME() {
        return CN_NAME;
    }

    public void setCN_NAME(String CN_NAME) {
        this.CN_NAME = CN_NAME;
    }

    public String getEN_NAME() {
        return EN_NAME;
    }

    public void setEN_NAME(String EN_NAME) {
        this.EN_NAME = EN_NAME;
    }

    public String getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(String ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }

    public String getWRITE_IS_ENABLED() {
        return WRITE_IS_ENABLED;
    }

    public void setWRITE_IS_ENABLED(String WRITE_IS_ENABLED) {
        this.WRITE_IS_ENABLED = WRITE_IS_ENABLED;
    }

    public Object getDISPLAY_TYPE() {
        return DISPLAY_TYPE;
    }

    public void setDISPLAY_TYPE(Object DISPLAY_TYPE) {
        this.DISPLAY_TYPE = DISPLAY_TYPE;
    }

    public Object getREMARK() {
        return REMARK;
    }

    public void setREMARK(Object REMARK) {
        this.REMARK = REMARK;
    }
}
