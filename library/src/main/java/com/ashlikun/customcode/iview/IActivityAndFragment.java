package com.ashlikun.customcode.iview;

import android.view.View;

import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.OnLoadSwitchClick;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:36
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：抽离activity和fragment的公共方法
 */

public interface IActivityAndFragment {
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
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取页面切换的布局管理器
     */
    public LoadSwitchService getLoadSwitchService();

    //获取需要转化为{@link LoadSwitchService}的控件
    public View getSwitchRoot();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    OnLoadSwitchClick getOnLoadSwitchClick();

}
