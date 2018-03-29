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
import vr.xinjing.com.vrmc.bean.LastAyncInfo;
import vr.xinjing.com.vrmc.bean.PatientInfo;

/**
 * Created by raytine on 2017/3/3.
 */

public class AyncTimeModel {
    public void getAync(Map<String,Object> map, final AyncTimeModel.OnLoginListener onLoginListener){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_AYNCLAST)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        Gson gson = new Gson();

                        LastAyncInfo lastAyncInfo = (LastAyncInfo) gson.fromJson(response.body().string(),LastAyncInfo.class);

                        if (lastAyncInfo.getCode() == 0 && lastAyncInfo.getData() !=null){
                            onLoginListener.loginSuccess(lastAyncInfo);
                        }else if(lastAyncInfo.getCode() == 95 ||lastAyncInfo.getCode() == 96 ||lastAyncInfo.getCode() ==97||lastAyncInfo.getCode() ==98){
                            onLoginListener.tokenChange();
                        }else{
                            onLoginListener.loginFailed(lastAyncInfo);
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
        void loginSuccess(LastAyncInfo user);
        void tokenChange();
        void loginFailed(LastAyncInfo user);
    }
}
