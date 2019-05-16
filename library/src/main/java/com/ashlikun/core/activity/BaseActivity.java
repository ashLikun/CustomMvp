package com.ashlikun.core.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.core.R;
import com.ashlikun.core.listener.IBaseWindow;
import com.ashlikun.core.listener.OnDispatcherMessage;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.DefaultOnLoadLayoutListener;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.supertoobar.SuperToolBar;
import com.ashlikun.utils.ui.StatusBarCompat;

/**
 * @author　　: 李坤
 * 创建时间: 2018/8/8 15:56
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：自定义最顶层的Activity
 * @see IBaseWindow : 只要是窗口都会实现这个统一接口
 * @see OnDispatcherMessage : Activity处理其他(Fragment)发送的事件
 */

public abstract class BaseActivity extends AppCompatActivity implements IBaseWindow, OnDispatcherMessage {
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
    protected SuperToolBar toolbar;
    protected StatusBarCompat statusBar;

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
            statusBar = new StatusBarCompat(this);
            //设置状态栏颜色兼容,默认只是颜色
            if (isStatusTranslucent() || isStatusTranslucentAndroidMHalf()) {
                statusBar.translucentStatusBar(isStatusTranslucentAndroidMHalf());
            } else {
                statusBar.setStatusBarColor(getStatusBarColor());
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
        toolbar = f(R.id.toolbar);
        initLoadSwitch();
    }

    @Override
    public <T extends View> T f(@IdRes int id) {
        return findViewById(id);
    }

    public StatusBarCompat getStatusBar() {
        return statusBar;
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
        //获取主题颜色
        TypedArray array = getTheme().obtainStyledAttributes(new int[]{R.attr.statusColorCustom});
        int color = array.getColor(0, 0xffffffff);
        array.recycle();
        return color;
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
     * @author　　: 李坤
     * 创建时间: 2019/3/1 0:05
     * 邮箱　　：496546144@qq.com
     * <p>
     * 功能介绍：6.0以下是否绘制半透明,因为不能设置状态栏字体颜色
     */

    public boolean isStatusTranslucentAndroidMHalf() {
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
        return f(R.id.switchRoot);
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

    /**
     * 处理fragment发送过来的事件
     *
     * @param what:事件类型
     * @param bundle    事件传递的数据
     */
    @Override
    public void onDispatcherMessage(int what, Bundle bundle) {

    }

    public boolean getUserVisibleHint() {
        return true;
    }
}
