package com.ashlikun.core.activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.core.R
import com.ashlikun.core.iview.IBaseWindow
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.loadswitch.*
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.supertoobar.SupperToolBar
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.StatusBarCompat

abstract class BaseActivity : AppCompatActivity(), IBaseWindow {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     *
     *
     * 方法功能：请求CODE
     */
    var REQUEST_CODE = Math.abs(this.javaClass.getSimpleName().hashCode() % 60000)

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     *
     *
     * 方法功能：布局切换
     */
    var mSwitchService: LoadSwitchService? = null

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:13
     *
     *
     * 方法功能：用于显示提示
     */

    private var loadDialog: LoadDialog? = null

    protected var toolbar: SupperToolBar? = null


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     *
     *
     * 方法功能：获取状态栏颜色
     */
    val statusBarColor: Int
        get() = R.color.colorPrimary

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/4 0004 0:01
     *
     *
     * 方法功能：设置状态栏透明度 0-255
     */

    val statusBarAlpha: Int
        get() = 255

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/3 0003 23:39
     *
     *
     * 方法功能：内容是不是放到状态栏里面
     */

    val isStatusTranslucent: Boolean
        get() = false


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     *
     *
     * 方法功能：状态栏是否开启沉浸式
     */
    val isStatusBarEnable: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseIntent(intent)
        setActivityContentView(getLayoutId())
        setStatueBar()
        baseInitView()
        initView()
        initData()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        parseIntent(intent)
        initData()
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/5 14:38
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：设置状态栏
     */
    protected fun setStatueBar() {
        if (isStatusBarEnable) {
            //设置状态栏颜色兼容,默认只是颜色
            if (isStatusTranslucent) {
                StatusBarCompat(this).setTransparentBar(statusBarColor)
            } else {
                StatusBarCompat(this).setColorBar(statusBarColor)
            }
        }
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/5 14:03
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：设置activity的布局，可以重写
     */
    protected fun setActivityContentView(@LayoutRes layoutId: Int) {
        setContentView(layoutId)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/5 10:59
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：基本的View初始化
     */
    protected open fun baseInitView() {
        toolbar = findViewById(R.id.toolbar)
        initLoadSwitch()
    }

    /**
     * 初始化布局切换的管理器
     */
    override fun initLoadSwitch() {
        val view = getSwitchRoot();
        if (view != null) {
            mSwitchService = LoadSwitch.get()
                    .register(view, DefaultOnLoadLayoutListener(this, getOnLoadSwitchClick()))
        }
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     *
     *
     * 方法功能：初始化view
     */
    abstract override fun initView()

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/11 0011 20:23
     *
     *
     * 方法功能：初始化数据
     */
    protected fun initData() {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:16
     *
     *
     * 方法功能：解析意图
     */
    open fun parseIntent(intent: Intent) {
        ARouter.getInstance().inject(this)
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     *
     *
     * 方法功能：显示不同的界面布局 监听器
     */
    override fun getOnLoadSwitchClick(): OnLoadSwitchClick? {
        return if (this is OnLoadSwitchClick) {
            this
        } else {
            null
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:07
     *
     *
     * 方法功能：获取需要转化为[LoadSwitchService]的控件
     */
    override fun getSwitchRoot(): View? {
        return findViewById(R.id.switchRoot)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelAllHttp()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        cancelAllHttp()
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:10
     *
     *
     * 方法功能：显示对话框，用于网络请求
     */

    fun showProgress(msg: String = "", isCancelable: Boolean = false) {
        // 判断是否加载对话框
        if (!isFinishing) {
            if (loadDialog == null) {
                loadDialog = LoadDialog(this)
            }
            loadDialog!!.setContent(StringUtils.dataFilter(msg, resources.getString(R.string.loadding)))
            loadDialog!!.setCancelable(isCancelable)
            try {
                loadDialog!!.show()
            } catch (e: Exception) {

            }

        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:10
     *
     *
     * 方法功能：销毁对话框
     */
    fun hintProgress() {
        if (loadDialog != null) {
            loadDialog!!.dismiss()
            loadDialog = null
        }
    }

    override fun getSwitchService(): LoadSwitchService? {
        return mSwitchService
    }

    override fun showLoading(data: ContextData) {
        if (mSwitchService != null) {
            mSwitchService!!.showLoading(data)
        }
    }

    override fun showContent() {
        if (mSwitchService != null) {
            mSwitchService!!.showContent()
        }
    }

    override fun showEmpty(data: ContextData) {
        if (mSwitchService != null) {
            mSwitchService!!.showEmpty(data)
        }
    }

    override fun showRetry(data: ContextData) {
        if (mSwitchService != null) {
            mSwitchService!!.showRetry(data)
        }
    }

    override fun finish() {
        super.finish()
    }

    /**
     * 销毁网络访问
     */
    override fun cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this)
    }
}
/**
 * 作者　　: 李坤
 * 创建时间: 2016/9/22 11:10
 *
 *
 * 方法功能：显示对话框，用于网络请求
 */
/**
 * 作者　　: 李坤
 * 创建时间: 2016/9/22 11:10
 *
 *
 * 方法功能：显示对话框，用于网络请求
 */
