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
import com.tianzunchina.sample.widget.GVItem;

import java.util.ArrayList;

public class HomePageColumnAdapter extends BaseAdapter {
    private ArrayList<? extends GVItem> fixedNewsColumns = new ArrayList<>();
    private Context context;

    public HomePageColumnAdapter(Context context,
                                 ArrayList<? extends GVItem> fixedNewsColumns) {
        this.fixedNewsColumns = fixedNewsColumns;
        this.context = context;
    }

    public void setList(ArrayList<? extends GVItem> items) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fixed_news_column, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.tvName);
            holder.description = (TextView) convertView.findViewById(R.id.tvDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GVItem column = fixedNewsColumns.get(position);
        holder.title.setText(column.getTitle());
        holder.description.setText(column.getDescription());

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
