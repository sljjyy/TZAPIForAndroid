package com.tianzunchina.android.api.widget.photo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPageViewAdapter extends PagerAdapter {
	private List<View> pages;

	public ViewPageViewAdapter(List<View> pages) {
		super();
		this.pages = pages;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(pages.get(position));
		return pages.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
