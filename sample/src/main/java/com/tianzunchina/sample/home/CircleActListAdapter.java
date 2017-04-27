package com.tianzunchina.sample.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.tianzunchina.android.api.view.list.TZCommonAdapter;
import com.tianzunchina.android.api.view.list.TZViewHolder;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.CircleAct;

import java.util.List;

/**
 * 所有活动适配器
 * 我的活动适配器
 */
public class CircleActListAdapter extends TZCommonAdapter<CircleAct> {

    private Context mContext;
    private List<CircleAct> mLists;

    private static final int TYPE_WITHOUT_PHOTO = 0;
    private static final int TYPE_WITH_PHOTO_ONE = 1;
    private static final int TYPE_WITH_PHOTO_TWO = 2;
    private static final int TYPE_WITH_PHOTO_THREE = 3;
    private static final String PICTURE_URL = "http://218.108.93.154:8090/ImgHandler.ashx?Path=";

    public CircleActListAdapter(Context context, List<CircleAct> datas, int layoutId) {
        super(context, datas, layoutId);
        this.mContext = context;
        this.mLists = datas;
    }

    @Override
    public void convert(TZViewHolder holder, final CircleAct circleAct, int position) {
        int type = getItemViewType(position);

        holder.setText(R.id.circle_title, circleAct.getCircleName());
        holder.setText(R.id.circle_act_createTime, circleAct.getcreateTimeTimeStr());
        holder.setText(R.id.circle_act_title, circleAct.getTitle());
        holder.setText(R.id.circle_act_content, circleAct.getContent());

        holder.setCircleImage(R.id.circle_image, "http://218.108.93.154:8090/ImgHandler.ashx?Path=" + circleAct.getCircleSmallPath(), R.drawable.ico_rank_picture);

        holder.setVisible(R.id.circle_act_photo, true);
        switch (type) {
            case TYPE_WITHOUT_PHOTO:
                holder.setVisible(R.id.circle_act_photo, false);
                break;
            case TYPE_WITH_PHOTO_THREE:
                holder.setImage(R.id.ivLoverelayPhotoRight, PICTURE_URL + circleAct.getPathArray()[2]);
            case TYPE_WITH_PHOTO_TWO:
                holder.setImage(R.id.ivLoverelayPhotoMiddle, PICTURE_URL + circleAct.getPathArray()[1]);
            case TYPE_WITH_PHOTO_ONE:
                holder.setImage(R.id.ivLoverelayPhotoLeft, PICTURE_URL + circleAct.getPathArray()[0]);
                break;
        }

        holder.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CircleActActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("circleAct", circleAct);
                bundle.putBoolean("isComment", false);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        int size = mLists.get(position).getPathArray().length;
        if (size == TYPE_WITHOUT_PHOTO) {
            return TYPE_WITHOUT_PHOTO;
        } else {
            if (size == TYPE_WITH_PHOTO_ONE) {
                return TYPE_WITH_PHOTO_ONE;
            } else if (size == TYPE_WITH_PHOTO_TWO) {
                return TYPE_WITH_PHOTO_TWO;
            } else if ((size == TYPE_WITH_PHOTO_THREE)) {
                return TYPE_WITH_PHOTO_THREE;
            } else {
                return TYPE_WITHOUT_PHOTO;
            }
        }
    }

}
