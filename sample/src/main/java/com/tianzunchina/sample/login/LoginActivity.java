package com.tianzunchina.sample.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.login.TZLoginActivity;
import com.tianzunchina.android.api.network.TZRequest;
import com.tianzunchina.sample.MainActivity;
import com.tianzunchina.sample.R;
import com.tianzunchina.sample.model.User;
import com.tianzunchina.sample.util.LoginUtil;
import com.tianzunchina.sample.view.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录
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
        Intent intent;
        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btnRegister:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);

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
        try {
            JSONObject body = jsonObject.getJSONObject("Body");
            JSONObject userJSONObject = body.getJSONObject("User");
            User user = new User(userJSONObject);
            LoginUtil.setUser(user);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

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


    public JSONObject getJsonBody(Object obj) throws JSONException {
        String json = (String) obj;
        JSONObject dataJson;
        dataJson = new JSONObject(json);
        return dataJson.getJSONObject("Body");
    }
}
