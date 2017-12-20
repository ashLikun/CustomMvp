package com.ashlikun.core.activity;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ashlikun.adapter.databind.recycleview.CommonBindAdapter;
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener;
import com.ashlikun.core.BaseListPresenter;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.R;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.xrecycleview.PagingHelp;
import com.ashlikun.xrecycleview.RefreshLayout;
import com.ashlikun.xrecycleview.StatusChangListener;
import com.ashlikun.xrecycleview.SuperRecyclerView;

import java.util.Collection;


/**
 * Created by Administrator on 2016/8/8.
 */

public abstract class BaseListActivity<P extends BasePresenter, VDB extends ViewDataBinding, D>
        extends BaseMvpActivity<P, VDB>
        implements SuperRecyclerView.ListSwipeViewListener, OnItemClickListener<D>,
        OnLoadSwitchClick {
    protected SuperRecyclerView listSwipeView;

    protected CommonBindAdapter adapter;

    public RefreshLayout getRefreshLayout() {
        return listSwipeView.getRefreshLayout();
    }

    public StatusChangListener getStatusChangListener() {
        return listSwipeView.getStatusChangListener();
    }

    @Override
    public void baseInitView() {
        super.baseInitView();
        listSwipeView = (SuperRecyclerView) findViewById(R.id.switchRoot);
        adapter = getAdapter();
        listSwipeView.getRecyclerView().addItemDecoration(getItemDecoration());
        listSwipeView.getRecyclerView().setLayoutManager(getLayoutManager());
        listSwipeView.setAdapter(adapter);
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.setOnLoaddingListener(this);
        adapter.setOnItemClickListener(this);
    }


    public abstract RecyclerView.ItemDecoration getItemDecoration();

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    public abstract CommonBindAdapter getAdapter();

    public void clearData() {
    }

    /**
     * 获取分页的有效数据
     */
    public <T> Collection<T> getValidData(Collection<T> c) {
        return listSwipeView.getPagingHelp().getValidData(c);
    }

    public RefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getRefreshLayout();
    }

    public PagingHelp getPagingHelp() {
        return listSwipeView.getPagingHelp();
    }

    public void clearPagingData() {
        listSwipeView.getPagingHelp().clear();
    }

    public int getPageindex() {
        return listSwipeView.getPagingHelp().getPageindex();
    }

    public int getPageCount() {
        return listSwipeView.getPagingHelp().getPageCount();
    }

    public void notifyChanged() {
        adapter.notifyDataSetChanged();
        if (presenter instanceof BaseListPresenter) {
            if (((BaseListPresenter) presenter).listDatas.isEmpty()) {
                loadSwitchService.showEmpty(new ContextData());
            }
        }

    }
}