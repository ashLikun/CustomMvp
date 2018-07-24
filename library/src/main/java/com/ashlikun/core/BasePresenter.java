package com.ashlikun.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;

import com.ashlikun.core.iview.BaseView;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.utils.other.LogUtils;

/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BasePresenter<T extends BaseView> implements LifecycleObserver {
    public T mvpView;
    public Lifecycle lifecycle;


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能： 把view也创建  在onCreate之前
     */

    public void onAttachView(T mvpView) {
        this.mvpView = mvpView;
    }

    /**
     * 解析意图数据
     */
    public void parseIntent(Intent intent) {
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能：当P创建的时候
     */
    public void onCreate(Bundle savedInstanceState) {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
    }

    //保存状态，
    public void onSaveInstanceState(Bundle outState) {

    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能：销毁
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtils.e("onDestroy");
        cancelAllHttp();
        mvpView = null;
    }


    /**
     * 销毁网络访问
     */
    public void cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
