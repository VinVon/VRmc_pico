package vr.xinjing.com.vrmc.httpnet;

import android.util.Log;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vr.xinjing.com.vrmc.UrlPath.UrlHttp;
import vr.xinjing.com.vrmc.bean.IConstant;
import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.bean.requestbody.UserReq;
import vr.xinjing.com.vrmc.utils.Logger;

/**
 * Created by raytine on 2017/2/28.
 */

public class HttpMethods {
    private static final int DEAFULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private MoveServe moveServe;
    private HttpMethods(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEAFULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(UrlHttp.BASE_URL)
                .build();
        moveServe = retrofit.create(MoveServe.class);

    }
    private static  class SingletonHolder{
        private static final  HttpMethods INSTANCE = new HttpMethods();
    }
    public static HttpMethods getInstance(){
        return  SingletonHolder.INSTANCE;
    }

    //登录
    public void getTopMovice(UserReq userReq){
        RequestBody body=
                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(userReq));
        moveServe.getTopMovie(body)
                .enqueue(new Callback<LoginInfo>() {
                    @Override
                    public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                        Logger.e("-------------",response.body().getCode()+""+response.body().getMessage());
                    }
                    @Override
                    public void onFailure(Call<LoginInfo> call, Throwable t) {

                    }
                });

    }
}
