package vr.xinjing.com.vrmc;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.hotrepair.entryptlibrary.EncryptClick;
import com.demo.hotrepair.entryptlibrary.EncryptFile;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.task.Priority;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.greendao.Note;
import vr.xinjing.com.vrmc.activity.BaseActivity;
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
import vr.xinjing.com.vrmc.service.SynVedioService;
import vr.xinjing.com.vrmc.update.ApkUtils;
import vr.xinjing.com.vrmc.update.SDCardUtils;
import vr.xinjing.com.vrmc.update.UpdateStatus;
import vr.xinjing.com.vrmc.update.VersionInfo;
import vr.xinjing.com.vrmc.utils.DownFileService;
import vr.xinjing.com.vrmc.utils.Logger;
import vr.xinjing.com.vrmc.utils.MyLog;
import vr.xinjing.com.vrmc.utils.MyToast;
import vr.xinjing.com.vrmc.utils.NoteService;
import vr.xinjing.com.vrmc.utils.SpUtils;
import vr.xinjing.com.vrmc.utils.ToastCommom;
import vr.xinjing.com.vrmc.utils.UpdateVersionUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener, QueryPrescription, AyncTime, EncryptClick {

    @BindView(R.id.app_name)
    TextView appName;
    LoginPresenter lp;
//    @BindView(R.id.img_img)
//    ImageView imgImg;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    private LocalInfo users;
    //    private QueryPatientPresenter queryPatientPresenter;13868001234
    private QueryPrescriptionPresenter queryPrescriptionPresenter;
    private ProgressDialog dialog;
    private List patient = new ArrayList();
    private List<PrescriptionContent.DataBean> patient_content = new ArrayList();
    private Intent ZLintent;//任务指令
    private Intent SYNintent;//任务指令
    private String token;
    private boolean isExit = false;
    private String LastAyncTime = "2016-10-20 09:38:45";//最后一次同步的时间
    private String NowAyncTime;
    private Timer mTimer = new Timer();//定时器检查同步内容
    private AyncTimePresenter ayncp;
    private TasklistPresenter getTaskPresenter;
    //    private EndTaskPresenter endTaskPresenter;
    private List<PrescriptionInfo> ayncPath = new ArrayList<>();//同步内容url的集合
    //    private BaseDao baseDao;
    private NoteService noteService;//数据库操作类
    private MyReceiver receiver; //定义接收指令的广播
    private MyTimerTask timerTask;
    private EncryptFile encryptFile;
    private Note n;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mReceiver, mFilter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MyLog.e("+-----------onCreate", "我有回来了");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        getData();
        encryptFile = new EncryptFile(MainActivity.this);//初始化加密解密类
        noteService = ((Appaplication) getApplication()).noteService;//数据库操作类
//
//        List<Note> all = noteService.getAll();
//        Note noteById = noteService.getNoteById(16);
//        noteById.setIssecret(false);
//        noteService.updateData(noteById.getContentid(),noteById);
//        Note noteByIds = noteService.getNoteById(18);
//        noteByIds.setIssecret(false);
//        noteService.updateData(noteByIds.getContentid(),noteById);

        queryPrescriptionPresenter = new QueryPrescriptionPresenter(MainActivity.this);
//        endTaskPresenter = new EndTaskPresenter(MainActivity.this);
        ayncp = new AyncTimePresenter(MainActivity.this);//获取同步内容的present
//        getTaskPresenter = new TasklistPresenter(MainActivity.this);

        //注册
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVER");
        registerReceiver(receiver, filter);
        //开启获取指令服务
        ZLintent = new Intent(MainActivity.this, SynVedioService.class);
        ZLintent.setPackage("vr.xinjing.com.vrmc.service");
        ZLintent.putExtra("token", token);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", users);
        ZLintent.putExtras(bundle);
        startService(ZLintent);
        //
        MyLog.e("---------------token", token);
        appName.setText("版本号：" + ApkUtils.getVersionName(this));
        tvUpdate.setOnClickListener(this);
        UpadteVersionm();
    }

    //检查版本更新
    private void UpadteVersionm() {
        Map<String, String> priArgsss = new HashMap<>();
        priArgsss.put("systemVersion", "2");
        priArgsss.put("token", token);
        UpdateVersionUtil.checkVersion(priArgsss, MainActivity.this, new UpdateVersionUtil.UpdateListener() {

            @Override
            public void onUpdateReturned(int updateStatus, VersionInfo versionInfo) {
                //判断回调过来的版本检测状态

                switch (updateStatus) {
                    case UpdateStatus.YES:
                        //弹出更新提示
                        UpdateVersionUtil.showDialog(MainActivity.this, versionInfo);
                        break;
                    case UpdateStatus.NO:
                        //没有新版本
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "已经是最新版本了!");
                        if (mTimer != null) {
                            if (timerTask != null) {
                                timerTask.cancel();  //将原任务从队列中移除
                            }
                            timerTask = new MyTimerTask();
                            mTimer.schedule(new MyTimerTask(), 0, 1000 * 60 * 60 * 24);
                        }
                        clearUpateFile(MainActivity.this);
                        break;
                    case UpdateStatus.NOWIFI:
                        //当前是非wifi网络
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "只有在wifi下更新！");
//							DialogUtils.showDialog(MainActivity.this, "温馨提示","当前非wifi网络,下载会消耗手机流量!", "确定", "取消",new DialogOnClickListenner() {
//								@Override
//								public void btnConfirmClick(Dialog dialog) {
//									dialog.dismiss();
//									//点击确定之后弹出更新对话框
//									UpdateVersionUtil.showDialog(SystemActivity.this,versionInfo);
//								}
//
//								@Override
//								public void btnCancelClick(Dialog dialog) {
//									dialog.dismiss();
//								}
//							});
                        break;
                    case UpdateStatus.ERROR:
                        //检测失败
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "检测失败，请稍后重试！");
                        break;
                    case UpdateStatus.TIMEOUT:
                        //链接超时
                        ToastCommom.createInstance().ToastShow(getApplicationContext(), "链接超时，请检查网络设置!");
                        break;
                }
            }
        });
    }

    /**
     * 清理升级文件
     *
     * @param context
     */
    private void clearUpateFile(final Context context) {
        File updateDir;
        File updateFile;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            updateDir = new File(SDCardUtils.getRootDirectory() + "/updateVersion/gdmsaec-app.apk");
        } else {
            updateDir = context.getFilesDir();
        }
//        updateFile = new File(updateDir.getPath(), context.getResources()
//                .getString(R.string.app_name) + ".apk");
        if (updateDir.exists()) {
            MyLog.e("---------------update", "升级包存在，删除升级包");
            updateDir.delete();
        } else {
            MyLog.e("-----------update", "升级包不存在，不用删除升级包");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastCommom.createInstance().ToastShow(getApplicationContext(), "再按一次退出程序");
                mHandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_update:
                UpadteVersionm();
                break;
        }
    }

    PrescriptionContent.DataBean datatimes;

    //内容列表
    @Override
    public void update(PrescriptionContent p) {
        if (p.getData() != null) {
            for (int i = 0; i < p.getData().size(); i++) {
                patient_content.add(p.getData().get(i));
            }
        } else {
            ToastCommom.createInstance().ToastShow(MainActivity.this, "没有数据");
        }
    }

    //内容详情
    @Override
    public void update(PrescriptionInfo p, boolean is) {
        if (p.getData() != null || p.getData().getExt() != null) {
            if (is) {
                String Path = p.getData().getExt().getContent();
                String names = getPathName(Path);
                if (noteService.isExistByContentId(p.getData().getId())) {
                    Note videoInfo = noteService.getNameById(p.getData().getId());
                    if (videoInfo.getState()) {
                        Intent intent = new Intent(MainActivity.this, PlayerFragmentAcivity.class);
                        intent.putExtra("url", videoInfo.getPath());
                        MyLog.e("-------------path", videoInfo.getPath());
                        intent.putExtra("type", p.getData().getType());
                        startActivityForResult(intent, 1);
                    } else {
                        ToastCommom.createInstance().ToastShow(this, "资源等待同步");
                    }
                } else {
                    ToastCommom.createInstance().ToastShow(this, "资源还未同步");
                }
//                List<Note> all = noteService.getAll();
//                Intent intent = new Intent(MainActivity.this, PlayerFragmentAcivity.class);
//                intent.putExtra("url", all.get(0).getPath());
//                MyLog.e("-------------path", all.get(0).getPath());
//                intent.putExtra("type", 1);
//                startActivity(intent);

            } else { //需要同步内容详情的接口回调
                ayncPath.add(p);
                MyLog.e("----下载time", p.getData().getVideoupdateAt() + "-" + needAynvcount + "==" + ayncPath.size());
                if (ayncPath.size() == needAynvcount) {
                    List<PrescriptionInfo> newLisst = new ArrayList<>();
                    newLisst.addAll(ayncPath);
                    if (last.size() != 0) {
                        last.clear();
                    }
                    needAynvcount = 0;
                    for (int i = 0; i < newLisst.size(); i++) {
                        String FileName = getPathName(newLisst.get(i).getData().getExt().getContent());
                        if (FileName.equals("htt")) {
                            ayncPath.remove(newLisst.get(i));
                        }
                    }
                    List<Note> all = noteService.getAll();//获得数据库所有文件的记录,来校验新获得的文件
                    List<PrescriptionInfo> newList = new ArrayList<>();
                    newList.addAll(ayncPath);
                    for (int i = 0; i < newList.size(); i++) {//添加数据
                        //根据url获取文件名
                        String FileName = getPathName(newList.get(i).getData().getExt().getContent());
                        String contentId = newList.get(i).getData().getId();
                        //根据contentId获取文件名
                        String FileContentIdName = contentId + ".mp4";
                        if (FileName.equals("htt")) {
                            ayncPath.remove(newList.get(i));
                        } else if (FileName != null) {
                            //通过contentId 查询视频存在数据库 STROAGE_PATH
                            if (noteService.isExistByContentId(contentId)) {
                                Note noteByText = noteService.getNameById(contentId);
                                File f = new File(IConstant.getStroagePath(this) + noteByText.getName());
                                File f2 = new File(IConstant.getStroagePath(this) + contentId + ".mp4");
                                if (f.exists() || f2.exists()) {//视频存在文件夹中,更新后视频名为contentId.故需要兼容以前的命名视频
                                    String t1 = newList.get(i).getData().getVideoupdateAt();
                                    String t2 = noteByText.getDate();
                                    if (newList.get(i).getData().getVideoupdateAt().equals(noteByText.getDate())) {//相同视频未更新过
//                                        Note noteByText = noteService.getNoteByText(FileName);
                                        noteByText.setUrl(newList.get(i).getData().getExt().getContent());
                                        noteByText.setContentid(newList.get(i).getData().getId());
                                        noteService.updateData(contentId, noteByText);
                                        ayncPath.remove(newList.get(i));
                                    } else {//相同视频更新过
                                        List<Note> nameByIdList = noteService.getNameByIdList(contentId);
                                        for (int j = 0; j < nameByIdList.size(); j++) {
                                            Note note = nameByIdList.get(j);
                                            File fs = new File(IConstant.getStroagePath(this) + note.getName());
                                            File fs2 = new File(IConstant.getStroagePath(this) + contentId + ".mp4");
                                            if (fs.exists()) {
                                                fs.delete();
                                            }
                                            if (fs2.exists()) {
                                                fs2.delete();
                                            }
                                        }
                                        Note ci = new Note();
                                        ci.setName(FileContentIdName);
                                        ci.setState(false);
                                        if (newList.get(i).getData().getExt().getIsencryption() == 0) {
                                            ci.setIssecret(false);
                                        } else {
                                            ci.setIssecret(true);
                                        }
                                        ci.setType(newList.get(i).getData().getType());
                                        ci.setPath(IConstant.getStroagePath(this) + FileContentIdName);
                                        ci.setUrl(newList.get(i).getData().getExt().getContent());
                                        ci.setContentid(newList.get(i).getData().getId());
                                        ci.setDate(newList.get(i).getData().getVideoupdateAt());
                                        ci.setVodeosize(newList.get(i).getData().getExt().getVideosize());
                                        noteService.updateData(contentId, ci);
                                        ayncPath.remove(newList.get(i));
                                    }

                                } else {//视频不存在存在文件夹中
                                    Note ci = new Note();
                                    ci.setName(FileContentIdName);
                                    ci.setState(false);
                                    if (newList.get(i).getData().getExt().getIsencryption() == 0) {
                                        ci.setIssecret(false);
                                    } else {
                                        ci.setIssecret(true);
                                    }
                                    ci.setType(newList.get(i).getData().getType());
                                    ci.setPath(IConstant.getStroagePath(this) + FileContentIdName);
                                    ci.setUrl(newList.get(i).getData().getExt().getContent());
                                    ci.setContentid(newList.get(i).getData().getId());
                                    ci.setDate(newList.get(i).getData().getVideoupdateAt());
                                    ci.setVodeosize(newList.get(i).getData().getExt().getVideosize());
                                    noteService.updateData(contentId, ci);
                                    ayncPath.remove(newList.get(i));
                                }

                            } else //通过contentId 查询视频不存在数据库中
                                if (!noteService.isExistByContentId(contentId)) {
                                    Note n1 = new Note();
                                    n1.setName(FileContentIdName);
                                    n1.setPath(IConstant.getStroagePath(this) + FileContentIdName);
                                    n1.setState(false);
                                    if (newList.get(i).getData().getExt().getIsencryption() == 0) {
                                        n1.setIssecret(false);
                                    } else {
                                        n1.setIssecret(true);
                                    }
                                    n1.setType(newList.get(i).getData().getType());
                                    n1.setUrl(newList.get(i).getData().getExt().getContent());
                                    n1.setContentid(newList.get(i).getData().getId());
                                    n1.setDate(newList.get(i).getData().getVideoupdateAt());
                                    n1.setVodeosize(newList.get(i).getData().getExt().getVideosize());
                                    noteService.insertNote(n1);
                                    ayncPath.remove(newList.get(i));
                                }
                        }
                    }
                    HttpHandler<File> handler = DownFileService.getHandler();
                    noteService = NoteService.getNoteService(MainActivity.this);
                    all = noteService.getAll();//获得数据库所有文件的记录,来校验新获得的文件
                    List<String> contentids = new ArrayList<>();
                    for (int i = 0; i < ayncPath.size(); i++) {
                        contentids.add(ayncPath.get(i).getData().getId());
                    }
                    for (int i = 0; i < all.size(); i++) {

                        if (handler == null) {
//                         if (handler.getRequestCallBack().getRequestUrl() == all.get(i).getUrl() && handler.isCancelled()){//获得数据库中没有进行的任务线程
                            if (!all.get(i).getState() && !contentids.contains(all.get(i).getContentid())) {//文件没有下载完成
                                PrescriptionInfo pp = new PrescriptionInfo();
                                PrescriptionInfo.DataBean ppb = new PrescriptionInfo.DataBean();
                                PrescriptionInfo.DataBean.ExtBean ppe = new PrescriptionInfo.DataBean.ExtBean();
                                ppb.setType(all.get(i).getType());
                                ppb.setVideoupdateAt(all.get(i).getDate());
                                ppb.setId(all.get(i).getContentid());
                                ppe.setContent(all.get(i).getUrl());
                                ppe.setVideosize(all.get(i).getVodeosize());
                                ppb.setExt(ppe);
                                pp.setData(ppb);
                                ayncPath.add(pp);
                            }
//                         }
                        } else {
                            MyLog.e("----handler!=null", "有下载handler");
                            if (handler.isCancelled()) {//获得数据库中没有进行的任务线程
                                MyLog.e("----handler=isCancelled", "handler被取消了");
                                if (!all.get(i).getState()&& !contentids.contains(all.get(i).getContentid())) {//文件没有下载完成
                                    PrescriptionInfo pp = new PrescriptionInfo();
                                    PrescriptionInfo.DataBean ppb = new PrescriptionInfo.DataBean();
                                    PrescriptionInfo.DataBean.ExtBean ppe = new PrescriptionInfo.DataBean.ExtBean();
                                    ppb.setType(all.get(i).getType());
                                    ppb.setVideoupdateAt(all.get(i).getDate());
                                    ppb.setId(all.get(i).getContentid());
                                    ppe.setContent(all.get(i).getUrl());
                                    ppe.setVideosize(all.get(i).getVodeosize());
                                    ppb.setExt(ppe);
                                    pp.setData(ppb);
                                    ayncPath.add(pp);
                                }
                            } else {//该下载任务在下次刷新定时器时还处于执行状态
                                MyLog.e("----handler=isCancelled", "handler还有");
                                List<PrescriptionInfo> newLists = new ArrayList<>();
                                newLists.addAll(ayncPath);
                                for (int j = 0; j < newLists.size(); j++) {
                                    if (getPathName(all.get(i).getUrl()).equals(getPathName(newLists.get(j).getData().getExt().getContent()))&& contentids.contains(all.get(i).getContentid())) {
                                        ayncPath.remove(newLists.get(j));
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < ayncPath.size(); i++) {
                        if (i == ayncPath.size() - 1) {
                            SYNintent = new Intent(MainActivity.this, DownFileService.class);
                            SYNintent.setPackage("vr.xinjing.com.vrmc.utils");
                            SYNintent.putExtra("downloadUrl", ayncPath.get(i).getData().getExt().getContent());
//                            SYNintent.putExtra("count", i);
                            SYNintent.putExtra("countnumber", ayncPath.size());
                            SYNintent.putExtra("Aynctime", NowAyncTime);
                            SYNintent.putExtra("issecret", ayncPath.get(i).getData().getExt().getIsencryption());
                            SYNintent.putExtra("date", ayncPath.get(i).getData().getVideoupdateAt());
                            SYNintent.putExtra("type", ayncPath.get(i).getData().getType());
                            SYNintent.putExtra("vedioSize", ayncPath.get(i).getData().getExt().getVideosize());
                            SYNintent.putExtra("ContentId", ayncPath.get(i).getData().getId());
                            MyLog.e("-------rengzai", ayncPath.get(i).getData().getVideoupdateAt());
                            startService(SYNintent);
                        } else {
                            SYNintent = new Intent(MainActivity.this, DownFileService.class);
                            SYNintent.setPackage("vr.xinjing.com.vrmc.utils");
                            SYNintent.putExtra("downloadUrl", ayncPath.get(i).getData().getExt().getContent());
//                            SYNintent.putExtra("count", i);
                            SYNintent.putExtra("countnumber", ayncPath.size());
                            SYNintent.putExtra("Aynctime", NowAyncTime);
                            SYNintent.putExtra("issecret", ayncPath.get(i).getData().getExt().getIsencryption());
                            SYNintent.putExtra("date", ayncPath.get(i).getData().getVideoupdateAt());
                            SYNintent.putExtra("type", ayncPath.get(i).getData().getType());
                            SYNintent.putExtra("vedioSize", ayncPath.get(i).getData().getExt().getVideosize());
                            SYNintent.putExtra("ContentId", ayncPath.get(i).getData().getId());
                            MyLog.e("-------rengzai", ayncPath.get(i).getData().getVideoupdateAt());
                            startService(SYNintent);
                        }

                    }
                    if (ayncPath.size() != 0) {
                        ayncPath.clear();
//                        String  noewTime = getNoewAyncTime();
//                        SpUtils.getInstance().saveLastAynvTime(LastAyncTime);
                    }
                }
            }
        } else {
            ToastCommom.createInstance().ToastShow(MainActivity.this, "没有数据");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
//                String result = data.getStringExtra("id");
                //调用结束任务接口
//                    int useTimes = datatimes.getUseTimes();
//                    useTimes += 1;
//                    datatimes.setUseTimes(useTimes);
//                    Map<String, String> endtasks = new HashMap<>();
//                    endtasks.put("token", token);
//                    endtasks.put("vrRoomAppTaskId",result);
//                    endTaskPresenter.endTask(endtasks);

                break;
        }
    }


    @Override
    public void showError(PrescriptionInfo msg, boolean is) {
        if (is) {

        } else {
            needAynvcount -= 1;
        }
    }


//    @Override
//    public void gettasksuccess(TaskInfo info) {
//        if (info.getData() == null || info.getData().size() == 0) {
//            MyLog.e("---------", "没有指令");
//        } else {
//            MyLog.e("---------", info.getData().get(1).getContent()+"");
//            Map<String, String> priArgs = new HashMap<>();
//            priArgs.put("contentId", info.getData().get(1).getContent()+"");
//            priArgs.put("token", token);
//            queryPrescriptionPresenter.setMap(priArgs);
//            queryPrescriptionPresenter.getPrescriptionlistcontent(true);
//        }
//    }
//
//    @Override
//    public void gettaskfailed(String msh) {
//        MyLog.e("---------", msh);
//    }


    //token 发生变化时，重新登录获取token
    @Override
    public void tokenchange() {
        ToastCommom.createInstance().ToastShow(MainActivity.this, "登录异常,请重新点击查询");
        Map<String, String> priArgs = new HashMap<>();
        priArgs.put("appId", IConstant.devoceId(this));
        priArgs.put("appVersion", IConstant.getVersion(this));
        priArgs.put("channel", "null");
        priArgs.put("deviceModel", IConstant.getModel());
        priArgs.put("deviceSystem", "android");
        priArgs.put("deviceVersion", IConstant.getVersionRelease());
        priArgs.put("password", users.getPassword());
        priArgs.put("username", users.getUsername());
        MyLog.e("-----passMain", users.getPassword());
        MyLog.e("-----user", users.getUsername());
        lp = new LoginPresenter(new LoginView() {
            @Override
            public void updateView(LoginInfo user) {
                token = user.getData().getToken();
                MyLog.e("------------重新的token", token);
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

    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public void getData() {
        SpUtils instance = SpUtils.getInstance();
        instance.init(MainActivity.this);
        users = instance.getUser();
        token = users.getToken();

    }

    public void saveData(LocalInfo info) {
        SpUtils instance = SpUtils.getInstance();
        instance.init(MainActivity.this);
        instance.saveUser(info);
    }

    @Override
    public void encrySuccess() {
        Intent intents = new Intent(MainActivity.this, PlayerActivity.class);
        intents.putExtra("url", n.getPath());
        MyLog.e("++解密", n.getId() + "解密");
        n.setIssecret(false);
        noteService.updateData(n.getContentid(), n);
        intents.putExtra("type", 1);
        startActivity(intents);
    }

    @Override
    public void encryFailed() {

    }

    // 计时器
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            LastAyncTime = getLastAyncTime();
            NowAyncTime = getNoewAyncTime();
//            LastAyncTime = "2019-10-20 09:38:45";
            MyLog.e("-----------当前时间", LastAyncTime);
            Map<String, Object> ayncmap = new HashMap<>();
            ayncmap.put("token", token);
            ayncmap.put("paging", paging);
            ayncmap.put("lastSyncAt", LastAyncTime);
            MyLog.e("---haha", token + "--" + paging + "===" + LastAyncTime);
            ayncp.setMap(ayncmap);
            ayncp.getAync();
        }
    }

    ;
    private int needAynvcount = 0;
    private int needAynvcounts = 0;
    private int paging = 1;
    private List<LastAyncInfo.DataBean> last = new ArrayList<>();

    //获取同步内容的回调
    @Override
    public void showsuccess(LastAyncInfo s) {
        MyLog.e("---tongbu", s.getData().size() + "个" + ayncPath.size());
        needAynvcounts = s.getData().size();
        needAynvcount += s.getData().size();
        if (needAynvcounts == 10) {//分页
            for (int i = 0; i < s.getData().size(); i++) {
                last.add(s.getData().get(i));
            }
            paging += 1;
            Map<String, Object> ayncmap = new HashMap<>();
            ayncmap.put("token", token);
            ayncmap.put("paging", paging);
            ayncmap.put("lastSyncAt", LastAyncTime);
            Logger.e("--params", ayncmap.toString());
            ayncp.setMap(ayncmap);
            ayncp.getAync();
        } else if (ayncPath.size() == 0 && needAynvcounts < 10) {//获得需要同步的url
            paging = 1;
            for (int j = 0; j < s.getData().size(); j++) {
                last.add(s.getData().get(j));
            }
            MyLog.e("---xiazai", last.size() + "个");
            //有需要同步的url
            for (int i = 0; i < last.size(); i++) {
                Map<String, String> priArgs = new HashMap<>();
                priArgs.put("contentId", last.get(i).getId());
                priArgs.put("token", token);
//                MyLog.e("---xiazai", last.get(i).getId() + "id" + last.get(i).getName());
                MyLog.e("---xiazaiTAG", last.get(i).getId() + "==id==" + token);
                int ss = checkNetWork();
                if (ss == 0) {
                    ToastCommom.createInstance().ToastShow(MainActivity.this, "请设置网络环境");
                } else {
                    queryPrescriptionPresenter.setMap(priArgs);
                    queryPrescriptionPresenter.getPrescriptionlistcontent(false);
                }
            }
        }
    }

    @Override
    public void showfaild() {
        MyLog.e("---------tongbu", "没有同部署怒");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null)
            this.unregisterReceiver(mReceiver);
        if (receiver != null)
            unregisterReceiver(receiver);
        if (SYNintent != null)
            stopService(SYNintent);
        if (ZLintent != null)
            stopService(ZLintent);
    }

    //根据文件的url来获取数据库保存的名字，路径
    public String getPathName(String url) {
        String names = null;
        try {
            String name = url.substring(0, url.indexOf(".mp") + 4);
            names = name.substring(name.lastIndexOf("/") + 1);
        } catch (Exception e) {
            ToastCommom.createInstance().ToastShow(this, "资源不足");
        }
        return names;
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private boolean isNet = false;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable() && isNet) {
                    //查询数据库，是否有为下载完的
                    MyLog.e("--------------mark", "可用网络");
                    List<Note> all = noteService.getAll();
                    for (int i = 0; i < all.size(); i++) {
                        if (!all.get(i).getState()) {
                            MyLog.e("--------------", "网络启动下载");
                            SYNintent = new Intent(MainActivity.this, DownFileService.class);
                            SYNintent.setPackage("vr.xinjing.com.vrmc.utils");
                            SYNintent.putExtra("countnumber", all.size());
                            SYNintent.putExtra("Aynctime", NowAyncTime);
                            if (all.get(i).getIssecret() != null && all.get(i).getIssecret()) {
                                SYNintent.putExtra("issecret", 1);
                            } else {
                                SYNintent.putExtra("issecret", 0);
                            }
                            SYNintent.putExtra("date", all.get(i).getDate());
                            SYNintent.putExtra("downloadUrl", all.get(i).getUrl());
                            SYNintent.putExtra("type", all.get(i).getType());
                            SYNintent.putExtra("vedioSize", all.get(i).getVodeosize());
                            SYNintent.putExtra("ContentId", all.get(i).getContentid());
                            startService(SYNintent);

                        }
                    }

                } else if (info == null) {
                    isNet = true;
                    MyLog.e("--------------mark", "没有可用网络");
                } else if (!info.isAvailable()) {
                    isNet = true;
                }
            }
        }
    };

    private String getLastAyncTime() {//获取上一次时间的时间戳
//        SpUtils.getInstance().saveLastAynvTime("2017-11-06 23:59:59");
        return SpUtils.getInstance().getLastAyncTime();
    }

    private String getNoewAyncTime() {
        //2016-10-20 09:38:45
        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(d);
        return str;
    }

    /**
     * 广播接收器
     *
     * @author user
     */
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (intent.getStringExtra("ischange").equals("yes")) {
//                startActivity(new Intent(MainActivity.this, AuthorizationActivity.class));
            } else {
                TaskInfo.DataBean dataBean = (TaskInfo.DataBean) extras.getSerializable("task");
                String tokens = extras.getString("token", null);
                if (tokens != null) {
                    token = tokens;
                }
                if (dataBean.getType() == 1) { //获取内容详情播放
//                    Map<String, String> priArgs = new HashMap<>();
//                    priArgs.put("contentId", dataBean.getContent());
//                    priArgs.put("token", MainActivity.this.token);
//                    MyLog.e("---mAin type =1", dataBean.getContent());
//                    queryPrescriptionPresenter.setMap(priArgs);
//                    queryPrescriptionPresenter.getPrescriptionlistcontent(true);
                    MyLog.e("---main", "ContentId : " + dataBean.getContent());
                    n = noteService.getNameById(dataBean.getContent() + "");
                    if (n == null || !n.getState()) {
                        MyToast.makeText(getApplicationContext(), "资源同步中", Toast.LENGTH_SHORT).show();
                    } else {
                        MyLog.e("---main", "noteService != null");
                        if (n.getIssecret() == null || !n.getIssecret()) {
                            MyLog.e("---main", "传过去播放");
                            Intent intents = new Intent(MainActivity.this, PlayerActivity.class);
                            intents.putExtra("url", n.getPath());
//                                encryptFile.EncryFile(n.getPath(), dataBean.getVoidpassword());
//                                MyLog.e("-------------path", n.getPath());
//                                n.setIssecret(false);
//                                noteService.updateData(n.getName(),n);
                            intents.putExtra("type", 1);
                            startActivity(intents);
                        } else {
                            MyLog.e("---main", "加密");
                            encryptFile.decryFile(n.getPath(), dataBean.getVoidpassword());
                        }
                    }
                }
//            else if(dataBean.getType() == 2){//停止播放
//                MyLog.e("---mAin","type =2");
//                EventBus.getDefault().post("dsa");
//            }
            }
        }
    }

}
