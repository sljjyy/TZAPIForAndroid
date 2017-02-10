package com.tianzunchina.android.api.login;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.tianzunchina.android.api.base.TZActivity;
import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZLog;
import com.tianzunchina.android.api.log.TZToastTool;
import com.tianzunchina.android.api.network.WebCallBackListener;


/**
 * CraetTime 2016-3-22
 *
 * @author SunLiang
 */
public abstract class TZLoginActivity extends TZActivity implements View.OnClickListener, WebCallBackListener {

    private ArrayMap<String, Object> propertyMap;
    private SignInUser signInUser;
    private String webServiceUrl, method;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    protected abstract int getContentView();

    /**
     * @param webServiceUrl 接口服务器地址
     * @param method        接口方法名
     */
    protected void initSOAP(String webServiceUrl, String method) {
        this.webServiceUrl = webServiceUrl;
        this.method = method;
    }

    /**
     * 设置请求参数
     *
     * @param map 参数集合
     */
    protected void setWebServicePropertys(ArrayMap<String, Object> map) {
        propertyMap = map;
    }

    protected ArrayMap<String, Object> getWebServicePropertys() {
        return propertyMap;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        try {
            return (T) findViewById(viewId);
        } catch (ClassCastException ex) {
            TZLog.e(TZApplication.getInstance().getPackageName(), ex.toString());
            throw ex;
        }
    }

    /**
     * 登录、保存账号
     *
     * @param userName     用户名
     * @param userPassword 密码
     */
    protected void login(String userName, String userPassword) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            TZToastTool.essential("账户名或密码为空");
            return;
        }
        new Luncher(webServiceUrl, method, propertyMap, this).login();

        signInUser = new SignInUser(userName, userPassword);
        signInUser.save();
    }

    /**
     * 登录
     *
     * @param userName     用户名
     * @param userPassword 密码
     * @param isKeep       记住密码
     */
    protected void login(String userName, String userPassword, CheckBox isKeep) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            TZToastTool.essential("账户名或密码为空");
            return;
        }
        signInUser = new SignInUser(userName, userPassword, isKeep.isChecked());
        new Luncher(webServiceUrl, method, propertyMap, this).login();
    }

    /**
     * 登录
     *
     * @param userName     用户名
     * @param userPassword 密码
     * @param isKeep       记住密码
     * @param isAuto       自动登录
     */
    protected void login(String userName, String userPassword, CheckBox isKeep, CheckBox isAuto) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
            TZToastTool.essential("账户名或密码为空");
            return;
        }
        signInUser = new SignInUser(userName, userPassword, isKeep.isChecked(), isAuto.isChecked());
        new Luncher(webServiceUrl, method, propertyMap, this).login();
    }

    /**
     * 正则表达式
     *
     * @param regex
     */
    protected void setAccountRegex(String regex) {

    }

}
