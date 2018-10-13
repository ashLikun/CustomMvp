package com.ashlikun.core.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.factory.PresenterFactoryImp;
import com.ashlikun.core.iview.IBaseView;

import java.lang.reflect.ParameterizedType;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/7/3 9:54
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BaseMvpActivity<P extends BasePresenter>
        extends BaseActivity {

    public P presenter;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = initPresenter();
        if (presenter != null) {
            if (this instanceof IBaseView) {
                //时机要提前
                presenter.onAttachView((IBaseView) this);
            } else {
                new RuntimeException(this.getClass().getSimpleName() + " 必须实现 BaseView");
            }
        }
        super.onCreate(savedInstanceState);
        if (presenter != null) {
            presenter.onCreate(savedInstanceState);
        }
    }


    @Override
    public void parseIntent(Intent intent) {
        super.parseIntent(intent);
        if (presenter != null) {
            presenter.parseIntent(intent);
        }
    }

    @Override
    protected void baseInitView() {
        super.baseInitView();
        getLifecycle().addObserver(presenter);
        presenter.lifecycle = getLifecycle();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
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

    /**
     * 处理fragment发送过来的事件
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
