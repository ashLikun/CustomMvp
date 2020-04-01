package com.ashlikun.core.simple

import android.os.Bundle

import com.ashlikun.core.BasePresenter
import com.ashlikun.utils.other.LogUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19　17:08
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
class MainPresenter : BasePresenter<IMainView>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val aaa = view.findSize()
        LogUtils.e("onCreate")
    }

    override fun onAttachView(mvpView: IMainView) {
        super.onAttachView(mvpView)
        LogUtils.e("onAttachView")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.e("onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.e("onStop")
    }
}
