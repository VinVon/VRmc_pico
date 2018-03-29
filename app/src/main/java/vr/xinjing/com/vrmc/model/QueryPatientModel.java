package vr.xinjing.com.vrmc.model;
import android.os.Message;

import com.utovr.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;
import vr.xinjing.com.vrmc.UrlPath.UrlHttp;
import vr.xinjing.com.vrmc.bean.PatientInfo;
import vr.xinjing.com.vrmc.utils.JsonUtils;

/**
 * Created by raytine on 2017/2/11.
 */

public class QueryPatientModel {
//    public void getPatient(Map<String,String> map,final OnLoginListener onLoginListener){
//        OkHttpUtils.postString()
//                .url(UrlHttp.PATH_PATIENT)
//                .content(new Gson().toJson(map))
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//
//                .build()
//                .execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(Response response, int id) throws Exception {
//                        PatientInfo info = JsonUtils.patientPares(response.body().string());
//                        if (info.getCode() == 0) {//查询患者成功。根据患者的useid(即patientid)来查询患者的处方列表
//                            onLoginListener.loginSuccess(info);
//                        } else if(info.getCode() == 95 ||info.getCode() == 96 ||info.getCode() ==97||info.getCode() ==98){
//                            onLoginListener.tokenChange();
//                        }
//                        else
//                        {
//                            onLoginListener.loginFailed(info);
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
//    }
//    public interface OnLoginListener
//    {
//        void loginSuccess(PatientInfo user);
//        void tokenChange();
//        void loginFailed(PatientInfo user);
//    }
}
