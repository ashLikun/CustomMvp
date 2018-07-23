package com.ashlikun.core

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Intent
import android.os.Bundle

import com.ashlikun.core.iview.IBaseView
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.utils.other.LogUtils

/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
abstract class BasePresenter<T : IBaseView> : LifecycleObserver {
    var mvpView: T? = null
        private set
    var lifecycle: Lifecycle? = null


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     *
     *
     * 方法功能： 把view也创建  在onCreate之前
     */

    open fun onAttachView(mvpView: Nothing) {
        this.mvpView = mvpView
    }

    /**
     * 解析意图数据
     */
    fun parseIntent(intent: Intent) {}

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     *
     *
     * 方法功能：当P创建的时候
     */
    open fun onCreate(savedInstanceState: Bundle?) {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    //保存状态，
    fun onSaveInstanceState(outState: Bundle) {

    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:02
     *
     *
     * 方法功能：销毁
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        LogUtils.e("onDestroy")
        cancelAllHttp()
        mvpView = null
    }


    /**
     * 销毁网络访问
     */
    fun cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this)
    }

}
