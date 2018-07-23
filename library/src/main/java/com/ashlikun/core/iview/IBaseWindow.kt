package com.ashlikun.core.iview

import android.view.View

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.loadswitch.OnLoadSwitchClick

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:36
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：一个窗口要实现的接口
 */

interface IBaseWindow {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     *
     *
     * 方法功能：获取布局id
     */
    fun getLayoutId(): Int

    /**
     * 获取需要转化为[LoadSwitchService]的控件
     *
     * @return
     */
    fun getSwitchRoot(): View?

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     *
     *
     * 方法功能：显示不同的界面布局 监听器
     */
    fun getOnLoadSwitchClick(): OnLoadSwitchClick?

    /**
     * 获取自动切换加载中布局的管理器
     */

    fun getSwitchService(): LoadSwitchService?

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     *
     *
     * 方法功能：初始化view
     */
    fun initView()

    /**
     * 显示加载中布局
     *
     * @param data
     */
    fun showLoading(data: ContextData)

    /**
     * 初始化布局切换的管理器
     */
    fun initLoadSwitch()

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

    /**
     * 销毁页面
     */
    fun finish()

    /**
     * 销毁网络访问
     */
    fun cancelAllHttp()
}
