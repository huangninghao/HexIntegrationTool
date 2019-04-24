package com.hx.base.mInterface.config;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * update by:
 * update day:
 */
public class HexBundle {
    private Bundle bundle;

    public HexBundle() {
        bundle = new Bundle();
    }

    public HexBundle put(String key, int value) {
        bundle.putInt(key, value);
        return this;
    }

    public HexBundle put(String key, long value) {
        bundle.putLong(key, value);
        return this;
    }

    public HexBundle put(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return this;
        }
        bundle.putString(key, value);
        return this;
    }

    public HexBundle put(String key, Serializable value) {
        if (value == null) {
            return this;
        }
        bundle.putSerializable(key, value);
        return this;
    }

    public HexBundle put(String key, Parcelable value) {
        if (value == null) {
            return this;
        }
        bundle.putParcelable(key, value);
        return this;
    }

    public HexBundle put(String key, String[] arrays) {
        if (arrays == null) {
            return this;
        }
        bundle.putStringArray(key, arrays);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}
