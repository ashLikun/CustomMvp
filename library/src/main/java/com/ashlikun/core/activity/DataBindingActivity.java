package com.ashlikun.core.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

/**
 * 作者　　: 李坤
 * 创建时间:2017/4/16 0016　11:56
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public abstract class DataBindingActivity<VDB extends ViewDataBinding> extends BaseActivity {
    protected VDB dataBind;


    public VDB getDataBind() {
        return dataBind;
    }

    @Override
    public void setActivityContentView(@LayoutRes int layoutResID) {
        dataBind = DataBindingUtil.setContentView(this, layoutResID);
    }
}
