package com.ashlikun.customcode.factory;

import com.ashlikun.customcode.BasePresenter;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19　15:53
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Presenter工厂模式的接口
 */

public interface PresenterFactory<P extends BasePresenter> {
    P create();
}
