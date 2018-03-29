package vr.xinjing.com.vrmc.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.demo.hotrepair.entryptlibrary.EncryptClick;
import com.demo.hotrepair.entryptlibrary.EncryptFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.itangqi.greendao.Note;
import vr.xinjing.com.vrmc.Appaplication;
import vr.xinjing.com.vrmc.AuthorizationActivity;
import vr.xinjing.com.vrmc.MainActivity;
import vr.xinjing.com.vrmc.PlayerFragmentAcivity;
import vr.xinjing.com.vrmc.bean.IConstant;
import vr.xinjing.com.vrmc.bean.LastAyncInfo;
import vr.xinjing.com.vrmc.bean.LocalInfo;
import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.bean.PrescriptionContent;
import vr.xinjing.com.vrmc.bean.PrescriptionInfo;
import vr.xinjing.com.vrmc.bean.TaskInfo;
import vr.xinjing.com.vrmc.imp.AyncTime;
import vr.xinjing.com.vrmc.imp.LoginView;
import vr.xinjing.com.vrmc.imp.QueryPrescription;
import vr.xinjing.com.vrmc.imp.TaskListimp;
import vr.xinjing.com.vrmc.presenter.AyncTimePresenter;
import vr.xinjing.com.vrmc.presenter.LoginPresenter;
import vr.xinjing.com.vrmc.presenter.QueryPrescriptionPresenter;
import vr.xinjing.com.vrmc.presenter.TasklistPresenter;
import vr.xinjing.com.vrmc.utils.Logger;
import vr.xinjing.com.vrmc.utils.MyLog;
import vr.xinjing.com.vrmc.utils.MyToast;
import vr.xinjing.com.vrmc.utils.NoteService;
import vr.xinjing.com.vrmc.utils.SpUtils;
import vr.xinjing.com.vrmc.utils.ToastCommom;

/**
 * huoqu
 * Created by raytine on 2017/3/2.
 */

public class SynVedioService extends Service implements TaskListimp,EncryptClick{
    private final String TAG="SynVedioService";
    private Timer timer = new Timer();;//每过一个小时，刷新服务器视频内容列表
    private String token;
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private TasklistPresenter getTaskPresenter;
    private QueryPrescriptionPresenter queryPrescriptionPresenter;
    private LocalInfo users;
    LoginPresenter lp;
    private ActivityManager manager;
    private TaskInfo taskInfo;
    private boolean tokenChange = false;
//    private MyTimerTask myTimerTask;
    private EncryptFile encryptFile;
    private NoteService noteService;//数据库操作类
    @Override
    public void onCreate() {
//        IntentFilter mFilter = new IntentFilter();
//        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(mReceiver, mFilter);
        getTaskPresenter = new TasklistPresenter(this);
        manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        EventBus.getDefault().register(this);
        noteService = ((Appaplication) getApplication()).noteService;//数据库操作类
        encryptFile = new EncryptFile(this);
        token = SpUtils.getInstance().getToken();
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
//            token = intent.getStringExtra("token");
            Bundle extras = intent.getExtras();
            users = (LocalInfo) extras.getSerializable("user");
            Log.e("---service",users.getPassword()+" == "+users.getUsername());
//        timer.schedule(timerTask, 0, );
//            if (timer != null){
//                if (myTimerTask != null){
//                    myTimerTask.cancel();  //将原任务从队列中移除
//                }
//                myTimerTask = new MyTimerTask();
//                timer.schedule(myTimerTask, 0, 5000);
//            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                Log.e("-----------","网络状态已经改变");
                connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    Log.e("-----------","当前网络名称："+ name);
                } else {
                    Log.e("-----------","没有可用网络");
                }
            }
        }
    };

    @Override
    public void encrySuccess() {

    }

    @Override
    public void encryFailed() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * b播放界面手动退出后，进行加密
     * @param message
     */
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void backEventBus(String message){
        if (message.equals("back")){
            for (int i = 0; i <taskInfo.getData().size() ; i++) {
                if (taskInfo.getData().get(i).getType()==1){
                    Note nameById = noteService.getNameById(taskInfo.getData().get(i).getContent() + "");
                   if (nameById.getIssecret() != null){
                       if (!nameById.getIssecret()&&taskInfo.getData().get(i).getJmvalues()!=0){
                           Logger.e("++synVedioService",nameById.getId()+"加密成功");
                           encryptFile.EncryFile(nameById.getPath(),taskInfo.getData().get(i).getJmvalues());
                           nameById.setIssecret(true);
                           noteService.updateData(nameById.getContentid(),nameById);
                       }
                   }
                }
            }
        } else if (message.equals("pushtask"))
        {
            Map<String, String> ayncmap = new HashMap<>();
            ayncmap.put("token", SpUtils.getInstance().getToken());
            getTaskPresenter.getTask(ayncmap);

        } else if (taskInfo.getData().size() == 2 && message.equals("dsa")) {
            TaskInfo.DataBean dataBean = taskInfo.getData().get(1);
            sendBroadCast(dataBean);
        }
    }
    @Override
    public void gettasksuccess(TaskInfo info) {
        if (info.getData() == null || info.getData().size() == 0){
//            Logger.e("----","没有指令任务");
        }else{ //多条指令
            Logger.e("----service","有指令任务"+info.getData().size());
            for (int i = 0; i <info.getData().size() ; i++) {
//                TaskInfo.DataBean dataBean = info.getData().get(i);
//                sendBroadCast(dataBean);
                Logger.e("----service","有指令类型"+info.getData().get(i).getType());
            }
            taskInfo = info;
            TaskInfo.DataBean dataBean = info.getData().get(0);
            sendBroadCast(dataBean);
        }

    }
    public synchronized void  sendBroadCast(TaskInfo.DataBean dataBean){
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName();
        if (shortClassName.equals(".MainActivity") && dataBean.getType() ==1){
            //发送特定action的广播
            Logger.e("----MainActivity","有指令任务"+dataBean.getType());
            Intent intent = new Intent();
            intent.putExtra("ischange","no");
            intent.setAction("android.intent.action.MY_RECEIVER");
            Bundle bundle = new Bundle();
            bundle.putSerializable("task",dataBean);
            if (tokenChange){
                bundle.putString("token",token);
            }
            intent.putExtras(bundle);
            sendBroadcast(intent);
            tokenChange = false;
        }else//  PlayerFragmentAcivity
        if (shortClassName.equals(".PlayerActivity"))
        {
            Logger.e("----PlayerFragmentAci","有指令任务"+dataBean.getType());
            //发送特定action的广播
            Intent intent = new Intent();
            intent.putExtra("ischange","no");
            intent.setAction("android.intent.action.MY_RECEIVERS");
            Bundle bundle = new Bundle();
            bundle.putSerializable("task",dataBean);
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }else if (taskInfo.getData().size() == 2){
            TaskInfo.DataBean dataBeans = taskInfo.getData().get(1);
            sendBroadCast(dataBeans);
        }
    }
    @Override
    public void gettaskfailed(String msh) {
        Logger.e("---------",msh);
    }

    @Override
    public void tokenchange() {
//        MyToast.makeText(this,"登录失效,请重新点击播放", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.MY_RECEIVER");
//        intent.putExtra("ischange","yes");
//        sendBroadcast(intent);
//        timer.cancel();
//        stopSelf();
        Map<String, String> priArgs = new HashMap<>();
        priArgs.put("appId", IConstant.devoceId(this));
        priArgs.put("appVersion", IConstant.getVersion(this));
        priArgs.put("channel", "null");
        priArgs.put("deviceModel", IConstant.getModel());
        priArgs.put("deviceSystem", "android");
        priArgs.put("deviceVersion", IConstant.getVersionRelease());
        priArgs.put("password", users.getPassword());
        priArgs.put("username", users.getUsername());
        lp = new LoginPresenter(new LoginView() {
            @Override
            public void updateView(LoginInfo user) {
                token = user.getData().getToken();
                tokenChange = true;
                Logger.e("------------s重新的token", token);
                users.setToken(token);
                saveData(users);
//                xinjingSeacher.performClick();
            }

            @Override
            public void showProgressDialog() {

            }

            @Override
            public void hideProgressDialog() {

            }

            @Override
            public void showError(String msg) {

            }
        }, priArgs);
        lp.Login();
    }
    public void saveData(LocalInfo info) {
        SpUtils instance = SpUtils.getInstance();
        instance.init(this);
        instance.saveUser(info);
    }
}
