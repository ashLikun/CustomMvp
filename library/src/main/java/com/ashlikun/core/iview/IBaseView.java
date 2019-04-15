package com.ashlikun.core.iview;

import android.content.Context;

import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.LoadSwitchService;

/**
 * 作者　　: 李坤
 * 创建时间:  2016/8/8 15:38
 * 邮箱　　：496546144@qq.com
 * <p>
 * 方法功能：抽离Mvp的View
 */
public interface IBaseView {
    Context getContext();


    /**
     * 清空数据  一般在列表使用  但是也可以作为其他的界面使用
     */
    void clearData();

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
     * Fragment是否选中，只有Fragment才有
     * 一般是ViewPager嵌套Fragment
     *
     * @return true:选中，false：未选中
     */
    public boolean getUserVisibleHint();

    void finish();

}
