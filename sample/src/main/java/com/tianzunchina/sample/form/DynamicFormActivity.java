package com.tianzunchina.sample.form;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.tianzunchina.android.api.base.TZAppCompatActivity;
import com.tianzunchina.android.api.widget.form.DynamicFormFragment;
import com.tianzunchina.android.api.widget.form.FormItem;
import com.tianzunchina.android.api.widget.form.FormTable;
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;
import com.tianzunchina.sample.R;

import java.util.ArrayList;
import java.util.List;

public class DynamicFormActivity extends TZAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_form);
        toFragment();
    }

    private void toFragment(){
        DynamicFormFragment fragment = new DynamicFormFragment();
        Bundle bundle = new Bundle();
        String json =  getJson();
        bundle.putString("json", json);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        fTransaction.add(fragment, DynamicFormFragment.TAG);
        fTransaction.replace(R.id.fragment, fragment, DynamicFormFragment.TAG);
        fTransaction.commit();
    }

    private String getJson(){
        FormTable table = new FormTable();
        table.setTitle("测试表");
        table.setColorIDContent(R.color.grey_black_1000);
        table.getItems().add(new FormItem("姓名"));
        table.getItems().add(new FormItem("年龄"));
        table.getItems().add(new FormItem("地址"));
        table.getItems().add(new FormItem("电话"));
        List<ArrayAdapterItem> items = new ArrayList<>();
        items.add(new ArrayAdapterItem(1, 0, "1111", "一一一一一"));
        items.add(new ArrayAdapterItem(2, 0, "2222", "二二二二二"));
        items.add(new ArrayAdapterItem(3, 0, "3333", "三三三三三"));
        table.getItems().add(new FormItem("单选框", items));
        table.getItems().add(new FormItem("证件"));
        table.getItems().add(new FormItem("巴拉巴拉"));
        return JSON.toJSONString(table);
    }
}
