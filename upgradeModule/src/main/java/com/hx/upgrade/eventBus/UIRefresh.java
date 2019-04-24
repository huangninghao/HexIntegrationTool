package com.hx.upgrade.eventBus;

public class UIRefresh extends EventBase {
    private String toast;//UI界面 toast提示信息，空表示不作处理
    private String progress; //UI界面 progress显示信息，空表示不作处理
    private boolean tag; //UI界面 progress显示(true)/隐藏(false)

    public UIRefresh(String toast, String progress, boolean tag) {
        super();
        this.progress = progress;
        this.tag = tag;
        this.toast = toast;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }
}
