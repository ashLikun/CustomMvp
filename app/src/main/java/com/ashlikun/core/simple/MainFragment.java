package com.ashlikun.core.simple;

import android.util.Log;

import com.ashlikun.core.fragment.BaseMvpFragment;
import com.ashlikun.core.simple.databinding.ActivityMainBinding;

public class MainFragment extends BaseMvpFragment<MainFragmentPresenter, ActivityMainBinding>
        implements IMainView {


    @Override
    public int getLayoutId() {
        Log.e("MainFragment", "getLayoutId");
        return R.layout.activity_main;

    }

    @Override
    public void initView() {
        Log.e("MainFragment", "initView");
    }


    @Override
    public void clearData() {

    }
}
