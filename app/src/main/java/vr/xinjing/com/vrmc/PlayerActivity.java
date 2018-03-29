package vr.xinjing.com.vrmc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demo.hotrepair.entryptlibrary.EncryptClick;
import com.demo.hotrepair.entryptlibrary.EncryptFile;
import com.picovr.vractivity.Eye;
import com.picovr.vractivity.HmdState;
import com.picovr.vractivity.RenderInterface;
import com.picovr.vractivity.VRActivity;
import com.utovr.player.UVEventListener;
import com.utovr.player.UVInfoListener;
import com.utovr.player.UVMediaPlayer;
import com.utovr.player.UVMediaType;
import com.utovr.player.UVPlayerCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import me.itangqi.greendao.Note;
import vr.xinjing.com.vrmc.bean.TaskInfo;
import vr.xinjing.com.vrmc.utils.Logger;
import vr.xinjing.com.vrmc.utils.MyLog;
import vr.xinjing.com.vrmc.utils.MyToast;
import vr.xinjing.com.vrmc.utils.NoteService;


/**
 * Created by 123 on 2017/2/7.
 */

public class PlayerActivity extends VRActivity implements UVPlayerCallBack, VideoController.PlayerControl,RenderInterface, EncryptClick {

    private UVMediaPlayer mMediaplayer = null;  // 媒体视频播放器
    private VideoController mCtrl = null;
    //    private String Path = "http://cache.utovr.com/201508270528174780.m3u8";
    //   /storage/emulated/0/vrmc/video/2c90e40a5d7de393015db0395bcb0c88.mp4
    private String Path = null;
    private boolean bufferResume = true;

    private boolean needBufferAnim = true;
    private ImageView imgBuffer;                // 缓冲动画
    private ImageView imgBack;
    private RelativeLayout rlParent = null;
    protected int CurOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    private int SmallPlayH = 0;
    private boolean colseDualScreen = true;
    private List<String> mlist = new ArrayList<>();
    private String Paths;
    private boolean first = true;
    RelativeLayout  rlPlayView;
    private ListView testList;

    //
    private TaskInfo.DataBean dataBean;
    private MyReceiver receiver;
    private NoteService noteService;//数据库操作类
    private EncryptFile encryptFile;
    private Note n;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity);
        initView();

//        Intent i = getIntent();
//        Path = i.getStringExtra("url");
        Paths = Path +"demo.mp4";
        Path  =getIntent().getStringExtra("url");
        //初始化播放器
        rlPlayView = (RelativeLayout) findViewById(R.id.activity_rlPlayView);
        mMediaplayer = new UVMediaPlayer(PlayerActivity.this, rlPlayView);
        //将工具条的显示或隐藏交个SDK管理，也可自己管理
        RelativeLayout rlToolbar = (RelativeLayout) findViewById(R.id.activity_rlToolbar);
        mMediaplayer.setToolbar(rlToolbar, null, imgBack);
        mCtrl = new VideoController(rlToolbar, PlayerActivity.this, false);
        changeOrientation(true);
//        File file = new File(Path);
//        File[] files = file.listFiles();
//        for (int i = 0; i <files.length ; i++) {
//            mlist.add(files[i].getName());
//        }
//        testList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,mlist));
//        testList.setOnItemClickListener((parent,view,position,id) -> {
//                Paths = Path + mlist.get(position);
//                Log.e("---------",Paths);
//                mMediaplayer.reset();
//                mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Paths);
//        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMediaplayer != null) {
            mMediaplayer.onResume(PlayerActivity.this);
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().post("back");
        super.onPause();
        if (mMediaplayer != null) {
            mMediaplayer.onPause();
        }
    }

    @Override
    public void onDestroy() {
        releasePlayer();
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeOrientation(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    private void changeOrientation(boolean isLandscape) {
        if (rlParent == null) {
            return;
        }
        if (isLandscape) {
            CurOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            rlParent.setLayoutParams(lp);
            if (colseDualScreen && mMediaplayer != null) {
                mCtrl.setDualScreenEnabled(true);
            }
            colseDualScreen = false;
            mCtrl.changeOrientation(true, 0);

        } else {
            CurOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            getSmallPlayHeight();
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, SmallPlayH);
            rlParent.setLayoutParams(lp);
            if (mMediaplayer != null && mMediaplayer.isDualScreenEnabled()) {
                mCtrl.setDualScreenEnabled(false);
                colseDualScreen = true;
            }
            mCtrl.changeOrientation(false, 0);
        }
    }

    private void initView() {
//        testList = (ListView) findViewById(R.id.test_list);
        rlParent = (RelativeLayout) findViewById(R.id.activity_rlParent);
        imgBuffer = (ImageView) findViewById(R.id.activity_imgBuffer);
        imgBack = (ImageView) findViewById(R.id.activity_imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        receiver = new MyReceiver();
        noteService = ((Appaplication) getApplication()).noteService;//数据库操作类
        encryptFile = new EncryptFile(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVERS");
        //注册
        registerReceiver(receiver, filter);
    }

    public void releasePlayer() {
        if (mMediaplayer != null) {
            mMediaplayer.release();
            mMediaplayer = null;
        }
    }

    @Override
    public void createEnv() {
        try {
            // 创建媒体视频播放器
            mMediaplayer.initPlayer();
            mMediaplayer.setListener(mListener);
            mMediaplayer.setInfoListener(mInfoListener);
            //如果是网络MP4，可调用 mCtrl.startCachePro();mCtrl.stopCachePro();
//            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_M3U8, Path);
//            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Path);
            mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Path);
        } catch (Exception e) {
            Log.e("utovr", e.getMessage(), e);
        }
    }

    @Override
    public void updateProgress(long position) {
        if (mCtrl != null) {
            mCtrl.updateCurrentPosition();
        }
    }

    private UVEventListener mListener = new UVEventListener() {
        @Override
        public void onStateChanged(int playbackState) {
            Log.i("utovr", "+++++++ playbackState:" + playbackState);
            switch (playbackState) {
                case UVMediaPlayer.STATE_PREPARING:
                    Log.e("---------","1");
                    break;
                case UVMediaPlayer.STATE_BUFFERING:
                    Log.e("---------","2");
                    if (needBufferAnim && mMediaplayer != null && mMediaplayer.isPlaying()) {
                        bufferResume = true;
                        Utils.setBufferVisibility(imgBuffer, true);
                    }
                    break;
                case UVMediaPlayer.STATE_READY:
                    Log.e("---------","3");
                    // 设置时间和进度条
                    mCtrl.setInfo();
                    if (bufferResume) {
                        bufferResume = false;
                        Utils.setBufferVisibility(imgBuffer, false);
                    }
                    if (!mMediaplayer.isGyroEnabled()){
                    mMediaplayer.setGyroEnabled(true);}
                    if (!mMediaplayer.isDualScreenEnabled()){
                    mMediaplayer.setDualScreenEnabled(true);}
                    break;
                case UVMediaPlayer.STATE_ENDED:
                    Log.e("---------","4");
                    //这里是循环播放，可根据需求更改
                    mMediaplayer.replay();
                    break;
                case UVMediaPlayer.TRACK_DISABLED:
                    Log.e("---------","5");
                case UVMediaPlayer.TRACK_DEFAULT:
                    Log.e("---------","6");
                    break;
            }
        }

        @Override
        public void onError(Exception e, int ErrType) {
            Toast.makeText(PlayerActivity.this, Utils.getErrMsg(ErrType), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoSizeChanged(int width, int height) {
        }

    };

    private UVInfoListener mInfoListener = new UVInfoListener() {
        @Override
        public void onBandwidthSample(int elapsedMs, long bytes, long bitrateEstimate) {
        }

        @Override
        public void onLoadStarted() {
        }

        @Override
        public void onLoadCompleted() {
            if (bufferResume) {
                bufferResume = false;
                Utils.setBufferVisibility(imgBuffer, false);
            }
            if (mCtrl != null) {
                mCtrl.updateBufferProgress();
            }

        }
    };

    @Override
    public long getDuration() {
        return mMediaplayer != null ? mMediaplayer.getDuration() : 0;
    }

    @Override
    public long getBufferedPosition() {
        return mMediaplayer != null ? mMediaplayer.getBufferedPosition() : 0;
    }

    @Override
    public long getCurrentPosition() {
        return mMediaplayer != null ? mMediaplayer.getCurrentPosition() : 0;
    }

    @Override
    public void setGyroEnabled(boolean val) {
        if (mMediaplayer != null)
            mMediaplayer.setGyroEnabled(val);
    }

    @Override
    public boolean isGyroEnabled() {
        return mMediaplayer != null ? mMediaplayer.isGyroEnabled() : false;
    }

    @Override
    public boolean isDualScreenEnabled() {
        return mMediaplayer != null ? mMediaplayer.isDualScreenEnabled() : false;
    }

    @Override
    public void toolbarTouch(boolean start) {
        if (mMediaplayer != null) {
            if (true) {
                mMediaplayer.cancelHideToolbar();
            } else {
                mMediaplayer.hideToolbarLater();
            }
        }
    }

    @Override
    public void pause() {
        if (mMediaplayer != null && mMediaplayer.isPlaying()) {
            mMediaplayer.pause();
        }

    }

    @Override
    public void seekTo(long positionMs) {
        if (mMediaplayer != null)
            mMediaplayer.seekTo(positionMs);
    }

    @Override
    public void play() {
        if (mMediaplayer != null && !mMediaplayer.isPlaying()) {
            mMediaplayer.play();
        }
    }

    @Override
    public void setDualScreenEnabled(boolean val) {
        if (mMediaplayer != null)
            mMediaplayer.setDualScreenEnabled(val);
    }

    @Override
    public void toFullScreen() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    /* 大小屏切 可以再加上 ActivityInfo.SCREEN_ORIENTATION_SENSOR 效果更佳**/

    private void back() {
        if (CurOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            releasePlayer();
            finish();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void getSmallPlayHeight() {
        if (this.SmallPlayH != 0) {
            return;
        }
        int ScreenW = getWindowManager().getDefaultDisplay().getWidth();
        int ScreenH = getWindowManager().getDefaultDisplay().getHeight();
        if (ScreenW > ScreenH) {
            int temp = ScreenW;
            ScreenW = ScreenH;
            ScreenH = temp;
        }
        SmallPlayH = ScreenW * ScreenW / ScreenH;
    }

    @Override
    public void onFrameBegin(HmdState hmdState) {

    }

    @Override
    public void onDrawEye(Eye eye) {

    }

    @Override
    public void onFrameEnd() {

    }

    @Override
    public void onTouchEvent() {

    }

    @Override
    public void onRenderPause() {

    }

    @Override
    public void onRenderResume() {

    }

    @Override
    public void onRendererShutdown() {

    }

    @Override
    public void initGL(int i, int i1) {

    }

    @Override
    public void renderEventCallBack(int i) {

    }

    @Override
    public void surfaceChangedCallBack(int i, int i1) {

    }


        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {

            if (keyCode == 1001) {
                this.finish();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

    @Override
    public void encrySuccess() {
        n.setIssecret(true);
        noteService.updateData(n.getContentid(),n);
//
        finish();
        EventBus.getDefault().post("dsa");
    }

    @Override
    public void encryFailed() {

    }

    /**
     * 广播接收器
     * @author user
     *
     */
    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            dataBean = (TaskInfo.DataBean) extras.getSerializable("task");
            n = noteService.getNameById(dataBean.getContent()+"");
            MyLog.e("-------path",n.getPath()+"");
//            password =  dataBean.getVoidpassword();
            if (dataBean.getType() == 2){
                if (mMediaplayer.isPlaying()){
//                        mPlayerFragment.showDialog();
                    MyLog.e("++palyactivity",n.getId()+"加密成功");
                    if (dataBean.getJmvalues()!=0){
                        encryptFile.EncryFile(n.getPath(),dataBean.getJmvalues());
                    }else{
                        finish();
                        EventBus.getDefault().post("dsa");
                    }
                }
                else
                {
                    Logger.e("----palyactivity","播放类型");
                }
            }else{
                if (mMediaplayer.isPlaying()){
                    if (n == null || !n.getState()){
                        finish();
                        MyToast.makeText(getApplicationContext(),"资源同步中",Toast.LENGTH_SHORT).show();
                    }else{
                        mMediaplayer.reset();
                        mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, n.getPath());}
                }
            }
        }
    }
}
