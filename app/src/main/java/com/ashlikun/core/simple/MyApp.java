package com.ashlikun.core.simple;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.utils.AppUtils;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/1/22　13:52
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class MyApp extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setDebug(true);
        AppUtils.init(this);
        ARouter.init(this);
    }
}
