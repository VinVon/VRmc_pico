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
import vr.xinjing.com.vrmc.bean.TaskInfo;

/**
 * Created by raytine on 2017/4/10.
 */

public class TasklistModel {
    public void  getTask(Map<String,String> map,final TaskListener taskListener){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_GETTASK)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        Gson gson = new Gson();
                        TaskInfo info = (TaskInfo) gson.fromJson(response.body().string(),TaskInfo.class);
                        if (info.getCode() == 0){
                            taskListener.getTaskSuccess(info);
                        }else if(info.getCode() == 95 ||info.getCode() == 96 ||info.getCode() ==97||info.getCode() ==98){
                            taskListener.tokenChange();
                        }else{
                            taskListener.getTaskFailed(info.getMessage());
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
    public  interface TaskListener{
        void getTaskSuccess(TaskInfo info);
        void tokenChange();
        void getTaskFailed(String msg);
    }
}
