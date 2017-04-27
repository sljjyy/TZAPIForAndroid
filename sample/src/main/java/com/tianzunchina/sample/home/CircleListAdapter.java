package com.tianzunchina.sample.home;

import android.content.Context;

import com.tianzunchina.android.api.view.list.TZCommonAdapter;
import com.tianzunchina.android.api.view.list.TZViewHolder;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.Circle;

import java.util.List;

public class CircleListAdapter extends TZCommonAdapter<Circle> {
    public static final int HBQY_CIRCLE_ID = 190;
    private boolean isRank;
    private String countDetail = "圈友$1名，活动$2次";
    private String rankCountDetail = "圈友$1名，活动$2次,排名第$3";

    public CircleListAdapter(Context context, List<Circle> datas, int layoutId, boolean isRank) {
        super(context, datas, layoutId );
        this.isRank = isRank;
        if (isRank) {
            countDetail = rankCountDetail;
        }
    }

    @Override
    public void convert(TZViewHolder holder, Circle circle, int position) {
        holder.setCircleImage(R.id.image, "http://218.108.93.154:8090/ImgHandler.ashx?Path=" + circle.getSmallPath(), R.drawable.ico_loading_photo);
        holder.setText(R.id.tvName, circle.getTitle());
        holder.setText(R.id.tvDescription, countDetail
                .replace("$1", circle.getCircleMemCount())
                .replace("$2", circle.getActCount())
                .replace("$3", String.valueOf(position + 1)));
        holder.setText(R.id.state, circle.getIsApplyStr());
        holder.setVisible(R.id.divider, !isRank);
    }


}
