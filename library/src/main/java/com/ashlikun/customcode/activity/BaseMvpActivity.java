package com.ashlikun.customcode.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.ashlikun.customcode.BasePresenter;
import com.ashlikun.customcode.factory.PresenterFactoryImp;
import com.ashlikun.customcode.iview.BaseView;

import java.lang.reflect.ParameterizedType;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/7/3 9:54
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BaseMvpActivity<P extends BasePresenter, VDB extends ViewDataBinding>
        extends DataBindingActivity<VDB> {

    public P presenter;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this instanceof BaseView) {
            if (presenter != null) {
                presenter.onAttachView((BaseView) this);
                presenter.onCreate(savedInstanceState);
            }
        } else {
            throw new RuntimeException(this.getClass().getSimpleName() + " 必须实现 BaseView");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this instanceof BaseView) {
            if (presenter != null) {
                presenter.onAttachView((BaseView) this);
                presenter.onCreate(null);
            }
        } else {
            new RuntimeException(this.getClass().getSimpleName() + " 必须实现 BaseView");
        }
    }


    @Override
    protected void baseInitView() {
        super.baseInitView();
        presenter = initPresenter();
    }

    @Override
    protected void onStart() {
        presenter.onStart();
        super.onStart();

    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onLowMemory() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onLowMemory();
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
