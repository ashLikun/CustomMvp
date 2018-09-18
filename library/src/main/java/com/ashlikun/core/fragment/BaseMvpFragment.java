package com.ashlikun.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.factory.PresenterFactoryImp;
import com.ashlikun.core.iview.IBaseView;

import java.lang.reflect.ParameterizedType;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:34
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：封装MvpFragment
 */


public abstract class BaseMvpFragment<P extends BasePresenter> extends
        BaseFragment {

    public P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            if (this instanceof IBaseView) {
                if (presenter != null) {
                    presenter.onAttachView((IBaseView) this);
                    Intent intent = new Intent();
                    if (getArguments() != null) {
                        intent.putExtras(getArguments());
                    }
                    presenter.parseIntent(intent);
                    presenter.onCreate(savedInstanceState);
                }
            } else {
                new Exception("BaseMvpFragment 必须实现 BaseView");
            }
        }
    }

    @Override
    protected void baseInitView() {
        super.baseInitView();
        presenter = initPresenter();
        getLifecycle().addObserver(presenter);
        presenter.lifecycle = getLifecycle();
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
            if (this instanceof IBaseView && this.getClass().getGenericSuperclass() instanceof ParameterizedType
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (presenter != null) {
            presenter.onHiddenChanged(hidden);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (presenter != null) {
            presenter.onHiddenChanged(!isVisibleToUser);
        }
    }

    /**
     * 处理Activity发送过来的事件
     *
     * @param what:事件类型
     * @param bundle    事件传递的数据
     */
    @Override
    public void onDispatcherMessage(int what, Bundle bundle) {
        super.onDispatcherMessage(what, bundle);
        if (presenter != null) {
            presenter.onDispatcherMessage(what, bundle);
        }
    }
}
