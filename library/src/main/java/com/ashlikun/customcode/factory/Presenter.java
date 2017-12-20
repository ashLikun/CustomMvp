package com.ashlikun.customcode.factory;

import com.ashlikun.customcode.BasePresenter;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19　15:49
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Presenter的声明接口
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Presenter {
    Class<? extends BasePresenter> value();
}
