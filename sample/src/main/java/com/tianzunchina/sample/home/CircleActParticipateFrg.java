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
import com.tianzunchina.sample.model.Circler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 参与
 * Created by Administrator on 2017/4/21.
 */

public class CircleActParticipateFrg extends Fragment {

    private static final int TAKE_NUMBER = 10;
    private int circleActID;
    private List<Circler> mCircleActParticipaters;
    SOAPWebAPI webAPI = new SOAPWebAPI();
    private View view;
    CircleActParticipateAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        init();

        return view;
    }

    private void init() {
        Intent intent = getActivity().getIntent();
        CircleAct circleAct = (CircleAct) intent.getSerializableExtra("circleAct");
        circleActID = circleAct.getId();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("CircleActParticipaterActivity");
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lvCircleAct);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mCircleActParticipaters = new ArrayList<>();
        adapter = new CircleActParticipateAdapter(this.getActivity(),mCircleActParticipaters);
        recyclerView.setAdapter(adapter);

        this.onRefresh();
    }

    private class CircleActParticipateAdapter extends RecyclerView.Adapter {

        private List<Circler> lists;
        private Context context;

        CircleActParticipateAdapter(Context context, List<Circler> lists) {
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

        private class Holder extends RecyclerView.ViewHolder {
            private TextView tvName;
            private TextView tvTime;
            private ImageView iv;

            Holder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.name);
                tvTime = (TextView) itemView.findViewById(R.id.applyTime);
                iv = (ImageView) itemView.findViewById(R.id.image);
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_circle_act_participater, parent, false);
            return new  Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Holder holder1 = (Holder) holder;
            holder1.tvName.setText(lists.get(position).getName());
            holder1.tvTime.setText(lists.get(position).getApplyTimeStr());

            if(lists.get(position).getHeadImage() != null){
                Glide.with(context).load("http://218.108.93.154:8090/ImgHandler.ashx?Path=" +lists.get(position).getHeadImage())
                        .override(80, 80).error(R.drawable.ico_rank_picture).into(holder1.iv);

            }

        }
    }

    private void setData(int isRefresh, List<Circler> list) {
        if (isRefresh == 1) {
            mCircleActParticipaters.clear();
            mCircleActParticipaters.addAll(list);

        } else {
            for (Circler circler : list) {
                if (!mCircleActParticipaters.contains(circler)) {
                    mCircleActParticipaters.add(circler);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    public void onRefresh() {
        getParticipateList(0);
    }

    public void onLoadMore() {
      getParticipateList(mCircleActParticipaters.size());
    }

    private void getParticipateList(final int skip){
        TZRequest tzRequest = new TZRequest("http://218.108.93.154:8090/CirclesService.asmx","GetEvaluateMembers");
        tzRequest.addParam("CAID", circleActID);
        tzRequest.addParam("SkipNumber", skip);
        tzRequest.addParam("TakeNumber", TAKE_NUMBER);
        webAPI.call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(JSONObject jsonObject, TZRequest request) {
                try{
                    JSONObject body = jsonObject.getJSONObject("Body");
                    JSONArray jsonList = body.getJSONArray("EvaluationMembers");
                    Log.e("EvaluationMembers",jsonList.toString());

                    String count = body.getString("Count");
                    int size = jsonList.length();
                    List<Circler> circlers = new ArrayList<>();
                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            Circler circler = new Circler(jsonList.getJSONObject(i));
                            circlers.add(circler);
                        }
                    }
                    setData(skip, circlers);
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


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("CircleActParticipaterActivity")) {
                onRefresh();
            }
        }
    };

    private void sendBroadcast(String count) {
        Intent intent = new Intent();
        intent.putExtra("count", count);
        intent.putExtra("from", "CircleActParticipaterActivity");
        intent.setAction("CircleActActivity");
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }
}
