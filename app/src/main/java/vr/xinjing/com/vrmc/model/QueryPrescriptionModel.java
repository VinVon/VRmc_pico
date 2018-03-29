package vr.xinjing.com.vrmc.model;

import android.os.Message;
import android.util.Log;

import com.utovr.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;
import vr.xinjing.com.vrmc.UrlPath.UrlHttp;
import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.bean.Prescription;
import vr.xinjing.com.vrmc.bean.PrescriptionContent;
import vr.xinjing.com.vrmc.bean.PrescriptionInfo;
import vr.xinjing.com.vrmc.utils.GsonImpl;
import vr.xinjing.com.vrmc.utils.JsonUtils;

/**
 * Created by raytine on 2017/2/11.
 */

public class QueryPrescriptionModel {
//    //获取处方
//    public void  getPrescription(Map<String,String> map, final OnLoginListener onLoginListener){
//        OkHttpUtils.postString()
//                .url(UrlHttp.PATH_PRESCRIPTION)
//                .content(new Gson().toJson(map))
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(Response response, int id) throws Exception {
//                        Prescription prescription = JsonUtils.prescriptionPares(response.body().string());
//                        if (prescription.getCode() == 0){
//                            onLoginListener.loginSuccess(prescription);
//                        } else if(prescription.getCode() == 95 ||prescription.getCode() == 96 ||prescription.getCode() ==97||prescription.getCode() ==98){
//                            onLoginListener.tokenChange();
//                        }
//                        else{
//                            onLoginListener.loginFailed(prescription);
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Object response, int id) {
//
//                    }
//                });
//
//    }
//    //获取内容列表
//        public void getPrescriptionlist(Map<String,String> map, final OnLoginListenero onLoginListener){
//            OkHttpUtils.postString()
//                    .url(UrlHttp.PATH_PRESCRIPTIONCONTENT)
//                    .content(new Gson().toJson(map))
//                    .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                    .build()
//                    .execute(new Callback() {
//                        @Override
//                        public Object parseNetworkResponse(Response response, int id) throws Exception {
//
//                            String st = response.body().string();
//
//                            PrescriptionContent prescriptionContent = JsonUtils.prescriptionContentParess(st);
//                            if (prescriptionContent.getCode() == 0) {//查询内容列表成功
//                                onLoginListener.loginSuccess(prescriptionContent);
//                            } else if(prescriptionContent.getCode() == 95 ||prescriptionContent.getCode() == 96 ||prescriptionContent.getCode() ==97||prescriptionContent.getCode() ==98){
//                                onLoginListener.tokenChange();
//                            }
//                            else {
//
//                                onLoginListener.loginFailed(prescriptionContent);
//                            }
//                            return null;
//                        }
//
//                        @Override
//                        public void onError(Call call, Exception e, int id) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Object response, int id) {
//
//                        }
//                    });
//        }
    //获取内容详情
    public void getPrescriptionlistcontent(Map<String,String> map, final OnLoginListenert onLoginListener){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_PRESCRIPTIONCONTENT_ONE)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        String st = response.body().string();
//                        Log.e("-------------内容",st);
                        PrescriptionInfo prescriptionInfo = GsonImpl.get().toObject(st,PrescriptionInfo.class);
                        if (prescriptionInfo.getCode() == 0&& prescriptionInfo.getData()!=null) {//查询患者的处方列表成功
                            onLoginListener.loginSuccess(prescriptionInfo);
                        }
                        else if(prescriptionInfo.getCode() == 95 ||prescriptionInfo.getCode() == 96 ||prescriptionInfo.getCode() ==97||prescriptionInfo.getCode() ==98){
                            onLoginListener.tokenChange();
                        }
                        else {
                            onLoginListener.loginFailed(prescriptionInfo);
                        }
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }

//    public interface OnLoginListener
//    {
//        void loginSuccess(Prescription user);
//        void tokenChange();
//        void loginFailed(Prescription user);
//    }
//    public interface OnLoginListenero
//    {
//        void loginSuccess(PrescriptionContent user);
//        void tokenChange();
//        void loginFailed(PrescriptionContent user);
//    }
    public interface OnLoginListenert
    {
        void loginSuccess(PrescriptionInfo user);
        void tokenChange();
        void loginFailed(PrescriptionInfo user);
    }
}
