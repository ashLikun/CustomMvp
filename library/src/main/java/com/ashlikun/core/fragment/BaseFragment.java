package com.ashlikun.core.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.core.HttpCacheExecuteCall;
import com.ashlikun.core.R;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.core.iview.IActivityAndFragment;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.MyOnLoadLayoutListener;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.supertoobar.SupperToolBar;
import com.ashlikun.utils.ui.UiUtils;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:31
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：基类fragment
 */

public abstract class BaseFragment extends Fragment implements IActivityAndFragment {
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
    protected LoadSwitchService loadSwitchService = null;

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
            UiUtils.applyFont(rootView);
            toolbar = (SupperToolBar) rootView.findViewById(R.id.toolbar);
            View viewSwitch = getSwitchRoot();
            if (viewSwitch != null) {
                loadSwitchService = LoadSwitch.getDefault()
                        .register(viewSwitch, new MyOnLoadLayoutListener(getContext(), getOnLoadSwitchClick()));
            }
        } else {
            isRecycle = true;
        }
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            baseInitView();
            initView();
        }
    }

    protected abstract void baseInitView();

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
        return rootView.findViewById(R.id.switchRoot);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：获取页面切换的布局管理器
     */
    @Override
    public LoadSwitchService getLoadSwitchService() {
        return loadSwitchService;
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
    public void showDialog(String msg, boolean isCancelable) {
        activity.showDialog(msg, isCancelable);
    }

    public void showDialog(String msg) {
        showDialog(msg, false);
    }

    public void showDialog() {
        showDialog(null);
    }

    public void dismissDialog() {
        activity.dismissDialog();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示错误信息
     */
    public void showErrorMessage(String result) {
        activity.showErrorMessage(result);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showWarningMessage(String result) {
        activity.showWarningMessage(result);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:11
     * <p>
     * 方法功能：显示警告信息
     */
    public void showInfoMessage(String result) {
        activity.showInfoMessage(result);
    }

    public void showInfoMessage(String result, boolean isFinish) {
        activity.showInfoMessage(result, isFinish);
    }


    public void finish() {
        activity.finish();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/5/27 10:40
     * <p>
     * 方法功能：每调用一个请求添加
     */
    public void addHttpCall(ExecuteCall s) {
        HttpCacheExecuteCall.getInstance().register(this, s);
    }

    /**
     * 销毁网络访问
     */
    public void cancelAllHttp() {
        HttpCacheExecuteCall.getInstance().cancelAllToKey(this);
    }
}
