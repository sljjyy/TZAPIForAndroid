package com.tianzunchina.android.api.network;

import android.support.v4.util.ArrayMap;

import com.tianzunchina.android.api.base.TZApplication;
import com.tianzunchina.android.api.log.TZLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * SAOP网络访问工具
 * CraetTime 2016-3-24
 * @author SunLiang
 */
public class SOAPWebAPI implements WebAPIable {
    private String nameSpace = "http://tempuri.org/";
    private EventBus bus = new EventBus();

    public SOAPWebAPI(){
        bus.register(this);
    }

    public SOAPWebAPI(String nameSpace){
        this.nameSpace = nameSpace;
        bus.register(this);
    }

    public void destroy(){
        bus.unregister(this);
    }

    @Override
    public void call(final TZRequest request, final WebCallBackListener listenner) {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                SoapObject soapObject = new SoapObject(nameSpace, request.method);
                ArrayMap<String,Object> params = request.params;
                for (int i = 0; i < params.size(); i++) {
                    soapObject.addProperty(params.keyAt(i), params.valueAt(i));
                }
                SoapSerializationEnvelope envelope = getEnvelope(soapObject);
                String json = null;
                try {
                    HttpTransportSE transport = new HttpTransportSE(request.service, TIME_OUT);
                    transport.call(nameSpace + request.method, envelope);
                    json = envelope.getResponse().toString();
                    TZLog.e(json);
                    bus.post(new Success(new JSONObject(json), listenner, request));
                } catch (Exception e) {
                    e.printStackTrace();
                    String text = e.getMessage();
                    if(json != null){
                        text = json;
                    }
                    bus.post(new Error(text,listenner, request));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Success success){
        success.listenner.success(success.json, success.request);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Error err){
        err.listenner.err(err.msg, err.request);
    }

    protected static final int TIME_OUT = 30000;
    protected SoapSerializationEnvelope getEnvelope(SoapObject soapObject) {
        System.setProperty("http.keepAlive", "false");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        return envelope;
    }

}
