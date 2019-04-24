package com.hx.upgrade.eventBus;

/**
 * Created by
 * HEN022 on 2018/6/28.
 */


public class EventBase {
    //消息
    private String message;

    public EventBase() {
    }
    public EventBase(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
