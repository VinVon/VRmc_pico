package vr.xinjing.com.vrmc.model;



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
import vr.xinjing.com.vrmc.utils.JsonUtils;

/**
 * Created by raytine on 2017/2/11.
 */

public class LoginModel {

        public void getUser(Map<String,String> map, final OnLoginListener listenter){
                OkHttpUtils.postString()
                        .url(UrlHttp.PATH_LOGIN)
                        .content(new Gson().toJson(map))
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))

                        .build()
                        .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(Response response, int id) throws Exception {

                                    LoginInfo loginInfo = JsonUtils.jsonPares(response.body().string());
                                    if (loginInfo.getCode() == 0){
                                        listenter.loginSuccess(loginInfo);
                                    }else
                                    {
                                        listenter.loginFailed(loginInfo);
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
    public interface OnLoginListener
    {
        void loginSuccess(LoginInfo user);

        void loginFailed(LoginInfo user);
    }
}
