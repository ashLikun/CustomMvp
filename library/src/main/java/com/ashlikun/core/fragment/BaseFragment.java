package com.ashlikun.core.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.core.R;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.core.iview.IBaseWindow;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.DefaultOnLoadLayoutListener;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.supertoobar.SupperToolBar;
import com.ashlikun.utils.ui.StatusBarCompat;
import com.ashlikun.utils.ui.UiUtils;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:31
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：基类fragment
 */

public abstract class BaseFragment extends Fragment implements IBaseWindow {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：请求CODE
     */

    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:15
     * <p>
     * 方法功能：宿主activity
     */


    protected BaseActivity activity;
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：布局
     */

    protected View rootView;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：布局切换
     */
    public LoadSwitchService switchService = null;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:15
     * <p>
     * 方法功能：是否是回收利用的Fragment
     */
    protected boolean isRecycle = false;
    @Nullable
    protected SupperToolBar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
        ARouter.getInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            isRecycle = false;
            rootView = UiUtils.getInflaterView(activity, getLayoutId());
        } else {
            isRecycle = true;
        }
        return rootView;
    }

    @Override
    public void initLoadSwitch() {
        View viewSwitch = getSwitchRoot();
        if (viewSwitch != null) {
            switchService = LoadSwitch.get()
                    .register(viewSwitch, new DefaultOnLoadLayoutListener(getContext(), getOnLoadSwitchClick()));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            baseInitView();
            initView();
        }
        initData();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/11 0011 20:23
     * <p>
     * 方法功能：初始化数据
     */
    protected void initData() {

    }

    protected void baseInitView() {
        toolbar = f(R.id.toolbar);
        initLoadSwitch();
    }

    @Override
    public <T extends View> T f(@IdRes int id) {
        return rootView.findViewById(id);
    }

    public StatusBarCompat getActivityStatusBar() {
        return activity.getStatusBar();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取布局id
     */
    @Override
    public abstract int getLayoutId();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：初始化view
     */
    @Override
    public abstract void initView();


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：显示不同的界面布局 监听器
     */
    @Override
    public OnLoadSwitchClick getOnLoadSwitchClick() {
        if (this instanceof OnLoadSwitchClick) {
            return (OnLoadSwitchClick) this;
        } else {
            return null;
        }
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取需要转化为{@link LoadSwitchService}的控件
     */
    @Override
    public View getSwitchRoot() {
        return f(R.id.switchRoot);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2018/1/22 10:09
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：当activity返回的时候,需要配合activity调用
     *
     * @return false:默认不处理  true:fragment处理
     */

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        cancelAllHttp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAllHttp();
    }

    /**
     * 显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    public void showProgress(String msg, boolean isCancelable) {
        activity.showProgress(msg, isCancelable);
    }

    public void showProgress(String msg) {
        showProgress(msg, false);
    }

    public void showProgress() {
        showProgress(null);
    }

    public void hintProgress() {
        activity.hintProgress();
    }

    @Override
    public void showLoading(ContextData data) {
        if (switchService != null) {
            switchService.showLoading(data);
        }
    }

    @Override
    public LoadSwitchService getSwitchService() {
        return switchService;
    }

    @Override
    public void showContent() {
        if (switchService != null) {
            switchService.showContent();
        }
    }

    @Override
    public void showEmpty(ContextData data) {
        if (switchService != null) {
            switchService.showEmpty(data);
        }
    }

    @Override
    public void showRetry(ContextData data) {
        if (switchService != null) {
            switchService.showRetry(data);
        }
    }

    @Override
    public void finish() {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }

    /**
     * 销毁网络访问
     */
    @Override
    public void cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
