package com.ashlikun.core;

import android.content.Context;

import com.ashlikun.core.listener.IBaseWindow;
import com.ashlikun.loadswitch.DefaultOnLoadLayoutListener;
import com.ashlikun.loadswitch.OnLoadLayoutListener;
import com.ashlikun.loadswitch.OnLoadSwitchClick;

import java.lang.reflect.Constructor;

/**
 * 作者　　: 李坤
 * 创建时间: 2019/5/24　14:43
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class MvpUtils {
    /**
     * 布局切换的布局渲染事件,必须有双参数构造方法
     */
    public static Class<? extends OnLoadLayoutListener> switchLayoutListener = null;


    public static void init(Class<? extends OnLoadLayoutListener> listener) {
        switchLayoutListener = listener;
    }

    /**
     * 通过反射 获取设置的全局OnLoadLayoutListener
     * @param context
     * @param window
     * @return
     */
    public static OnLoadLayoutListener getSwitchLayoutListener(Context context, IBaseWindow window) {
        OnLoadLayoutListener loadLayoutListener = null;
        if (switchLayoutListener != null) {
            try {
                //获取构造函数
                Constructor con = switchLayoutListener.getConstructor(Context.class, OnLoadSwitchClick.class);
                loadLayoutListener = (OnLoadLayoutListener) con.newInstance(context, window.getOnLoadSwitchClick());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (loadLayoutListener == null) {
            loadLayoutListener = new DefaultOnLoadLayoutListener(context, window.getOnLoadSwitchClick());
        }
        return loadLayoutListener;
    }

}
