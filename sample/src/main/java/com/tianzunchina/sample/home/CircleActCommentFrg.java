package com.tianzunchina.sample.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.tianzunchina.android.api.network.SOAPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.CircleAct;
import com.tianzunchina.sample.model.CircleActComment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论
 * Created by Administrator on 2017/4/21.
 */

public class CircleActCommentFrg extends Fragment {
    private static final int takeNumber = 10;
    SOAPWebAPI webAPI = new SOAPWebAPI();
    CircleActCommListAdapter adapter;
    private int circleActID;
    private List<CircleActComment> circleActComments;
    private CircleActCommentBroadcastReceiver mBroadcastReceiver = null;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        init();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    private void init() {
        Intent intent = getActivity().getIntent();
        CircleAct circleAct = (CircleAct) intent.getSerializableExtra("circleAct");
        circleActID = circleAct.getId();

        mBroadcastReceiver = new CircleActCommentBroadcastReceiver();
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(
                "CircleActCommentActivity"));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lvCircleAct);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        circleActComments = new ArrayList<>();
        adapter = new CircleActCommListAdapter(getActivity(), circleActComments);
        recyclerView.setAdapter(adapter);

        onRefresh();

    }

    private void onRefresh() {
        getCommentList(0);

    }

    private void onLoadMore() {
        getCommentList(1);

    }

    public void setData(List<CircleActComment> list, int isRefresh) {
        if (isRefresh == 1) {
            circleActComments.clear();
            circleActComments.addAll(list);

        } else {
            for (CircleActComment circleActComment : list) {
                if (!circleActComments.contains(circleActComment)) {
                    circleActComments.add(circleActComment);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void getCommentList(final int skip) {
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx", "GetCommentedActivityList");
        tzRequest.addParam("CAID", circleActID);
        tzRequest.addParam("SkipNumber", skip);
        tzRequest.addParam("TakeNumber", takeNumber);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("CircleComments");
                    Log.e("CircleComments",jsonList.toString());
                    String count = body.getString("Count");
                    int size = jsonList.length();
                    List<CircleActComment> circleActComments = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            CircleActComment circleActComment = new CircleActComment(
                                    jsonList.getJSONObject(i));
                            circleActComments.add(circleActComment);
                        }
                    }
                    setData(circleActComments, skip);
                    sendBroadcast(count);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void success(Object response, TZRequest request) {

            }

            @Override
            public void err(String err, TZRequest request) {

            }
        });

    }

    private void sendBroadcast(String count) {
        Intent intent = new Intent();
        intent.putExtra("count", count);
        intent.putExtra("from", "CircleActCommentActivity");
        intent.setAction("CircleActActivity");
        getActivity().sendBroadcast(intent);
    }

    private class CircleActCommListAdapter extends RecyclerView.Adapter {

        private List<CircleActComment> lists;
        private Context context;

        CircleActCommListAdapter(Context context, List<CircleActComment> lists) {
            this.lists = lists;
            this.context = context;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list3, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            CircleActCommentFrg.CircleActCommListAdapter.Holder holder1 = (CircleActCommentFrg.CircleActCommListAdapter.Holder) holder;
            holder1.tvName.setText(lists.get(position).getUserName());
            holder1.tvTime.setText(lists.get(position).getCommentTimeTimeStr());
            holder1.tvContent.setText(lists.get(position).getCommentContent());

            if (lists.get(position).getHeadImage() != null) {
                Glide.with(context).load("http://218.108.93.154:8090/ImgHandler.ashx?Path=" + lists.get(position).getHeadImage())
                        .override(80, 80).error(R.drawable.ico_rank_picture).into(holder1.iv);

            }

        }

        private class Holder extends RecyclerView.ViewHolder {
            private TextView tvName;
            private TextView tvTime;
            private TextView tvContent;
            private ImageView iv;

            Holder(View itemView) {
                super(itemView);

                tvName = (TextView) itemView.findViewById(R.id.name);
                tvTime = (TextView) itemView.findViewById(R.id.time);
                tvContent = (TextView) itemView.findViewById(R.id.content);
                iv = (ImageView) itemView.findViewById(R.id.ivCircleImage);
            }

        }
    }

    private class CircleActCommentBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("CircleActCommentActivity")) {
                onRefresh();
            }
        }
    }
}
