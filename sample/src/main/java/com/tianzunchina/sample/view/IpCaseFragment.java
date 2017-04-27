package com.tianzunchina.sample.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianzunchina.sample.R;

/**
 * 违停案件
 * Created by yqq on 2017/4/18.
 */

public class IpCaseFragment extends Fragment{

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragemnt_ip_case, container, false);

        return view;
    }
}
