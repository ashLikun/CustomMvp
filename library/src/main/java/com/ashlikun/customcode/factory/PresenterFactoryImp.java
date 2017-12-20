package com.ashlikun.customcode.factory;

import com.ashlikun.customcode.BasePresenter;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19　15:53
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Presenter工厂实现类,简单工厂（静态方法工厂）
 */

public class PresenterFactoryImp<P extends BasePresenter>
        implements PresenterFactory<P> {
    /**
     * 创建的Presenter的类型
     */
    private final Class<P> mClass;

    /**
     * 构造方法私有
     *
     * @param presenterClass Presenter的Class
     */
    private PresenterFactoryImp(Class<P> presenterClass) {
        this.mClass = presenterClass;
    }


    /**
     * 根据注解创建Presenter的工厂实现类
     *
     * @param viewClazz 需要创建Presenter的V层实现类
     * @param <P>       当前要创建的Presenter类型
     * @return 工厂类
     */
    public static <P extends BasePresenter> PresenterFactory<P> createFactory(Class<?> viewClazz) {
        //获取注解
        Presenter annotation = viewClazz.getAnnotation(Presenter.class);
        Class<P> _Class = null;
        if (annotation != null) {
            _Class = (Class<P>) annotation.value();
        }
        return new PresenterFactoryImp<P>(_Class);
    }

    @Override
    public P create() {
        if (mClass == null) {
            return null;
        }
        try {
            return mClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
