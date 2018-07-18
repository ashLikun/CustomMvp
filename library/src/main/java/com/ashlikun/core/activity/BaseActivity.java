package com.ashlikun.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.core.R;
import com.ashlikun.core.iview.IBaseWindow;
import com.ashlikun.customdialog.LoadDialog;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.DefaultOnLoadLayoutListener;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.supertoobar.SupperToolBar;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.StatusBarCompat;
import com.ashlikun.utils.ui.UiUtils;

public abstract class BaseActivity extends AppCompatActivity implements IBaseWindow {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     * <p>
     * 方法功能：请求CODE
     */
    public int REQUEST_CODE = Math.abs(this.getClass().getSimpleName().hashCode() % 60000);

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：布局切换
     */
    public LoadSwitchService switchService = null;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     * <p>
     * 方法功能：用于显示提示
     */

    private LoadDialog loadDialog;

    @Nullable
    protected SupperToolBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent(getIntent());
        setActivityContentView(getLayoutId());
        setStatueBar();
        baseInitView();
        initView();
        initData();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        parseIntent(intent);
        initData();
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/5 14:38
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：设置状态栏
     */
    protected void setStatueBar() {
        if (isStatusBarEnable()) {
            //设置状态栏颜色兼容,默认只是颜色
            if (isStatusTranslucent()) {
                new StatusBarCompat(this).setTransparentBar(getStatusBarColor());
            } else {
                new StatusBarCompat(this).setColorBar(getStatusBarColor());
            }
        }
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/5 14:03
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：设置activity的布局，可以重写
     */
    protected void setActivityContentView(@LayoutRes int layoutId) {
        setContentView(layoutId);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/5 10:59
     * 邮箱　　：496546144@qq.com
     * <p>
     * 方法功能：基本的View初始化
     */
    protected void baseInitView() {
        toolbar = (SupperToolBar) findViewById(R.id.toolbar);
        UiUtils.applyFont(UiUtils.getRootView(this));
        initLoadSwitch();
    }

    /**
     * 初始化布局切换的管理器
     */
    @Override
    public void initLoadSwitch() {
        View view = getSwitchRoot();
        if (view != null) {
            switchService = LoadSwitch.get()
                    .register(view, new DefaultOnLoadLayoutListener(this, getOnLoadSwitchClick()));
        }
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
     * 创建时间: 2017/9/11 0011 20:23
     * <p>
     * 方法功能：初始化数据
     */
    protected void initData() {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     * <p>
     * 方法功能：解析意图
     */
    public void parseIntent(Intent intent) {
        ARouter.getInstance().inject(this);
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     * <p>
     * 方法功能：获取状态栏颜色
     */
    public int getStatusBarColor() {
        return R.color.colorPrimary;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/4 0004 0:01
     * <p>
     * 方法功能：设置状态栏透明度 0-255
     */

    public int getStatusBarAlpha() {
        return 255;
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/3 0003 23:39
     * <p>
     * 方法功能：内容是不是放到状态栏里面
     */

    public boolean isStatusTranslucent() {
        return false;
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     * <p>
     * 方法功能：状态栏是否开启沉浸式
     */
    public boolean isStatusBarEnable() {
        return true;
    }


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
        return findViewById(R.id.switchRoot);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllHttp();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        cancelAllHttp();
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */

    public void showProgress(String msg, boolean isCancelable) {
        // 判断是否加载对话框
        if (!isFinishing()) {
            if (loadDialog == null) {
                loadDialog = new LoadDialog(this);
            }
            loadDialog.setContent(StringUtils.dataFilter(msg, getResources().getString(R.string.loadding)));
            loadDialog.setCancelable(isCancelable);
            try {
                loadDialog.show();
            } catch (Exception e) {

            }

        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showProgress(String msg) {
        showProgress(msg, false);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：显示对话框，用于网络请求
     */
    public void showProgress() {
        showProgress(null);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:10
     * <p>
     * 方法功能：销毁对话框
     */
    public void hintProgress() {
        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }
    }

    @Override
    public LoadSwitchService getSwitchService() {
        return switchService;
    }

    @Override
    public void showLoading(ContextData data) {
        if (switchService != null) {
            switchService.showLoading(data);
        }
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
        super.finish();
    }

    /**
     * 销毁网络访问
     */
    @Override
    public void cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
