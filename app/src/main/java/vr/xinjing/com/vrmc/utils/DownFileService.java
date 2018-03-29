package vr.xinjing.com.vrmc.utils;

import android.app.Notification;
import android.app.NotificationManager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;


import me.itangqi.greendao.Note;
import vr.xinjing.com.vrmc.Appaplication;
import vr.xinjing.com.vrmc.R;
import vr.xinjing.com.vrmc.bean.IConstant;

/**
 * Created by raytine on 2017/2/23.
 */

public class DownFileService extends Service {
    private HttpUtils httpUtils;
    private long initTotal = 0;//文件的总长度
    private static HttpHandler<File> httpHandler;
    private String names;
    private File file;
    private NoteService noteService;//数据库操作类
    private int conpletenumber = 0;//下载完成数
    private long cm = 0;

    @Override
    public void onCreate() {
        MyLog.e("--------------", "onCreate开始咯");
        httpUtils = new HttpUtils();
        httpUtils.configRequestThreadPoolSize(1);
        noteService = ((Appaplication) getApplication()).noteService;
        showNotificationProgress(DownFileService.this);
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyLog.e("--------------", "onStartCommand开始咯");
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            String url = bundle.getString("downloadUrl");
            String time = bundle.getString("Aynctime");
            String date = bundle.getString("date");
            String contentId = bundle.getString("ContentId");
            int type = bundle.getInt("type");
            int isencryption = bundle.getInt("issecret");
            long vedioSize = bundle.getLong("vedioSize");
//            int count = bundle.getInt("count");
            int countnumber = bundle.getInt("countnumber");
            //文件命名改为contentID+mp4
            file = new File(IConstant.getStroagePath(this) + contentId + ".mp4");
            downLoadFile(url, file, vedioSize, time, contentId, countnumber, date, type, isencryption);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void downLoadFile(String url, File file, long vedioSize, String time, String contentId, int cnm, String date, int type, int isencryption) {
        httpHandler = httpUtils.download(url, file.getAbsolutePath(), true, false, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                cm = 0;
                initTotal = 0;
                downloadSuccess();
                File result = responseInfo.result;
                long l = file.length(); //下载文件的大小
                long l1 = availableSize();//手机内存大小
                MyLog.e("----daxiao", "网络文件大小 ：" + vedioSize + "--------下载文件大小 :" + l + "-----------手机内存大小 :" + l1);
                if (vedioSize > l || l1 < l) {
                    MyLog.e("------------downfileser", "文件下载异常");
                    result.delete();
                } else {
                    conpletenumber += 1;
                    MyLog.e("----下载完成数量", conpletenumber + "个");
                    if (conpletenumber == cnm) {
                        stopSelf();
                        SpUtils.getInstance().saveLastAynvTime(time);
                        MyLog.e("------------downfileser", "同步时间保存了");
                    }
                    if (noteService.isExistByContentId(contentId)) {
                        Note ci = new Note();
                        ci.setName(result.getName());
                        ci.setState(true);
                        if (isencryption == 0) {
                            ci.setIssecret(false);
                        } else {
                            ci.setIssecret(true);
                        }
                        ci.setPath(file.getAbsolutePath());
                        ci.setUrl(url);
                        ci.setType(type);
                        ci.setDate(date);
                        ci.setVodeosize(vedioSize);
                        ci.setContentid(contentId);
                        noteService.updateData(contentId, ci);
                        MyLog.e("------------downfileser", "数据库操作完成");
                    } else {
                        Note ci = new Note();
                        ci.setName(result.getName());
                        ci.setState(true);
                        if (isencryption == 0) {
                            ci.setIssecret(false);
                        } else {
                            ci.setIssecret(true);
                        }
                        ci.setPath(file.getAbsolutePath());
                        ci.setUrl(url);
                        ci.setType(type);
                        ci.setDate(date);
                        ci.setVodeosize(vedioSize);
                        ci.setContentid(contentId);
                        noteService.insertNote(ci);
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                downloadFail();
//                String st = file.getAbsolutePath();
//                if (file.exists() && st.endsWith(".mp3")) {
//                    file.delete();
//                }
                if (e.getExceptionCode() == 416) {
                    MyLog.e("--------------", "文件已经下载了");
                    Note ci = new Note();
                    ci.setName(contentId+".mp4");
                    ci.setState(true);
                    if (isencryption == 0) {
                        ci.setIssecret(false);
                    } else {
                        ci.setIssecret(true);
                    }
                    ci.setPath(file.getAbsolutePath());
                    ci.setUrl(url);
                    ci.setType(type);
                    ci.setDate(date);
                    ci.setVodeosize(vedioSize);
                    ci.setContentid(contentId);
                    noteService.updateData(contentId, ci);
                } else {
                    if (file.exists()) {
                        file.delete();
                    }else {
                        MyLog.e("--------------", "文件下载失败"+s);
                    }
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                if (initTotal == 0) {//说明第一次开始下载
                    initTotal = total;
                }

                if (initTotal != total) {//说明下载过程中暂停过，文件的总长度出现问题  就把初始的文件的长度赋值给他重新计算已经下载的比例
                    total = initTotal;
                }

                long currentMillis = System.currentTimeMillis();
                if (currentMillis - cm > 2000) {
                    cm = currentMillis;
                    Long l = current * 100 / total;
                    MyLog.e("--------------", l + "%");
                    updateNotification(total, current, cnm - conpletenumber);
                }
                super.onLoading(total, current, isUploading);
            }

            @Override
            public void onStart() {
                super.onStart();
            }
        });
    }

    /**
     * 下载失败
     */
    private void downloadFail() {
        status = Status.FAIL;
        mRemoteViews.setTextViewText(R.id.tv_message, "下载失败");
        notificationManager.notify(NOTIFICATION_ID, notification);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    /**
     * 下载成功
     */
    private void downloadSuccess() {
        status = Status.SUCCESS;
        mRemoteViews.setTextViewText(R.id.tv_message, "下载完成");
        notificationManager.notify(NOTIFICATION_ID, notification);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    private void updateNotification(long total, long current, int name) {
        mRemoteViews.setTextViewText(R.id.tv_message, "下载中");
        mRemoteViews.setTextViewText(R.id.tv_name, "共有" + name + "个内容");
        mRemoteViews.setTextViewText(R.id.tv_size, formatSize(current) + "/" + formatSize(total));
        int result = Math.round((float) current / (float) total * 100);
        mRemoteViews.setTextViewText(R.id.tv_progress, result + "%");
        mRemoteViews.setProgressBar(R.id.pb, 100, result, false);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        httpHandler.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static HttpHandler<File> getHandler() {
        return httpHandler;
    }

    /**
     * 获取手机剩余内存大小
     *
     * @return 手机剩余内存(单位：byte)
     */
    public static long availableSize() {
        // 取得SD卡文件路径
        File file = Environment.getExternalStorageDirectory();
        StatFs fs = new StatFs(file.getPath());
        // 获取单个数据块的大小(Byte)
        int blockSize = fs.getBlockSize();
        // 空闲的数据块的数量
        long availableBlocks = fs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    /**
     * 当前在状态 默认正在下载中
     */
    private Status status = Status.DOWNLOADING;

    /**
     * 通知栏操作的四种状态
     */
    private enum Status {
        DOWNLOADING, PAUSE, FAIL, SUCCESS
    }

    /**
     * 通知
     */
    private Notification notification;
    /**
     * 通知的Id
     */
    private final int NOTIFICATION_ID = 1;
    /**
     * 通知管理器
     */
    private NotificationManager notificationManager;
    /**
     * 通知栏的远程View
     */
    private RemoteViews mRemoteViews;

    /**
     * 显示一个下载带进度条的通知
     *
     * @param context 上下文
     */
    public void showNotificationProgress(Context context) {
        /**进度条通知构建**/
        NotificationCompat.Builder builderProgress = new NotificationCompat.Builder(context);
        /**设置为一个正在进行的通知**/
        builderProgress.setOngoing(true);
        /**设置小图标**/
        builderProgress.setSmallIcon(R.mipmap.logo);

        /**新建通知自定义布局**/
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);
        /**进度条ProgressBar**/
        mRemoteViews.setProgressBar(R.id.pb, 100, 0, false);
        /**提示信息的TextView**/
        mRemoteViews.setTextViewText(R.id.tv_message, "准备下载...");
        /**设置左侧小图标*/
        mRemoteViews.setImageViewResource(R.id.iv, R.mipmap.main_logo);
        /**设置自定义布局**/
        builderProgress.setContent(mRemoteViews);
        /**设置滚动提示**/
        builderProgress.setTicker("开始下载...");
        notification = builderProgress.build();
        /**设置不可手动清除**/
        notification.flags = Notification.FLAG_NO_CLEAR;
        /**获取通知管理器**/
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        /**发送一个通知**/
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @return
     */
    private String formatSize(long size) {
        String format;
        if (size >= 1024 * 1024) {
            format = byteToMB(size) + "M";
        } else if (size >= 1024) {
            format = byteToKB(size) + "k";
        } else {
            format = size + "b";
        }
        return format;
    }

    /**
     * byte转换为MB
     *
     * @param bt 大小
     * @return MB
     */
    private float byteToMB(long bt) {
        int mb = 1024 * 1024;
        float f = (float) bt / (float) mb;
        float temp = (float) Math.round(f * 100.0F);
        return temp / 100.0F;
    }

    /**
     * byte转换为KB
     *
     * @param bt 大小
     * @return K
     */
    private int byteToKB(long bt) {
        return Math.round((bt / 1024));
    }

}
