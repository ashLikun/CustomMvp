package com.ashlikun.core.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.factory.PresenterFactoryImp;
import com.ashlikun.core.iview.BaseView;

import java.lang.reflect.ParameterizedType;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:34
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：封装MvpFragment
 */


public abstract class BaseMvpFragment<P extends BasePresenter, DB extends ViewDataBinding> extends
        DataBindingFragment<DB> {

    public P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            if (this instanceof BaseView) {
                if (presenter != null) {
                    presenter.onAttachView((BaseView) this);
                    presenter.onCreate(savedInstanceState);
                }
            } else {
                new Exception("BaseMvpFragment 必须实现 BaseView");
            }
        }
    }

    @Override
    protected void baseInitView() {
        presenter = initPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            presenter.onSaveInstanceState(outState);
        }
    }

    /**
     * 实例化Presenter对象
     * 优先使用注解,如果没注解，就调用这个方法
     *
     * @return 生成Presenter
     */
    public P initPresenter() {
        P presenter = null;
        if (presenter == null) {
            presenter = PresenterFactoryImp.<P>createFactory(getClass()).create();
        }
        if (presenter == null) {
            if (this instanceof BaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType
                    && ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments().length > 0) {
                Class _c = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                try {
                    presenter = (P) _c.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (presenter == null) {
            throw new RuntimeException("Presenter创建失败!检查是否声明了@Presenter(XXX.class)注解  或者 从写initPresenter方法 或者当前View的泛型没用Presenter");
        }
        return presenter;
    }
}
