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
import com.tianzunchina.sample.model.CircleEvalAct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * 打分
 * Created by Administrator on 2017/4/21.
 */

public class CircleActEvalActListFrg extends Fragment {

    private static final int takeNumber = 10;
    private int circleActID;
    private List<CircleEvalAct> circleEvalActs;
    private CircleActEvalActListAdapter adapter;
    private final static String[] SCORE_STRS = new String[]{"活动评价为0分，不能更差劲了！", "活动评价为1分，差劲了！", "活动评价为2分，不给力啊！",
            "活动评价为3分，马马虎虎啦！", "活动评价为4分，还不错啦！", "活动评价为5分，太赞了！"};
    private CircleActEvalActListBroadcastReceiver mBroadcastReceiver = null;
    SOAPWebAPI webAPI = new SOAPWebAPI();
    View view;

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

        mBroadcastReceiver = new CircleActEvalActListBroadcastReceiver();
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(
                "CircleActEvalActListActivity"));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lvCircleAct);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        circleEvalActs = new ArrayList<>();
        adapter = new CircleActEvalActListAdapter(getActivity(),circleEvalActs);
        recyclerView.setAdapter(adapter);

        this.onRefresh();
    }

    public void setData(int isRefresh, List<CircleEvalAct> list) {
        if (isRefresh == 1) {
            circleEvalActs.clear();
            circleEvalActs.addAll(list);

        } else {
            for (CircleEvalAct circleEvalAct : list) {
                if (!circleEvalActs.contains(circleEvalAct)) {
                    circleEvalActs.add(circleEvalAct);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void onRefresh() {
        getCircleEvalActList(0);
    }

    public void  onLoadMore() {
        getCircleEvalActList(circleEvalActs.size());

    }

    private void getCircleEvalActList(final int skip){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetEvaluatedActivityList");
        tzRequest.addParam("CAID", circleActID);
        tzRequest.addParam("SkipNumber", skip);
        tzRequest.addParam("TakeNumber", takeNumber);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try {
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("CircleEvaluations");
                    Log.e("CircleEvaluations",jsonList.toString());
                    String count = body.getString("Count");
                    int size = jsonList.length();
                    List<CircleEvalAct> circleEvalActs = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            CircleEvalAct circleEvalAct = new CircleEvalAct(
                                    jsonList.getJSONObject(i));
                            circleEvalActs.add(circleEvalAct);
                        }
                    }
                    setData(skip,circleEvalActs);
                    sendBroadcast(count);
                }catch (JSONException e){
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

    private class CircleActEvalActListBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("CircleActEvalActListActivity")) {
                onRefresh();
            }
        }
    }

    private void sendBroadcast(String count) {
        Intent intent = new Intent();
        intent.putExtra("count", count);
        intent.putExtra("from", "CircleActEvalActListActivity");
        intent.setAction("CircleActActivity");
        getActivity().sendBroadcast(intent);
    }

    private class CircleActEvalActListAdapter extends RecyclerView.Adapter {

        private List<CircleEvalAct> lists;
        private Context context;

        CircleActEvalActListAdapter(Context context, List<CircleEvalAct> lists) {
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
            CircleActEvalActListAdapter.Holder holder1 = (CircleActEvalActListAdapter.Holder) holder;
            holder1.tvName.setText(lists.get(position).getName());
            holder1.tvTime.setText(lists.get(position).getTimeTimeStr());
            holder1.tvContent.setText(SCORE_STRS[lists.get(position).getScore()] + lists.get(position).getContent());

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

}
