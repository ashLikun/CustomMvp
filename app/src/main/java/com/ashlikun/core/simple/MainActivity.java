package com.ashlikun.core.simple;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ashlikun.core.activity.BaseMvpActivity;

public class MainActivity extends BaseMvpActivity<MainPresenter>
        implements IMainView {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e("MainActivity", "onCreate");
    }

    @Override
    public int getLayoutId() {
        Log.e("MainActivity", "getLayoutId");
        return R.layout.activity_main;

    }

    @Override
    public void initView() {
        Log.e("MainActivity", "initView");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, new MainFragment()).commit();
    }

    @Override
    public void parseIntent(Intent intent) {
        Log.e("MainActivity", "parseIntent" + (presenter == null));
    }

    @Override
    public void clearData() {

    }
}
