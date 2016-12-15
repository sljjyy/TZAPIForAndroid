package com.tianzunchina.android.api.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.tianzunchina.android.api.view.list.TZViewHolder;

import java.util.List;


/**
 * 通用列表适配器
 * CraetTime 2016-4-6
 * @author SunLiang
 */
public abstract class TZRecyclerViewAdapter<T> extends  RecyclerView.Adapter<TZRecyclerViewHolder> {

    protected Context mContext;
    protected List<T> mDatas;
    private int layoutId;


    /**
     * @param context
     * @param datas 列表数据
     * @param layoutId item布局
     */
    public TZRecyclerViewAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public TZRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TZRecyclerViewHolder(mContext, parent, layoutId);
    }

    @Override
    public void onBindViewHolder(TZRecyclerViewHolder holder, int position) {
        convert(holder, getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return  mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public abstract void convert(TZRecyclerViewHolder holder, T t, int position);


}