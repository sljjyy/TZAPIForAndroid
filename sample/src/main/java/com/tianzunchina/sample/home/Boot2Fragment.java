package com.tianzunchina.sample.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.sample.MainActivity;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.util.BootConfig;

@SuppressLint("ValidFragment")
public class Boot2Fragment extends TZFragment {
    private int bgID, phonePicID, titleID;
    private boolean isLast;

    private static final int REQUEST_OK = 100;
    private static final int RESULT_ERR = -1;

    public Boot2Fragment(int bgID, int phonePicID, int titleID,
                         boolean isLast) {
        this.bgID = bgID;
        this.phonePicID = phonePicID;
        this.titleID = titleID;
        this.isLast = isLast;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_boot2, null);
        ImageView ivTitle = (ImageView) view.findViewById(R.id.ivTitle);
        ImageView ivPic = (ImageView) view.findViewById(R.id.ivPic);
        TextView tvClose = (TextView) view.findViewById(R.id.tvClose);
        view.setBackgroundResource(bgID);
        ivPic.setImageResource(phonePicID);
        ivTitle.setImageResource(titleID);
        if (!isLast) {
            return view;
        }
        tvClose.setVisibility(View.VISIBLE);
        tvClose.setOnClickListener(v -> {
            BootConfig bc = new BootConfig();
            bc.boot();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_ERR) {
            return;
        }

    }
}