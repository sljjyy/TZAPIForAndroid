package com.tianzunchina.sample.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.HTTPWebAPI;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.android.api.network.WebCallBackListener;
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;
import com.tianzunchina.android.api.widget.form.select.ItemSelectedBottomSheetDialog;
import com.tianzunchina.android.api.widget.form.select.ItemSelectedCallBack;
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxGroup;
import com.tianzunchina.android.api.widget.photo.TZPhotoBoxOne;
import com.tianzunchina.sample.R;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EventActivity extends TZAppCompatActivity {
    private TZPhotoBoxGroup photoBoxGroup;
    private TZPhotoBoxOne pbOne, pbOne2;
    private TextView etEventContent;
    private List<ArrayAdapterItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        initData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        photoBoxGroup = (TZPhotoBoxGroup) findViewById(R.id.pbg);
        pbOne = (TZPhotoBoxOne) findViewById(R.id.pbOne);
        pbOne2 = (TZPhotoBoxOne) findViewById(R.id.pbOne2);
        etEventContent = (TextView) findViewById(R.id.etEventContent);
        final TextView tvSelect = (TextView) findViewById(R.id.tvSelect);
        final TextView etEventAddress = (TextView) findViewById(R.id.etEventAddress);
        final TextView etEventDes = (TextView) findViewById(R.id.etEventDes);
        etEventContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etEventAddress.setText(filter(s.toString()));
                etEventDes.setText(unFilter(etEventAddress.getText().toString()));
            }
        });

        final ItemSelectedBottomSheetDialog dialog = new ItemSelectedBottomSheetDialog(this, items);
        dialog.setCallBack(new ItemSelectedCallBack() {
            @Override
            public void select(ArrayAdapterItem item) {
                tvSelect.setText(item.getVal());
            }
        });
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pbOne.onActivityResult(this, requestCode, resultCode, data);
        pbOne2.onActivityResult(this, requestCode, resultCode, data);
        photoBoxGroup.onActivityResult(this, requestCode, resultCode, data);
    }

    public void submit(View view) {
        TZRequest tzRequest = new TZRequest("http://10.80.2.108:8098/api/Login/", "Login");
        tzRequest.addParam("code", 10000);
        tzRequest.addParam("password", "aa1122");
        new HTTPWebAPI().call(tzRequest, new WebCallBackListener() {
            @Override
            public void success(org.json.JSONObject jsonObject, TZRequest request) {
                TZToastTool.mark(jsonObject.toString());
            }

            @Override
            public void success(Object response, TZRequest request) {
                TZToastTool.mark(response.toString());
            }

            @Override
            public void err(String s, TZRequest tzRequest) {
                TZToastTool.mark(s);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        photoBoxGroup.dispatchTouchEvent(ev, false);
        pbOne.dispatchTouchEvent(ev, false);
        pbOne2.dispatchTouchEvent(ev, false);
        return super.dispatchTouchEvent(ev);
    }


    public String filter(String str){
        if(str == null || str.isEmpty()){
            return "";
        }
        str+=" ";
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<str.length()-1;i++){
            int ch = str.charAt(i);
            System.err.print(ch);
            if(isNotEmojiCharacter(str.charAt(i))){
                sb.append((char)ch);
            }else{
                sb.append(String.format(Locale.CHINA, "%%u%x", ch));
            }
        }
        return sb.toString();
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return codePoint == 0x0 || codePoint == 0x9 || codePoint == 0xA || codePoint == 0xD || codePoint >= 0x20 && codePoint <= 0xD7FF || codePoint >= 0xE000 && codePoint <= 0xFFFD;
    }


    public  String unFilter(String str){
        if(str == null || str.isEmpty()){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        Pattern pattern = Pattern.compile("%u[0-9a-fA-F]{4}");
        Matcher matcher = pattern.matcher(str);
        String emojiStr = "";
        while (matcher.find()) {
            emojiStr = matcher.group();
            int index = sb.lastIndexOf(emojiStr);
            String emoji = emojiStr.substring(2);
            char ch = (char)Integer.parseInt(emoji, 16);
            sb.delete(index, index + 6);
            sb.insert(index, ch);

        }
        return sb.toString();
    }

    private void initData(){
        items = new ArrayList<>();
        items.add(new ArrayAdapterItem(1,0,"选项1", "描述1"));
        items.add(new ArrayAdapterItem(2,0,"选项2", "描述2"));
        items.add(new ArrayAdapterItem(3,0,"选项3", "描述3"));
        items.add(new ArrayAdapterItem(4,0,"选项4", "描述4"));
        items.add(new ArrayAdapterItem(5,0,"选项5", "描述5"));
        items.add(new ArrayAdapterItem(6,0,"选项6", "描述6"));
        items.add(new ArrayAdapterItem(7,0,"选项7", "描述7"));
        items.add(new ArrayAdapterItem(8,0,"选项8", "描述8"));
        items.add(new ArrayAdapterItem(9,0,"选项9", "描述9"));

    }
}
