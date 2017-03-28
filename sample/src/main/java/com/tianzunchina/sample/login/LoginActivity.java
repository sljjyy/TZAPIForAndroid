package com.tianzunchina.sample.login;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.login.TZLoginActivity;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.sample.R;

import org.json.JSONObject;

/**
 * admin
 * 2016/12/23 0023.
 */

public class LoginActivity extends TZLoginActivity {
    private EditText account, password;
    private TextView message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    private void init() {
        initView();
        initSOAP("http://218.108.93.154:8090/JCSJService.asmx", "Login");//初始化请求参数
    }

    private void initView() {
        account = getView(R.id.etAccount);
        password = getView(R.id.etPassword);
        message = getView(R.id.tvMessage);
        getView(R.id.btnLogin).setOnClickListener(this);
        getView(R.id.btnForgotPass).setOnClickListener(this);
        getView(R.id.btnRegister).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                TZToastTool.essential("跳转注册页面");
                break;
            case R.id.btnForgotPass:
                TZToastTool.essential("忘记密码啦");
        }
    }

    private void login() {
        String strAccount = account.getText().toString().trim();
        String strPassword = password.getText().toString().trim();

        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("_account", strAccount);
        map.put("_password", strPassword);
        setWebServicePropertys(map);
        login(strAccount, strPassword);
    }

    @Override
    public void success(JSONObject jsonObject, TZRequest request) {
        TZToastTool.essential("恭喜您，登录成功");
    }

    @Override
    public void success(Object response, TZRequest request) {
        message.setText(response.toString());
    }

    @Override
    public void err(String err, TZRequest request) {
        TZToastTool.essential(err);
        message.setText(err);
    }
}
