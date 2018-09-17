package com.ashlikun.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/17　15:23
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：利用动态代理机制，处理p调用v的null指针异常
 */
public class MvpViewHandler implements InvocationHandler {
    private BasePresenter presenter;

    public MvpViewHandler(BasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果V层没被销毁, 执行V层的方法.
        if (presenter != null && presenter.mvpView != null && presenter.mvpView.get() != null) {
            return method.invoke(presenter.mvpView.get(), args);
        }
        //P层不需要关注V层的返回值
        return null;
    }
}
