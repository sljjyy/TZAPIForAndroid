package com.tianzunchina.android.api.widget.form.select;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tianzunchina.android.api.R;

import java.util.List;

/**
 * admin
 * 2016/6/29 0029.
 */

public class ItemSelectedBottomSheetDialog extends BottomSheetDialog implements
        BaseQuickAdapter.OnRecyclerViewItemClickListener{
    private List<ArrayAdapterItem> items;
    private ItemSelectedCallBack callBack;

    public ItemSelectedBottomSheetDialog(@NonNull Context context, List<ArrayAdapterItem> items) {
        super(context);
        this.items = items;
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_dialog, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvBottomSheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemSelectedRecyclerAdapter adapter= new ItemSelectedRecyclerAdapter(R.layout.item_selected, items);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(false);
        adapter.setOnRecyclerViewItemClickListener(this);
        recyclerView.setAdapter(adapter);
        this.setContentView(view);
    }

    public void setCallBack(ItemSelectedCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onItemClick(View view, int i) {
        if(callBack != null){
            callBack.select(items.get(i));
        }
        dismiss();
    }
}
