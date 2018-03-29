package vr.xinjing.com.vrmc.update;

/**
 * Created by raytine on 2017/2/22.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import okhttp3.Call;
import okhttp3.Request;
import vr.xinjing.com.vrmc.R;
import vr.xinjing.com.vrmc.model.DownFileModel;
import vr.xinjing.com.vrmc.utils.ToastCommom;
import vr.xinjing.com.vrmc.utils.UpdateVersionUtil;

public class UpdateVersionService extends Service {

    private NotificationManager nm;
    private Notification notification;
    //标题标识
    private int titleId = 0;
    //安装文件
    private File updateFile;

    private static HttpHandler<File> httpHandler;
    private HttpUtils httpUtils;

    private long initTotal = 0;//文件的总长度
    private String downUrl ;
    @Override
    public void onCreate() {
        super.onCreate();

        httpUtils = new HttpUtils();
        updateFile = new File(SDCardUtils.getRootDirectory()+"/updateVersion/gdmsaec-app.apk");

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = R.mipmap.icons;
        notification.tickerText = "开始下载";
        notification.when = System.currentTimeMillis();
        notification.contentView = new RemoteViews(getPackageName(), R.layout.notifycation);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//		VersionInfo versionInfo = (VersionInfo) intent.getSerializableExtra("versionInfo");
//		String url = versionInfo.getDownloadUrl();
        Bundle bundle = intent.getExtras();
        downUrl = bundle.getString("downloadUrl");
        PreferenceUtils.setString(UpdateVersionService.this, "apkDownloadurl", downUrl);
        nm.notify(titleId, notification);
        Log.e("----apk","开始下载apk");
        downLoadFile(downUrl);
//        new downloadApkThread().start();
        return super.onStartCommand(intent, flags, startId);
    }



    public void downLoadFile(String url){
        httpHandler = httpUtils.download(url,updateFile.getAbsolutePath(), true, false, new RequestCallBack<File>() {

            @Override
            public void onSuccess(ResponseInfo<File> response) {
                // 更改文字
                notification.contentView.setTextViewText(R.id.msg, "下载完成!点击安装");

//                notification.contentView.setViewVisibility(R.id.btnStartStop, View.GONE);
//                notification.contentView.setViewVisibility(R.id.btnCancel,View.GONE);
                // 发送消息
                nm.notify(0, notification);
                stopSelf();
                //收起通知栏
                UpdateVersionUtil.collapseStatusBar(UpdateVersionService.this);
                //自动安装新版本
                Intent installIntent = ApkUtils.getInstallIntent(UpdateVersionService.this,updateFile);
                startActivity(installIntent);

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                //网络连接错误
                if(error.getExceptionCode() == 0 ){
                    // 更改文字
                    notification.contentView.setTextViewText(R.id.msg, "网络异常！请检查网络设置！");
                }else if(error.getExceptionCode() == 416){//文件已经下载完毕

                    // 更改文字
                    notification.contentView.setTextViewText(R.id.msg, "VR室");
                    // 更改文字
                    notification.contentView.setTextViewText(R.id.bartext, "检测到新版本已经下载完成，点击即安装!");
                    // 隐藏进度条
                    notification.contentView.setViewVisibility(R.id.progressBar1, View.GONE);

                    Intent intent = ApkUtils.getInstallIntent(UpdateVersionService.this,updateFile);
                    PendingIntent pendingIntent = PendingIntent.getActivity(UpdateVersionService.this, 0, intent, 0);
                    notification.flags = Notification.FLAG_AUTO_CANCEL;//点击通知栏之后 消失
                    notification.contentIntent  = pendingIntent;//启动指定意图
                }
                // 发送消息
                nm.notify(0, notification);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                if(initTotal == 0){//说明第一次开始下载
                    initTotal = total;
                }

                if(initTotal != total){//说明下载过程中暂停过，文件的总长度出现问题  就把初始的文件的长度赋值给他重新计算已经下载的比例
                    total = initTotal;
                }

                long l = current*100/total;
                notification.contentView.setTextViewText(R.id.msg, "正在下载：VR室");
                // 更改文字
                notification.contentView.setTextViewText(R.id.bartext, l+ "%");
                Log.e("----apk",l+"%");
                // 更改进度条
                notification.contentView.setProgressBar(R.id.progressBar1, 100,(int)l, false);
                // 发送消息
                nm.notify(0, notification);

//              Intent intent = new Intent();
//				intent.setAction("cancel");
//				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
//				notification.contentView.setOnClickPendingIntent(R.id.btnStartStop, pendingIntent);

            }

            @Override
            public void onStart() {
                notification.contentView.setTextViewText(R.id.msg, "开始下载：VR室");
                nm.notify(titleId, notification);
            }

        });
    }


    public static HttpHandler<File> getHandler(){
        return httpHandler;
    }


    @Override
    public void onDestroy() {
        //下载完成时，清楚该通知，自动安装
        nm.cancel(titleId);
        System.out.println("UpdateVersionService----onDestroy");
//		try {
//			GdmsaecApplication.db.deleteAll(VersionInfo.class);
//		} catch (DbException e) {
//			e.printStackTrace();
//		}
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**
     * 下载文件线程
     *
     * @author coolszy
     *@date 2012-4-26
     *@blog http://blog.92coding.com
     */
    	/* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /* 记录进度条数量 */
    private int progress;
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {

                    URL url = new URL(downUrl);
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(SDCardUtils.getRootDirectory()+"/updateVersion/");
                    // 判断文件目录是否存在
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(file, "gdmsaec-app.apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    int numread = 0 ;
                    // 写入到文件中
                    while((numread = is.read(buf))!=-1){
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        Log.e("---progress",progress+"%"+numread);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        // 写入文件
                        fos.write(buf, 0, numread);
                    }
                    fos.close();
                    is.close();
                    mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
//                    do
//                    {
//                         numread = is.read(buf);
//                        count += numread;
//                        // 计算进度条位置
//                        progress = (int) (((float) count / length) * 100);
//                        Log.e("---progress",progress+"%"+numread);
//                        // 更新进度
//                        mHandler.sendEmptyMessage(DOWNLOAD);
//                        if (numread <= 0)
//                        {
//                            // 下载完成
//                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
//                            break;
//                        }
//                        // 写入文件
//                        fos.write(buf, 0, numread);
//                    } while (numread != -1);// 点击取消就停止下载.

                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    };
    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 正在下载
                case DOWNLOAD:
                    notification.contentView.setTextViewText(R.id.msg, "正在下载：VR室");
                    // 更改文字
                    notification.contentView.setTextViewText(R.id.bartext, progress+ "%");
                    // 更改进度条
                    notification.contentView.setProgressBar(R.id.progressBar1, 100,progress, false);
                    // 发送消息
                    nm.notify(0, notification);
                    break;
                case DOWNLOAD_FINISH:
                    ToastCommom.createInstance().ToastShow(UpdateVersionService.this,"anzhuang");
                    // 更改文字
                    notification.contentView.setTextViewText(R.id.msg, "下载完成!点击安装");

//                notification.contentView.setViewVisibility(R.id.btnStartStop, View.GONE);
//                notification.contentView.setViewVisibility(R.id.btnCancel,View.GONE);
                    // 发送消息
                    nm.notify(0, notification);
                    stopSelf();
                    //收起通知栏
                    UpdateVersionUtil.collapseStatusBar(UpdateVersionService.this);
                    //自动安装新版本
                    Intent installIntent = ApkUtils.getInstallIntent(UpdateVersionService.this,updateFile);
                    startActivity(installIntent);
                    break;
                default:
                    break;
            }
        };
    };
}
