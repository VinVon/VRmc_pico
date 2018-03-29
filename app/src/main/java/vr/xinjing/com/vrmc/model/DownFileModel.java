package vr.xinjing.com.vrmc.model;

/**
 * Created by raytine on 2017/2/20.
 */

import android.content.Context;
import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.utils.Logger;


/**
 * 下载文件
 */
public class DownFileModel {
    public  void  loadFile(Context context,String path, String name, String url, OnLoginListener listener){
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(path,name) {
                    @Override
                    public void inProgress(float progress, long total, int id) {

                        if ((int)progress == 1){
                            Logger.e("--------下载进度","inProgress");

                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.loginFailed();
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Logger.e("--------下载进度",""+"onResponse");
                        listener.loginSuccess();
                    }
                });
    }
    public interface OnLoginListener
    {
        void loadprogress(boolean iscompele);
        void loginSuccess();

        void loginFailed();
    }
}
