package com.hx.base.mInterface.provider;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * description：
 * update by:
 * update day:
 */
public interface IFragmentProvider extends IBaseProvider {

    Fragment newInstance(Object... args);

    Activity getInstance(Object... args);

}
