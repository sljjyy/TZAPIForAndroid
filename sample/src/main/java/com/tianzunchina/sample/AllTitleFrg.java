package com.tianzunchina.sample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 *  头部标题fragment
 * Created by Administrator on 2017/4/19.
 */
public class AllTitleFrg extends Fragment {
    LinearLayout llBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.title_bar, container, false);
        llBack = (LinearLayout) view.findViewById(R.id.llBack);
        llBack.setOnClickListener(v -> getActivity().finish());
        return view;
    }

}