package com.tianzunchina.sample.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tianzunchina.android.api.network.HTTPWebAPI;
import com.tianzunchina.android.api.widget.dialog.TimePickerDialog;
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;
import com.tianzunchina.android.api.widget.form.select.ItemSelectedBottomSheetDialog;
import com.tianzunchina.android.api.widget.form.select.ItemSelectedCallBack;
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup;
import com.tianzunchina.sample.R;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 执法事件
 * Created by yqq on 2017/4/18.
 */

public class EventCaseFragment extends Fragment {
    LinearLayout llBigKind, llSmallKind, llReportDate, llMap;//大类，小类，时间，地图
    TextView tvBigKinds, tvSmallKinds, tvReportDate, tvEventLocation;//大类，小类，时间，地址坐标
    EditText etContactName, etContactMobile, etEventTitle, etEventAddress, etReportContent;//联系人名字，联系人电话，事件标题，事件地址，事件内容
    Button btnBottomOk;
    TZPhotoBoxGroup photoBoxGroup;
    ImageView ivMap;
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    int mapLocation = 0;
    HTTPWebAPI webAPI = new HTTPWebAPI();
    private List<ArrayAdapterItem> items;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_case, container, false);

        initData();
        init();
        initViewNew();

        return view;
    }

    private void initData() {
        items = new ArrayList<>();
        items.add(new ArrayAdapterItem(1, 0, "选项1", "描述1"));
        items.add(new ArrayAdapterItem(2, 0, "选项2", "描述2"));
        items.add(new ArrayAdapterItem(3, 0, "选项3", "描述3"));
        items.add(new ArrayAdapterItem(4, 0, "选项4", "描述4"));
        items.add(new ArrayAdapterItem(5, 0, "选项5", "描述5"));
        items.add(new ArrayAdapterItem(6, 0, "选项6", "描述6"));
        items.add(new ArrayAdapterItem(7, 0, "选项7", "描述7"));
        items.add(new ArrayAdapterItem(8, 0, "选项8", "描述8"));
        items.add(new ArrayAdapterItem(9, 0, "选项9", "描述9"));

    }

    private void init() {
        llBigKind = (LinearLayout) view.findViewById(R.id.llBigKinds);
        llSmallKind = (LinearLayout) view.findViewById(R.id.llSmallKinds);
        llReportDate = (LinearLayout) view.findViewById(R.id.llReportDate);
        llMap = (LinearLayout) view.findViewById(R.id.llMap);

        tvBigKinds = (TextView) view.findViewById(R.id.tvBigKinds);
        tvSmallKinds = (TextView) view.findViewById(R.id.tvSmallKinds);
        tvReportDate = (TextView) view.findViewById(R.id.tvReportDate);
        tvEventLocation = (TextView) view.findViewById(R.id.tvEventLocation);

        etContactName = (EditText) view.findViewById(R.id.etContactName);
        etContactMobile = (EditText) view.findViewById(R.id.etContactMobile);
        etEventTitle = (EditText) view.findViewById(R.id.etEventTitle);
        etEventAddress = (EditText) view.findViewById(R.id.etEventAddress);
        etReportContent = (EditText) view.findViewById(R.id.etReportContent);

        btnBottomOk = (Button) view.findViewById(R.id.btnBottomOk);
        ivMap = (ImageView) view.findViewById(R.id.ivMap);
        photoBoxGroup = (TZPhotoBoxGroup) view.findViewById(R.id.pbGroup);

        final ItemSelectedBottomSheetDialog dialog = new ItemSelectedBottomSheetDialog(this.getActivity(), items);
        dialog.setCallBack(new ItemSelectedCallBack() {
            @Override
            public void select(ArrayAdapterItem item) {
                tvBigKinds.setText(item.getVal());
            }
        });
        tvBigKinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    /**
     * 初始化
     */
    private void initViewNew() {
        mapLocation = 0;
        llReportDate.setOnClickListener(v -> {
        });
        btnBottomOk.setOnClickListener(v -> {
        });
        gregorianCalendar.setTimeInMillis(System.currentTimeMillis());
        llMap.setOnClickListener(v -> {

        });
    }

    /**
     * 检验填写内容的正确性
     *
     * @return true
     */
    private boolean checkCase() {
        if (tvBigKinds.getText().toString().equals("大类")) {
            showToast("请选择问题大类");
            return false;
        }
        if (tvSmallKinds.getText().toString().equals("小类")) {
            showToast("请选择问题小类");
            return false;
        }
        if (TextUtils.isEmpty(etEventTitle.getText().toString())) {
            showToast("请输入标题");
            return false;
        }
        if (TextUtils.isEmpty(etEventAddress.getText().toString())) {
            showToast("请输入地址");
            return false;
        }
        if (TextUtils.isEmpty(etReportContent.getText().toString())) {
            showToast("请输入内容");
            return false;
        }
        if (tvEventLocation.getText().toString().equals("0.0")) {
            showToast("请点击地图标记并选择位置");
            return false;
        }
        return true;
    }

    /**
     * 提示框
     *
     * @param text 弹出的信息提示
     */
    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示框
     *
     * @param resID 资源ID
     */
    public void showToast(int resID) {
        showToast(getActivity().getString(resID));
    }


    /*
     照片返回
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (photoBoxGroup == null) {
            return;
        }
        photoBoxGroup.onActivityResult(this.getActivity(), requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
