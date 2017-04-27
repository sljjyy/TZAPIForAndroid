package com.tianzunchina.sample.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tianzunchina.sample.R;

/**
 * 简易案件
 * Created by yqq on 2017/4/18.
 */

public class SampleCaseFragment extends Fragment{

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sample_case, container, false);


        return view;
    }
}
