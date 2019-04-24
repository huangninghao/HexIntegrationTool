package com.hx.base.model;

import java.io.Serializable;

public class FreezeMeterBean implements Serializable{

    /**
     * ID : 1604518
     * ORDER_NO : 89
     * CLASS_ID : 4
     * PROTOCOL_OBIS : 1-0:1.6.0.255
     * ATTRIBUTE : 2\5
     * DIMENSION : 0
     * CN_NAME : 正向有功总最大需量
     * EN_NAME : Active MD (+) &&Occurring time
     * UNIT : W
     * OBIS : 1.6.0
     * READ_IS_ENABLED : 1
     * REMARK : 0.0.98.1.0.255
     * MID : 5602.0
     */

    private int ID;
    private int ORDER_NO;
    private String CLASS_ID;
    private String PROTOCOL_OBIS;
    private String ATTRIBUTE;
    private int DIMENSION;
    private String CN_NAME;
    private String EN_NAME;
    private String UNIT;
    private String OBIS;
    private String READ_IS_ENABLED;
    private String REMARK;
    private String MID;

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

    public String getCLASS_ID() {
        return CLASS_ID;
    }

    public void setCLASS_ID(String CLASS_ID) {
        this.CLASS_ID = CLASS_ID;
    }

    public String getPROTOCOL_OBIS() {
        return PROTOCOL_OBIS;
    }

    public void setPROTOCOL_OBIS(String PROTOCOL_OBIS) {
        this.PROTOCOL_OBIS = PROTOCOL_OBIS;
    }

    public String getATTRIBUTE() {
        return ATTRIBUTE;
    }

    public void setATTRIBUTE(String ATTRIBUTE) {
        this.ATTRIBUTE = ATTRIBUTE;
    }

    public int getDIMENSION() {
        return DIMENSION;
    }

    public void setDIMENSION(int DIMENSION) {
        this.DIMENSION = DIMENSION;
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

    public String getUNIT() {
        return UNIT;
    }

    public void setUNIT(String UNIT) {
        this.UNIT = UNIT;
    }

    public String getOBIS() {
        return OBIS;
    }

    public void setOBIS(String OBIS) {
        this.OBIS = OBIS;
    }

    public String getREAD_IS_ENABLED() {
        return READ_IS_ENABLED;
    }

    public void setREAD_IS_ENABLED(String READ_IS_ENABLED) {
        this.READ_IS_ENABLED = READ_IS_ENABLED;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }
}
