package com.tianzunchina.sample.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.sample.R;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import static android.app.Activity.RESULT_OK;

/**
 * 个人信息
 * Created by yqq on 2017/4/7.
 */

public class MeFragment extends TZFragment implements View.OnClickListener{

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("个人信息");
        ImageView  ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
        ivHeader.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivHeader:

                break;

        }


    }
}
