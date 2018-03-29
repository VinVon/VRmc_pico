package vr.xinjing.com.vrmc.model;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;
import vr.xinjing.com.vrmc.UrlPath.UrlHttp;
import vr.xinjing.com.vrmc.bean.NoDataInfo;
import vr.xinjing.com.vrmc.bean.TaskInfo;

/**
 * Created by raytine on 2017/4/12.
 */

public class EndTaskModel {
    public void Endtask(Map<String,String> map,final EndTaskListener endTaskListener){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_OVERTASK)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response, int id) throws Exception {
                com.utovr.gson.Gson gson = new com.utovr.gson.Gson();
                NoDataInfo info = (NoDataInfo) gson.fromJson(response.body().string(),NoDataInfo.class);
                if (info.getCode() == 0){
                    endTaskListener.endTaskSuccess(info.getMessage());
                } else if(info.getCode() == 95 ||info.getCode() == 96 ||info.getCode() ==97||info.getCode() ==98){
                    endTaskListener.tokenChange();
                }else{
                    endTaskListener.endTaskFailed(info.getMessage());
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
    public  interface EndTaskListener{
        void endTaskSuccess(String msg);
        void tokenChange();
        void endTaskFailed(String msg);
    }
}
