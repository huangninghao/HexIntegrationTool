package com.hx.base.model;


import java.io.Serializable;

/**
 * @author by HEC271
 * on 2018/1/25.
 */
public class UserInfoEntity implements Serializable {
    private String userNumber;
    private String username;
    private String password;
    private String role;
    private String homeMenu;
    private String country;
    private int status = 1;
    private String electricity;//电力公司
    private String currency;//货币

    public String getHomeMenu() {
        return homeMenu;
    }

    public void setHomeMenu(String homeMenu) {
        this.homeMenu = homeMenu;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    private String en_name;

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    private String loginTime;

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    private boolean current = false;

    public String getUserNumber() {
        return this.userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getCurrent() {
        return this.current;
    }

    public String getElectricity() {
        return this.electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }
}
