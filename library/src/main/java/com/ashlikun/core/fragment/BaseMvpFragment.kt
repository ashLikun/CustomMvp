package com.ashlikun.core.fragment

import android.os.Bundle

import com.ashlikun.core.BasePresenter
import com.ashlikun.core.factory.PresenterFactoryImp
import com.ashlikun.core.iview.IBaseView

import java.lang.reflect.ParameterizedType

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:34
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：封装MvpFragment
 */


abstract class BaseMvpFragment<P : BasePresenter<out IBaseView>> : BaseFragment() {

    lateinit var presenter: P

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isRecycle) {
            if (this is IBaseView) {
                if (presenter != null) {
                    presenter.onAttachView(this as Nothing)
                    presenter.onCreate(savedInstanceState)
                }
            } else {
                Exception("BaseMvpFragment 必须实现 BaseView")
            }
        }
    }

    override fun baseInitView() {
        presenter = initPresenter()
        lifecycle.addObserver(presenter!!)
        presenter.lifecycle = lifecycle
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (presenter != null) {
            presenter.onSaveInstanceState(outState)
        }
    }

    /**
     * 实例化Presenter对象
     * 优先使用注解,如果没注解，就调用这个方法
     *
     * @return 生成Presenter
     */
    fun initPresenter(): P {
        var p: P? = null;
        if (p == null) {
            p = PresenterFactoryImp.createFactory<P>(javaClass).create()
        }
        if (p == null) {
            if (this is IBaseView && this.javaClass.genericSuperclass is ParameterizedType
                    && (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.size > 0) {
                val _c = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>
                try {
                    p = _c.newInstance() as P
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
        if (p == null) {
            throw RuntimeException("Presenter创建失败!检查是否声明了@Presenter(XXX.class)注解  或者 从写initPresenter方法 或者当前View的泛型没用Presenter")
        }
        return p
    }
}
