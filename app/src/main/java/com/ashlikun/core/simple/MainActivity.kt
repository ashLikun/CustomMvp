package com.ashlikun.core.simple

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

import com.ashlikun.core.activity.BaseMvpActivity

class MainActivity : BaseMvpActivity<MainPresenter>(), IMainView {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.e("MainActivity", "onCreate")
    }

    override fun getLayoutId(): Int {
        Log.e("MainActivity", "getLayoutId")
        return R.layout.activity_main

    }

    override fun initView() {
        Log.e("MainActivity", "initView")
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment, MainFragment()).commit()
    }

    override fun parseIntent(intent: Intent) {
        Log.e("MainActivity", "parseIntent" + (presenter == null))
    }

    override fun clearData() {

    }

    override fun findSize() = 111
}
