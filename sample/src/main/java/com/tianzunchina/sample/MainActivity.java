package com.tianzunchina.sample;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.widget.form.DynamicFormFragment;
import com.tianzunchina.android.api.widget.form.FormItem;
import com.tianzunchina.android.api.widget.form.FormTable;
import com.tianzunchina.android.api.widget.form.select.ArrayAdapterItem;
import com.tianzunchina.sample.home.MainActivityFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                toFragment();
            }
        });
    }

    private void toFragment(){
        DynamicFormFragment fragment = new DynamicFormFragment();
        Bundle bundle = new Bundle();
        String json =  getJson();
        bundle.putString("json", json);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
