package com.ashlikun.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;

import com.ashlikun.core.iview.BaseView;
import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.utils.other.LogUtils;

import java.util.HashSet;
import java.util.Set;

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
     * 创建时间: 2016/9/22 11:13
     * 方法功能：http请求执行后的集合   方便销毁
     */
    private Set<ExecuteCall> mHttpCalls;

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
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能：当P创建的时候
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/5/27 10:40
     * <p>
     * 方法功能：每调用一个请求添加
     */

    public void addHttpCall(ExecuteCall s) {
        if (this.mHttpCalls == null) {
            this.mHttpCalls = new HashSet<>();
        }
        this.mHttpCalls.add(s);
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

    //恢复状态，在onCreate之前
    public void onCreateTosavedState(Bundle savedInstanceState) {

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
    private void cancelAllHttp() {
        if (mHttpCalls != null) {
            for (ExecuteCall call : mHttpCalls) {
                if (!call.isCompleted() && !call.isCanceled()) {
                    call.cancel();
                }
            }
            mHttpCalls.clear();
        }
    }
}
