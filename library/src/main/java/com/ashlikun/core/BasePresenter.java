package com.ashlikun.core;

import android.app.Activity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.ashlikun.core.iview.IBaseView;
import com.ashlikun.core.listener.OnDispatcherMessage;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.utils.main.ActivityUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Proxy;

/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：P层的业务
 */
public abstract class BasePresenter<T extends IBaseView> implements LifecycleObserver, OnDispatcherMessage {
    /**
     * 软引用view
     */
    WeakReference<T> mvpView;
    /**
     * 利用动态代理实现以防null
     */
    private T mProxyView;
    public Lifecycle lifecycle;

    public T getView() {
        return mProxyView;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     * <p>
     * 方法功能： 把view也创建  在onCreate之前
     * 如果fragment使用缓存，那么这个方法无视缓存
     * * 一般在fragment的时候使用这个方法注册一些监听，因为这个方法在使用缓存的情况下会与onDestroy形成对应关系
     */

    public void onAttachView(T mvpView) {
        this.mvpView = new WeakReference<>(mvpView);
        mProxyView = (T) Proxy.newProxyInstance(mvpView.getClass().getClassLoader(),
                mvpView.getClass().getInterfaces(), new MvpViewHandler(this));
    }

    /**
     * 获取观察者
     *
     * @return
     */
    public LifecycleOwner getLifecycleOwner() {
        if (mvpView != null && mvpView.get() != null && mvpView.get() instanceof LifecycleOwner) {
            return (LifecycleOwner) mvpView.get();
        }
        return null;
    }

    /**
     * 获取Activity
     *
     * @return
     */
    public Activity getActivity() {
        Activity activity = null;
        if (mvpView != null && mvpView.get() != null) {
            if (mvpView.get() instanceof Activity) {
                return (Activity) mvpView.get();
            } else if ((activity = ActivityUtils.getActivity(mvpView.get().getContext())) != null) {
                return activity;
            } else if (mvpView.get() instanceof Fragment) {
                return ((Fragment) mvpView.get()).getActivity();
            }
        }
        return activity;
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

    /**
     * 保存状态，
     *
     * @param outState
     */
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
        cancelAllHttp();
        mvpView = null;
    }


    /**
     * 销毁网络访问
     */
    public void cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this);
    }

    /**
     * UI发送过来的事件
     *
     * @param what:事件类型
     * @param bundle    事件传递的数据
     */
    @Override
    public void onDispatcherMessage(int what, Bundle bundle) {

    }


    /**
     * 显示或者隐藏时候，或者viewpager切换时候，这个方法是Fragment才有的
     *
     * @param hidden 是否隐藏
     */
    public void onHiddenChanged(boolean hidden) {

    }

    /**
     * 对应Fragment,这个方法是Fragment才有的
     * 一般是ViewPager的切换
     *
     * @param isVisibleToUser 是否选中
     */
    public void setUserVisibleHint(boolean isVisibleToUser) {

    }
}
