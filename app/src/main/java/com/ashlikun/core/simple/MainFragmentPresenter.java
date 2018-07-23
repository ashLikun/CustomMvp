package com.ashlikun.core.simple;

import android.os.Bundle;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.utils.other.LogUtils;

import org.jetbrains.annotations.NotNull;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19　17:08
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class MainFragmentPresenter extends BasePresenter<IMainView> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("onCreate");
    }

    @Override
    public void onAttachView(@NotNull Void mvpView) {
        super.onAttachView(mvpView);
        LogUtils.e("onAttachView");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e("onStop");
    }
}
