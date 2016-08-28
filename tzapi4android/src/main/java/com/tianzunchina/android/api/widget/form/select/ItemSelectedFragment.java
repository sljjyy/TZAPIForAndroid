package com.tianzunchina.android.api.widget.form.select;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.view.list.TZCommonAdapter;
import com.tianzunchina.android.api.view.list.TZViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemSelectedFragment extends Fragment implements AdapterView.OnItemClickListener{
	private List<ArrayAdapterItem> items = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_item_selected, container, false);
		init(view);
		return view;
	}

	private void init(View view){
		initIntent(view);
		ListView lvSelected = (ListView) view.findViewById(R.id.lvSelected);
		TextView tvBack = (TextView) view.findViewById(R.id.tvBack);
		TextView tvList = (TextView) view.findViewById(R.id.tvList);

		TZCommonAdapter iSAdapter = new TZCommonAdapter<ArrayAdapterItem>(getActivity(), items, R.layout.item_selected) {
			@Override
			public void convert(TZViewHolder holder, ArrayAdapterItem arrayAdapterItem, int position) {
				holder.setText(R.id.tvItemName, arrayAdapterItem.toString());
			}
		};
		lvSelected.setAdapter(iSAdapter);
		lvSelected.setOnItemClickListener(this);
		tvBack.setVisibility(View.INVISIBLE);
		tvList.setVisibility(View.INVISIBLE);
	}
	
	@SuppressWarnings("unchecked")
	private void initIntent(View view){
		Intent intent = getActivity().getIntent();
		items = (List<ArrayAdapterItem>)intent.getSerializableExtra("items");
		Class<?> toClass = (Class<?>) intent.getSerializableExtra("toClass");
		int index = intent.getIntExtra("index", 0);
		String title = intent.getStringExtra("title");
		boolean backClose = intent.getBooleanExtra("backClose", true);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		if(title != null){
			tvTitle.setText(title);
		} else {
			tvTitle.setText("社工爆料");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

	}
}
