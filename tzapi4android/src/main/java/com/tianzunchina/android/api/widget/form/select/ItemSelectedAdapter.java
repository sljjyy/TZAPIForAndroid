package com.tianzunchina.android.api.widget.form.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tianzunchina.android.api.R;

import java.util.List;

public class ItemSelectedAdapter extends BaseAdapter {
	private List<ArrayAdapterItem> items;
	private int index;
	private Context context;
	private Class<?> toClass;
	private LayoutInflater mInflater;

	public ItemSelectedAdapter(Context context, Class<?> toClass, List<ArrayAdapterItem> items, int i){
		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.toClass = toClass;
		this.items = items;
		index = i;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_selected, null);
		final ArrayAdapterItem item = items.get(position);
		TextView tvItemName = (TextView) view.findViewById(R.id.tvItemName);
		tvItemName.setText(item.toString());
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, toClass);
				intent.putExtra("item", item);
				intent.putExtra("index", index);
				((Activity)context).setResult(-1, intent);
				((Activity)context).finish();
			}
		});
		return view;
	}
}