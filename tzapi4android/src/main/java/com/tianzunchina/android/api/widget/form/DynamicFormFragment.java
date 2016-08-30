package com.tianzunchina.android.api.widget.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.tianzunchina.android.api.R;
import com.tianzunchina.android.api.base.TZFragment;
import com.tianzunchina.android.api.log.TZLog;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态表单
 * 需要通过传递json/xml获取表单内容
 * Bundle bundle = new Bundle();
 bundle.putString("json", jsonString);
 fragment.setArguments(bundle);
 FragmentManager fragmentManager = getFragmentManager();
 FragmentTransaction fTransaction = fragmentManager.beginTransaction();
 Transaction.add(fragment, TAG);
 fTransaction.commit();
 */
public class DynamicFormFragment extends TZFragment {
    public List<View> childrenView = new ArrayList<>();
    public static final String TAG = "DynamicFormFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_form, container, false);
        init(view);
        return view;
    }

    private void init(View root){
        FormTable table = getFormTable();
        for (int i = 0; i < table.getItems().size(); i++) {
            TZLog.e("item",table.getItems().get(i).toString());
            childrenView.add(getView(table, table.getItems().get(i)));
        }
        addViews(root);
    }

    private void addViews(View root){
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.llDynamicForm);
        for (int i = 0; i < childrenView.size(); i++) {
            layout.addView(childrenView.get(i));
        }
    }

    /**
     * 获取并解析json/xml字符串
     * @return
     */
    private FormTable getFormTable(){
        Bundle bundle = getFragmentManager().findFragmentByTag(TAG).getArguments();
        String json = bundle.getString(KEY_INTENT_JSON);
        if(json != null){
            return json2FormTable(json);
        }
        String xml = bundle.getString(KEY_INTENT_XML);
        return xml2FormTable(xml);
    }

    /**
     * 将json字符串解析成FormTable对象
     * @param json
     * @return
     */
    private FormTable json2FormTable(String json){
        return JSON.parseObject(json, FormTable.class);
    }

    /**
     * 将xml字符串解析成FormTable对象
     * @param xml
     * @return
     */
    private FormTable xml2FormTable(String xml){
        //TODO 待添加xml解析
        return new FormTable();
    }

    private View getView(FormTable table, FormItem item){
        View view = null;
        switch (item.getType()){
            case EDIT:
                view = new FormItemEditView(getActivity(), table, item);
                break;
            case BOOL:
                break;
            case SINGLE_SELECT:
                view = new SelectFormItemTextView(getActivity(), table, item);
                break;
            case MULTIPLE_SELECT:
                break;
            case DATE:
                break;
        }
        return view;
    }

    public static final String KEY_INTENT_JSON = "json";
    public static final String KEY_INTENT_XML = "xml";
}
