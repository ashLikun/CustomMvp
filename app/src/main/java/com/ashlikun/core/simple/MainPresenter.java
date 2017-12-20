package com.ashlikun.core.simple;

import android.os.Bundle;
import android.util.Log;

import com.ashlikun.core.BasePresenter;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19　17:08
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class MainPresenter extends BasePresenter<IMainView> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MainPresenter", "onCreate");
    }

    @Override
    public void onAttachView(IMainView mvpView) {
        super.onAttachView(mvpView);
        Log.e("MainPresenter", "onAttachView");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MainPresenter", "onResume");
    }
}
