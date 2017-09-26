package com.tianzunchina.sample.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianzunchina.sample.R;
import com.tianzunchina.sample.widget.GVItem1;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/6.
 */

public class HomePageButtonAdapter extends BaseAdapter{

    private ArrayList<? extends GVItem1> fixedNewsColumns = new ArrayList<>();
    private Context context;

    public HomePageButtonAdapter(Context context,
                                 ArrayList<? extends GVItem1> fixedNewsColumns) {
        this.fixedNewsColumns = fixedNewsColumns;
        this.context = context;
    }

    public void setList(ArrayList<? extends GVItem1> items) {
        fixedNewsColumns = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fixedNewsColumns.size();
    }

    @Override
    public Object getItem(int position) {
        return fixedNewsColumns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomePageColumnAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_page_button, null);
            holder = new HomePageColumnAdapter.ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (HomePageColumnAdapter.ViewHolder) convertView.getTag();
        }
        GVItem1 column = fixedNewsColumns.get(position);
        holder.title.setText(column.getTitle());

        if (column.getResID() != -1) {//本地图片
            holder.image.setImageResource(column.getResID());
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView image;
    }
}
