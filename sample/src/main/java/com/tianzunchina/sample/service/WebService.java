package com.tianzunchina.sample.service;

import android.os.Handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/4/25.
 */

public class WebService {
        /**
         * 测试服务器url
         */
        public static final ICCServer1 ICC_SERVER_TEST = new ICCServer1(
                "http://218.108.93.154:8090/",
                "http://218.108.93.154:8081/ICCWapTest/Discounts/DiscountsScanSuccess_wap?decryptString=",
                "http://218.108.93.154:8090/ImgHandler.ashx?Path=",
                "http://218.108.93.154:8080/WebAPITest/api/Remind/",
                "http://218.108.93.154:8081/TestSocial/zhuhuinfos/studyjlselect?Userid=",
                "http://218.108.93.154:8084/",
                "http://218.108.93.154:8081/Yuqingtest/feelstudios/feelindex?userid=",
                "http://218.108.93.154:8081/TestSocial/PocketMailboxsWap/Index?UserID=",
                "http://218.108.93.154:8081/Testsocial/DailyAccounts/index?UserID=");
        /**
         * 正式服务器url
         */
        public static final ICCServer1 ICC_SERVER = new ICCServer1(
                "http://218.108.93.154/",
                "http://218.108.93.154:8080/Discounts/DiscountsScanSuccess_wap?decryptString=",
                "http://218.108.93.154/ImgHandler.ashx?Path=",
                "http://218.108.93.154:8080/WebAPI/api/Remind/",
                "http://218.108.93.154:8089/zhuhuinfos/studyjlselect?userid=",
                "http://218.108.93.154:8080/",
                "http://218.108.93.154:8085/Feelstudios/Feelindex?userid=",
                "http://218.108.93.154:8089/PocketMailboxsWap/Index?UserID=",
                "http://218.108.93.154:8089/DailyAccounts/index?UserID=");

        public static ICCServer1 server = ICC_SERVER_TEST;

        public static final int HANDLE_OK = 1;
        public static final int HANDLE_ERR = -1;
        public static final String MODIFY_HEAD = "ModifyHead";

        public static final String SERVICE_USER = "JCSJService.asmx";
        public static final String SERVICE_MY_EVENT = "WLBLService.asmx";
        public static final String SERVICE_STAFF_EVENT = "SGBLService.asmx";
        public static final String SERVICE_SMS = "SMSService.asmx";
        public static final String SERVICE_UPDATE = "UpgradeVersionService.asmx";
        public static final String SERVICE_TASK_LIST = "LVRYService.asmx";
        public static final String SERVICE_USER_HEAD = "UserHeadService.asmx";
        public static final String SERVICE_REDEEM_POINTS = "RedeemPointsService.asmx";
        public static final String SERVICE_DISCOUNTS = "EverydayDiscountsService.asmx";
        public static final String SERVICE_NEWS = "NewsService.asmx";
        public static final String SERVICE_HOMEPAGE = "HomePageService.asmx";
        public static final String SERVICE_SCORE_MESSAGE = "MyScoresService.asmx";
        public static final String SERVICE_HOME_PAGE = "HomePageService.asmx";
        public static final String SERVICE_MESSAGE = "RemindService.asmx";
        public static final String SERVICE_CIRCLE = "CirclesService.asmx";
        public static final String SERVICE_STUDY = "StudyBankService.asmx";
        public static final String SERVICE_SDXF = "SDXFWebService.asmx";
        public static final String SERVICE_DJZR = "PartyBuildingService.asmx";
        public static final String SERVICE_SQFW = "CommunityService.asmx";

        // 圈子
        public static final String GET_FIXED_CIRCLES = "GetCirclesOfHome";
        public static final String GET_JOINED_CIRCLES = "GetJoinedCircles";
        public static final String GET_CIRCLE_TYPES = "GetCircleTypes";
        public static final String GET_CIRCLE = "GetQYCircleDetail";
        public static final String GET_CIRCLES = "GetCircles";
        public static final String GET_CIRCLE_ACTIVITY = "GetActivitiesOfCircle";
        protected static final String APPLY_QUIT_CIRCLE = "ApplyOrQuitCircle";
        protected static final String CREATE_CIRCLE_ACTIVITY = "CreateCircleActivitiesAddEndTime";
        protected static final String GET_CIRCLE_APPLY_MEMBER = "GetApplyMembersOfCircle";
        public static final String GET_CIRCLE_MEMBER = "GetMembersOfCircle";
        protected static final String AUDIT_CIRCLE_APPLICATION = "ProcessApplication";
        protected static final String GET_CIRCLE_ACTIVITY_DETAIL = "GetActivityDetail";
        protected static final String APPLY_CIRCLE_ACTIVITY = "ApplyActivity";
        protected static final String COMMENTED_CIRCLE_ACTIVITY = "CommentActivity";
        protected static final String IS_EVALUATED_CIRCLE_ACTIVITY = "IsCommentActivity";
        protected static final String EVALUATED_CIRCLE_ACTIVITY = "EvaluateActivity";
        protected static final String EVALUATED_CIRCLE_MEMBER = "EvaluateMembers";
        protected static final String GET_CIRCLE_ACTIVITY_COMMENTED = "GetCommentedActivityList";
        protected static final String GET_CIRCLE_ACTIVITY_EVALUATED = "GetEvaluatedActivityList";
        protected static final String GET_EVALUATE_CIRCLE_MEMBER = "GetEvaluateMembers";
        protected static final String GET_CIRCLE_ACTIVITY_STATISTICS = "GetActivityOfStatistics";
        protected static final String GET_CIRCLE_STATISTICS = "CircleOfStatistics";
        protected static final String GET_CIRCLE_MEMBER_RANK = "GetCircleMemberRankings";

        protected static final int TIME_OUT = 10000;
        protected Handler handler;

        protected WebService(Handler handler) {
            this.handler = handler;
        }

        protected WebService() {
        }

        protected SoapSerializationEnvelope getEnvelope(SoapObject soapObject) {
            System.setProperty("http.keepAlive", "false");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(soapObject);
            return envelope;
        }

        public JSONObject getJsonBody(Object obj) throws JSONException {
            String json = (String) obj;
            JSONObject dataJson;
            dataJson = new JSONObject(json);
            return dataJson.getJSONObject("Body");
        }

        public static String getPicUrl() {
            return server.getPictureRoot();
        }

        public static String getWebServiceUrl() {
            return server.getWebServiceRoot();
        }

        public static String getWebSiteDiscountRoot() {
            return server.getWebSiteDiscountRoot();
        }

        public static String getMessageRoot() {
            return server.getMessageRoot();
        }

        public static String getVisiServicerUrl() {
            return server.getVisiServicerUrl();
        }

        public static String getAccessoryFileUrl() {
            return server.getAccessoryFileUrl();
        }

        public boolean isEnable(JSONObject body) throws JSONException {
            int errCode = body.getInt("ErrorCount");
            return errCode == 0;
        }

        public String postURL(String url, String param) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] datas;
            String msg = "";
            HttpClient client = null;
            try {
                InputStream instream = null;
                HttpPost request = new HttpPost(url);
                StringEntity paramEntity = new StringEntity(param, HTTP.UTF_8);
                paramEntity.setContentType("text/xml");
                request.setHeader("Content-Type", "text/xml");
                request.setEntity(paramEntity);
                client = new DefaultHttpClient();
                HttpResponse response = client.execute(request);
                response.getStatusLine().getStatusCode();
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    instream = responseEntity.getContent();
                    int bytesRead;
                    datas = new byte[8192];
                    while ((bytesRead = instream.read(datas, 0, 8192)) != -1) {
                        baos.write(datas, 0, bytesRead);
                    }
                    msg = new String(baos.toByteArray(), "utf-8");
                }
                try {
                    if (instream != null) {
                        instream.close();
                    }
                    baos.close();
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
//            httpPost.releaseConnection();
                shutdownHttpClient(client);
            }
            System.err.println("postURL:" + msg);
            return msg;
        }

        private void shutdownHttpClient(HttpClient hc) {
            if (hc != null && hc.getConnectionManager() != null) {
                hc.getConnectionManager().shutdown();
            }
        }
}
