package com.ashlikun.core.iview;

import android.view.View;

import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.OnLoadSwitchClick;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:36
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：一个窗口要实现的接口
 */

public interface IBaseWindow {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    public abstract int getLayoutId();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    public abstract void initView();

    /**
     * 获取需要转化为{@link LoadSwitchService}的控件
     *
     * @return
     */
    public View getSwitchRoot();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    OnLoadSwitchClick getOnLoadSwitchClick();

    /**
     * 获取自动切换加载中布局的管理器
     */

    LoadSwitchService getSwitchService();

    /**
     * 显示加载中布局
     *
     * @param data
     */
    public void showLoading(ContextData data);

    /**
     * 初始化布局切换的管理器
     */
    public void initLoadSwitch();

    /**
     * 显示重新加载页面
     *
     * @param data
     */
    public void showRetry(ContextData data);

    /**
     * 显示内容页面
     */
    public void showContent();

    /**
     * 显示空页面
     *
     * @param data
     */
    public void showEmpty(ContextData data);

    /**
     * 销毁页面
     */
    public void finish();

    /**
     * 销毁网络访问
     */
    public void cancelAllHttp();
}
