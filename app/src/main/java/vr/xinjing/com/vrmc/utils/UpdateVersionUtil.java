package vr.xinjing.com.vrmc.utils;

/**
 * Created by raytine on 2017/2/22.
 */
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.utovr.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;
import vr.xinjing.com.vrmc.UrlPath.UrlHttp;
import vr.xinjing.com.vrmc.bean.VideoInfo;
import vr.xinjing.com.vrmc.update.HttpRequest;
import vr.xinjing.com.vrmc.update.HttpRequest.RequestCallBackListener;
import vr.xinjing.com.vrmc.R;
import vr.xinjing.com.vrmc.update.ApkUtils;
import vr.xinjing.com.vrmc.update.JsonUtil;
import vr.xinjing.com.vrmc.update.NetworkUtil;
import vr.xinjing.com.vrmc.update.SDCardUtils;
import vr.xinjing.com.vrmc.update.ServerReqAddress;
import vr.xinjing.com.vrmc.update.UpdateStatus;
import vr.xinjing.com.vrmc.update.UpdateVersionService;
import vr.xinjing.com.vrmc.update.VersionInfo;

public class UpdateVersionUtil {
    /**
     * 接口回调
     * @author wenjie
     *
     */
    public interface UpdateListener{
        void onUpdateReturned(int updateStatus,VersionInfo versionInfo);

    }

    public UpdateListener updateListener;

//    public void setUpdateListener(UpdateListener updateListener) {
//        this.updateListener = updateListener;
//        HttpHandler<File> handler = UpdateVersionService.getHandler();
//
//    }

    /**
     * 网络测试 检测版本
     * @param context 上下文
     */
    public static void checkVersion(Map<String,String> map,final Context context, final UpdateListener updateListener){
        OkHttpUtils.postString()
                .url(UrlHttp.PATH_VERSION)
                .content(new Gson().toJson(map))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
//                        VersionInfo mVersionInfo = JsonUtils.jsonVersion(response.body().string());
//                        Logger.e("------------code",mVersionInfo.getData().getVersionCode()+"");
                        Gson g = new Gson();
                        Object o = g.fromJson(response.body().string(), VersionInfo.class);
                        return o;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        updateListener.onUpdateReturned(UpdateStatus.TIMEOUT,null);
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        VersionInfo mVersionInfo = (VersionInfo) response;
                        if (mVersionInfo.getData() !=null){
                        int clientVersionCode = ApkUtils.getVersionCode(context);
                        int serverVersionCode = Integer.valueOf(mVersionInfo.getData().getVersionCode());
                        //有新版本
                        if(clientVersionCode < serverVersionCode){
                            int i = NetworkUtil.checkedNetWorkType(context);
                            if(i == NetworkUtil.NOWIFI)
                            {
                                updateListener.onUpdateReturned(UpdateStatus.NOWIFI,mVersionInfo);
                            }else
                            if(i == NetworkUtil.WIFI)
                            {
                                updateListener.onUpdateReturned(UpdateStatus.YES,mVersionInfo);
                            }
                        }
                        else{
                            //无新本
                            updateListener.onUpdateReturned(UpdateStatus.NO,null);
                        }}else{
                            //无新本
                            updateListener.onUpdateReturned(UpdateStatus.NO,null);
                        }
                    }
                });
//        HttpRequest.get(ServerReqAddress.UPDATA_VERSION_REQ, new RequestCallBackListener() {
//
//
//            @Override
//            public void onSuccess(String resultData) {
//                try {
//                    JSONObject jsonObject = JsonUtil.stringToJson(resultData);
//                    JSONArray array = jsonObject.getJSONArray("data");
//                    VersionInfo mVersionInfo = JsonUtil.jsonToBean(array.getJSONObject(0).toString(), VersionInfo.class);
//                    int clientVersionCode = ApkUtils.getVersionCode(context);
//                    int serverVersionCode = Integer.valueOf(mVersionInfo.getData().getVersionCode());
//                    //有新版本
//                    if(clientVersionCode < serverVersionCode){
//                        int i = NetworkUtil.checkedNetWorkType(context);
//                        if(i == NetworkUtil.NOWIFI){
//                            updateListener.onUpdateReturned(UpdateStatus.NOWIFI,mVersionInfo);
//                        }else if(i == NetworkUtil.WIFI){
//                            updateListener.onUpdateReturned(UpdateStatus.YES,mVersionInfo);
//                        }
//                    }else{
//                        //无新本
//                        updateListener.onUpdateReturned(UpdateStatus.NO,null);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    updateListener.onUpdateReturned(UpdateStatus.ERROR,null);
//                }
//            }
//
//            @Override
//            public void onFailure(String error) {
//                updateListener.onUpdateReturned(UpdateStatus.TIMEOUT,null);
//            }
//        });
    }


    /**
     * 本地测试
     */
    public static void localCheckedVersion(final Context context,final UpdateListener updateListener){
        try {
//			JSONObject jsonObject = JsonUtil.stringToJson(resultData);
//			JSONArray array = jsonObject.getJSONArray("data");
//			VersionInfo mVersionInfo = JsonUtil.jsonToBean(array.getJSONObject(0).toString(), VersionInfo.class);
            VersionInfo mVersionInfos = new VersionInfo();
            VersionInfo.DataBean mVersionInfo = new VersionInfo.DataBean();
            mVersionInfo.setDownloadUrl("http://gdown.baidu.com/data/wisegame/57a788487345e938/QQ_358.apk");
            mVersionInfo.setVersionDesc("\n更新内容：\n1、增加xxxxx功能\n2、增加xxxx显示！\n3、用户界面优化！\n4、xxxxxx！");
            mVersionInfo.setVersionCode("2");
            mVersionInfo.setVersionName("v2020");
            mVersionInfo.setVersionSize("20.1M");
            mVersionInfo.setId("1");
            mVersionInfos.setData(mVersionInfo);
            int clientVersionCode = ApkUtils.getVersionCode(context);
            int serverVersionCode = Integer.valueOf(mVersionInfo.getVersionCode());
            //有新版本
            if(clientVersionCode < serverVersionCode){
                int i = NetworkUtil.checkedNetWorkType(context);
                if(i == NetworkUtil.NOWIFI){
                    updateListener.onUpdateReturned(UpdateStatus.NOWIFI,mVersionInfos);
                }else if(i == NetworkUtil.WIFI){
                    updateListener.onUpdateReturned(UpdateStatus.YES,mVersionInfos);
                }
            }else{
                //无新本
                updateListener.onUpdateReturned(UpdateStatus.NO,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateListener.onUpdateReturned(UpdateStatus.ERROR,null);
        }
    }


    /**
     * 弹出新版本提示
     * @param context 上下文
     * @param versionInfo 更新内容
     */
    public static void showDialog(final Context context,final VersionInfo versionInfo){
        final Dialog dialog = new AlertDialog.Builder(context).create();
        final File file = new File(SDCardUtils.getRootDirectory()+"/updateVersion/gdmsaec-app.apk");
        dialog.setCancelable(false);// 可以用“返回键”取消
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.version_update_dialog, null);
        dialog.setContentView(view);

        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
        TextView tvUpdateTile = (TextView) view.findViewById(R.id.tv_update_title);
        final TextView tvUpdateMsgSize = (TextView) view.findViewById(R.id.tv_update_msg_size);

        tvContent.setText(versionInfo.getData().getVersionDesc());
        tvUpdateTile.setText("最新版本："+versionInfo.getData().getVersionName());

        if(file.exists() && file.getName().equals("gdmsaec-app.apk")){
            tvUpdateMsgSize.setText("新版本已经下载，是否安装？");
        }else{
            tvUpdateMsgSize.setText("新版本大小："+versionInfo.getData().getVersionSize());
        }

        btnOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(v.getId() == R.id.btn_update_id_ok){
                    //新版本已经下载
                    if(file.exists() && file.getName().equals("gdmsaec-app.apk")){
                        Intent intent = ApkUtils.getInstallIntent(context,file);
                        context.startActivity(intent);
                    }else{
                        //没有下载，则开启服务下载新版本
                        Intent intent = new Intent(context,UpdateVersionService.class);
                        intent.putExtra("downloadUrl", versionInfo.getData().getDownloadUrl());
                        context.startService(intent);
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 收起通知栏
     * @param context
     */
    public static void collapseStatusBar(Context context) {
        try{
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16){
                collapse = statusBarManager.getClass().getMethod("collapse");
            }else{
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        }catch (Exception localException){
            localException.printStackTrace();
        }
    }
}
