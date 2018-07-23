package com.ashlikun.core.iview

import android.content.Context

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService

/**
 * 作者　　: 李坤
 * 创建时间:  2016/8/8 15:38
 * 邮箱　　：496546144@qq.com
 *
 *
 * 方法功能：抽离Mvp的View
 */
interface IBaseView {
    fun getContext(): Context

    /**
     * 获取自动切换加载中布局的管理器
     */

    fun getSwitchService(): LoadSwitchService

    /**
     * 显示对话框，用于网络请求
     */
    fun showProgress(msg: String = "", isCancelable: Boolean = true)

    /**
     * 关闭加载中对话框
     */
    fun hintProgress()


    /**
     * 清空数据  一般在列表使用  但是也可以作为其他的界面使用
     */
    fun clearData()

    /**
     * 显示加载中布局
     *
     * @param data
     */
    fun showLoading(data: ContextData)

    /**
     * 显示重新加载页面
     *
     * @param data
     */
    fun showRetry(data: ContextData)

    /**
     * 显示内容页面
     */
    fun showContent()

    /**
     * 显示空页面
     *
     * @param data
     */
    fun showEmpty(data: ContextData)

    fun finish()
}
