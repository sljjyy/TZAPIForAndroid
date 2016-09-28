package com.tianzunchina.android.api.widget.form.select;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianzunchina.android.api.R;

import java.util.List;

/**
 * admin
 * 2016/6/30 0030.
 */

public class ItemSelectedRecyclerAdapter extends BaseQuickAdapter<ArrayAdapterItem> {

    public ItemSelectedRecyclerAdapter(int layoutResId, List<ArrayAdapterItem> data) {
        super(layoutResId, data);
    }

    public ItemSelectedRecyclerAdapter(List<ArrayAdapterItem> data) {
        super(data);
    }

    public ItemSelectedRecyclerAdapter(View contentView, List<ArrayAdapterItem> data) {
        super(contentView, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ArrayAdapterItem arrayAdapterItem) {
        baseViewHolder.setText(R.id.tvItemName, arrayAdapterItem.getVal());
    }
}
