package com.ashlikun.core.listener;

import android.os.Bundle;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/8/8　15:48
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Activity处理接口事件（如fragment发生一条事件给Activity处理）
 */
public interface OnDispatcherMessage {
    /**
     * 处理回掉事件
     *
     * @param what:事件类型
     * @param bundle    事件传递的数据
     */
    void onDispatcherMessage(int what, Bundle bundle);
}
