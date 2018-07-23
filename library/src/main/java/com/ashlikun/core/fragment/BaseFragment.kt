package com.ashlikun.core.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.core.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.iview.IBaseWindow
import com.ashlikun.loadswitch.*
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.supertoobar.SupperToolBar
import com.ashlikun.utils.ui.UiUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：基类fragment
 */

abstract class BaseFragment : Fragment(), IBaseWindow {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     *
     *
     * 方法功能：请求CODE
     */

    var REQUEST_CODE = Math.abs(this.javaClass.simpleName.hashCode() % 60000)


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:14
     *
     *
     * 方法功能：布局
     */

    protected lateinit var rootView: View

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
     * 创建时间: 2016/9/22 11:15
     *
     *
     * 方法功能：是否是回收利用的Fragment
     */
    protected var isRecycle = false
    protected var toolbar: SupperToolBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            isRecycle = false
            rootView = UiUtils.getInflaterView(activity!!, getLayoutId())
            toolbar = rootView.findViewById(R.id.toolbar)
            initLoadSwitch()
        } else {
            isRecycle = true
        }
        return rootView
    }

    override fun initLoadSwitch() {
        val viewSwitch = getSwitchRoot()
        if (viewSwitch != null) {
            mSwitchService = LoadSwitch.get()
                    .register(viewSwitch, DefaultOnLoadLayoutListener(context, getOnLoadSwitchClick()))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!isRecycle) {
            baseInitView()
            initView()
        }
        initData()
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/11 0011 20:23
     *
     *
     * 方法功能：初始化数据
     */
    protected fun initData() {

    }

    protected abstract fun baseInitView()

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:06
     *
     *
     * 方法功能：获取布局id
     */
    abstract override fun getLayoutId(): Int

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
        return rootView.findViewById(R.id.switchRoot)
    }

    override fun onDetach() {
        super.onDetach()
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2018/1/22 10:09
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：当activity返回的时候,需要配合activity调用
     *
     * @return false:默认不处理  true:fragment处理
     */

    fun onBackPressed(): Boolean {
        return false
    }

    override fun onLowMemory() {
        super.onLowMemory()
        cancelAllHttp()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelAllHttp()
    }

    /**
     * 显示对话框，用于网络请求
     *
     * @param msg
     * @param isCancelable
     */
    fun showProgress(msg: String = "", isCancelable: Boolean = false) {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showProgress(msg, isCancelable)
        }
    }

    fun hintProgress() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hintProgress()
        }
    }

    override fun showLoading(data: ContextData) {
        if (mSwitchService != null) {
            mSwitchService!!.showLoading(data)
        }
    }

    override fun getSwitchService(): LoadSwitchService? {
        return mSwitchService
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
        if (activity != null && !activity!!.isFinishing) {
            activity!!.finish()
        }
    }

    /**
     * 销毁网络访问
     */
    override fun cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this)
    }
}
