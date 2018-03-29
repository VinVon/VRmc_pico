package vr.xinjing.com.vrmc.model;

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
 * 推送是否收到
 * Created by raytine on 2018/3/29.
 */

public class PushReciverModel {

    public void pushRevicer(Map<String,String> map, final PushReciverModel.OnPushListener listenter){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_PUSH)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))

                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        if (0 == 0){
                            listenter.pushSuccess("");
                        }else
                        {
                            listenter.pushFailed("");
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
    public interface OnPushListener
    {
        void pushSuccess(String user);

        void pushFailed(String user);
    }
}
