package vr.xinjing.com.vrmc;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.lidroid.xutils.http.HttpHandler;
import com.utovr.player.UVEventListener;
import com.utovr.player.UVInfoListener;
import com.utovr.player.UVMediaPlayer;
import com.utovr.player.UVMediaType;
import com.utovr.player.UVNetworkListenser;
import com.utovr.player.UVPlayerCallBack;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import cn.pedant.SweetAlert.SweetAlertDialog;

import vr.xinjing.com.vrmc.bean.VideoInfo;
import vr.xinjing.com.vrmc.imp.DownFile;

import vr.xinjing.com.vrmc.utils.DownFileService;
import vr.xinjing.com.vrmc.utils.Logger;
import vr.xinjing.com.vrmc.utils.ToastCommom;

/**
 * Created by raytine on 2017/2/11.
 */

public class PlayerFragment extends Fragment implements VideoController.PlayerControl,DownFile {
    private UVMediaPlayer mMediaplayer = null;  // 媒体视频播放器
    private VideoController mCtrl = null;
    private String Path;
    private boolean bufferResume = true;
    private boolean needBufferAnim = true;
    private ImageView imgBuffer;                // 缓冲动画
    private ImageView imgBack;
    private ToggleButton tb;
    private AlertDialog builder;
//    private DownFilePresenter downFilePresenter;
    private  String names;
//    private boolean iscompele = false;//视频是否完整下载完毕
//    private BaseDao baseDao;
//    private File file;
//    private String filePath;
    public static PlayerFragment newInstance() {
        PlayerFragment f = new PlayerFragment();
        return f;
    }

    public UVMediaPlayer getmMediaplayer() {
        return mMediaplayer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.player_fragment, container, false);
        initView(v);
        Bundle bundle1 = getArguments();
        Path = bundle1.getString("url");

//        Logger.e("--------------",Path);
//       try {
//           String name = Path.substring(0,Path.indexOf(".mp4"));
//           names = name.substring(name.lastIndexOf("/")+1);
//       }catch (Exception e){
//           ToastCommom.createInstance().ToastShow(getActivity(),"资源不足");
//           getActivity().finish();
//       }
//        file = new File(Environment.getExternalStorageDirectory()+"/vrmc/video/"+names+".mp4");
//        baseDao = new BaseDao();
//        if (baseDao.isDogExist(names)){
//            //数据库存在记录，获取存储地址
//            VideoInfo videoInfo = baseDao.queryDobByAge(names);
//            filePath = videoInfo.getPath();
//        }
//        else{
//            List<VideoInfo> videoInfos = baseDao.queryAllDog();
//            int a = videoInfos.size()+1;
//            Logger.e("-------------村的文件数",a+"个");
//            VideoInfo ci =new VideoInfo();
//            ci.setId(a);
//            ci.setName(names);
//            ci.setState(false);
//            ci.setPath(file.getAbsolutePath());
//            baseDao.addDog(ci);
//        }
//        VideoInfo videoInfo = baseDao.queryDobByAge(names);
//        iscompele = videoInfo.isState();
//
//        Logger.e("-----------kankan1",iscompele+"");
//        if (iscompele){
//            if (!file.exists()){
//                iscompele = false;
//                baseDao.updateDog(names,false);
//                Logger.e("-----------kankan2",baseDao.queryDobByAge(names).isState()+"");
//            }
//        }
//        getLenght(Path); //获取网络文件大小
//        if (iscompele) {
//            Logger.e("--------------pfragment","不下载");
//        }else {
//            Logger.e("--------------pfragment","下载");
////            downFilePresenter = new DownFilePresenter(getActivity(),names+".mp4",Path,this);
////            downFilePresenter.loadfile();
//            Intent intent = new Intent(getActivity(),DownFileService.class);
//            intent.putExtra("downloadUrl",Path);
////            intent.putExtra("path",file.getAbsolutePath());
////            intent.putExtra("FileName",names);
//            intent.putExtra("type",1);
//            getActivity().startService(intent);
//        }
        //初始化播放器
        RelativeLayout rlPlayView = (RelativeLayout) v.findViewById(R.id.fragment_rlPlayView);
        mMediaplayer = new UVMediaPlayer(getActivity(), rlPlayView, mUVPlayerCallBack);
        //将工具条的显示或隐藏交个SDK管理，也可自己管理
        RelativeLayout rlToolbar = (RelativeLayout) v.findViewById(R.id.fragment_rlToolbar);
        mMediaplayer.setToolbar(rlToolbar, null, imgBack);
        mCtrl = new VideoController(rlToolbar, this, false);
        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mMediaplayer != null)
        {
            mMediaplayer.onResume(getActivity());
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mMediaplayer != null)
        {
            mMediaplayer.onPause();
        }
    }

    @Override
    public void onDestroy()
    {

        releasePlayer();
        super.onDestroy();
//        baseDao.close();
    }

    private void initView(View view)
    {
        imgBuffer = (ImageView) view.findViewById(R.id.fragment_imgBuffer);
        imgBack = (ImageView) view.findViewById(R.id.fragment_imgBack);
        tb = (ToggleButton) view.findViewById(R.id.video_tool_tbtnPlayPause);

        imgBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                releasePlayer();
                getActivity().finish();
            }
        });
    }

    public void releasePlayer()
    {
        if (mMediaplayer != null)
        {
            mMediaplayer.release();
            mMediaplayer = null;
        }
    }

    private UVPlayerCallBack mUVPlayerCallBack = new UVPlayerCallBack(){
        @Override
        public void createEnv()
        {
            try
            {
                // 创建媒体视频播放器
                mMediaplayer.initPlayer();
                mMediaplayer.setListener(mListener);
                mMediaplayer.setInfoListener(mInfoListener);
//                mMediaplayer.setNetWorkListenser(netListener);
                //如果是网络MP4，可调用 mCtrl.startCachePro();mCtrl.stopCachePro();
//                File f = new File(Environment.getExternalStorageDirectory()+"/vrmc/video/"+names+".mp4");
//                VideoInfo videoInfo = baseDao.queryDobByAge(names);
//                if (videoInfo.isState()) {
//                    Logger.e("--------","本地bof");
                    mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Path);
//                }
//                else {
//                    Logger.e("--------","网络bof");
//                    mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, Path);
//                }
//                mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_M3U8, Path);
                //mMediaplayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, "/sdcard/wu.mp4");
            }
            catch (Exception e)
            {
                Logger.e("utovr", e.getMessage(), e);
            }

        }

        @Override
        public void updateProgress(long position)
        {
            if (mCtrl != null) {
                mCtrl.updateCurrentPosition();
            }
        }

    };
    //监听播放器网络变化
//    private UVNetworkListenser netListener = new UVNetworkListenser(){
//
//        @Override
//        public void onNetworkChanged(int i) {
//            if (i == UVNetworkListenser.NETWORK_NONE){
//                ToastCommom.createInstance().ToastShow(getActivity(),"请检查网络连接情况");
//            }else if (i == UVNetworkListenser.NETWORK_MOBILE_2G || i == UVNetworkListenser.NETWORK_MOBILE_3G || i == UVNetworkListenser.NETWORK_MOBILE_4G){
//
//            }else if( i == UVNetworkListenser.NETWORK_WIFI){
////                long currentPosition = mMediaplayer.getCurrentPosition();
////                mMediaplayer.seekTo(currentPosition);
////                mMediaplayer.play();
//                mMediaplayer.retry();
//                Intent intent = new Intent(getActivity(),DownFileService.class);
//                intent.putExtra("downloadUrl",Path);
////            intent.putExtra("path",file.getAbsolutePath());
////            intent.putExtra("FileName",names);
//                intent.putExtra("type",1);
//                getActivity().startService(intent);
//            }
//        }
//    };
    private UVEventListener mListener = new UVEventListener()
    {
        @Override
        public void onStateChanged(int playbackState)
        {
            Logger.i("utovr", "+++++++ playbackState:" + playbackState);
            switch (playbackState)
            {
                case UVMediaPlayer.STATE_PREPARING: //2

                    break;
                case UVMediaPlayer.STATE_BUFFERING: //3

                    if (needBufferAnim && mMediaplayer != null && mMediaplayer.isPlaying()) {
                        bufferResume = true;
                        Utils.setBufferVisibility(imgBuffer, true);
                    }
                    break;
                case UVMediaPlayer.STATE_READY: //4

                    // 设置时间和进度条
                    mCtrl.setInfo();
                    mCtrl.startCachePro();
                    mCtrl.setDualScreenEnabled(true);
                    mCtrl.settbtnGyroEnabled(true);
                    if (bufferResume)
                    {
                        bufferResume = false;

                        Utils.setBufferVisibility(imgBuffer, false);

                    }
                    break;
                case UVMediaPlayer.STATE_ENDED: //5

                    //这里是循环播放，可根据需求更改
//                    mMediaplayer.replay();
//                    mMediaplayer.stop();
//                    tb.setChecked(true);
                    ToastCommom.createInstance().ToastShow(getActivity(),"播放结束");
                    getActivity().finish();
//                    showDialog();
                    break;
                case UVMediaPlayer.TRACK_DISABLED:

                case UVMediaPlayer.TRACK_DEFAULT:

                    break;
            }
        }

        @Override
        public void onError(Exception e, int ErrType)
        {
            Toast.makeText(getActivity(), Utils.getErrMsg(ErrType), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVideoSizeChanged(int width, int height)
        {
        }

    };

    public void showDialog() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("欣赏结束，请点击")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        getActivity().finish();
                    }
                })
                .show();
    }

    private UVInfoListener mInfoListener = new UVInfoListener()
    {
        @Override
        public void onBandwidthSample(int elapsedMs, long bytes, long bitrateEstimate)
        {
        }

        @Override
        public void onLoadStarted()
        {
        }

        @Override
        public void onLoadCompleted()
        {

            if (bufferResume)
            {
                bufferResume = false;
                Utils.setBufferVisibility(imgBuffer, false);
            }
            if (mCtrl != null) {
                mCtrl.updateBufferProgress();
            }

        }
    };

    @Override
    public long getDuration()
    {
        return mMediaplayer != null ? mMediaplayer.getDuration() : 0;
    }

    @Override
    public long getBufferedPosition()
    {
        return mMediaplayer != null ? mMediaplayer.getBufferedPosition() : 0;
    }

    @Override
    public long getCurrentPosition()
    {
        return mMediaplayer != null ? mMediaplayer.getCurrentPosition() : 0;
    }

    @Override
    public void setGyroEnabled(boolean val)
    {
        if (mMediaplayer != null)
            mMediaplayer.setGyroEnabled(val);
    }

    @Override
    public boolean isGyroEnabled()
    {
        return mMediaplayer != null ? mMediaplayer.isGyroEnabled() : false;
    }

    @Override
    public boolean isDualScreenEnabled()
    {
        return mMediaplayer != null ? mMediaplayer.isDualScreenEnabled() : false;
    }

    @Override
    public void toolbarTouch(boolean start)
    {
        if (mMediaplayer != null)
        {
            if (true)
            {
                mMediaplayer.cancelHideToolbar();
            }
            else
            {
                mMediaplayer.hideToolbarLater();
            }
        }
    }

    @Override
    public void pause()
    {
        if (mMediaplayer != null && mMediaplayer.isPlaying())
        {
            mMediaplayer.pause();
        }
    }

    @Override
    public void seekTo(long positionMs)
    {
        if (mMediaplayer != null)
            mMediaplayer.seekTo(positionMs);
    }

    @Override
    public void play()
    {
        if (mMediaplayer != null && !mMediaplayer.isPlaying())
        {
            mMediaplayer.play();
        }
    }

    @Override
    public void setDualScreenEnabled(boolean val)
    {
        if (mMediaplayer != null)
            mMediaplayer.setDualScreenEnabled(val);
    }

    @Override
    public void toFullScreen()
    {
    }

    @Override
    public void showsuccess() {
        ToastCommom.createInstance().ToastShow(getActivity().getApplicationContext(),"下载完成");
    }

    @Override
    public void showfaild() {
        ToastCommom.createInstance().ToastShow(getActivity().getApplicationContext(),"下载失败");
    }
//    public void getLenght(String url){
//    new Thread(new Runnable() {
//        @Override
//        public void run() {
//            URL u = null;
//            HttpURLConnection urlcon = null;
//            try {
//                u = new URL(url);
//                urlcon = (HttpURLConnection) u.openConnection();
//                urlcon.setRequestProperty("Accept-Encoding", "identity");
//                urlcon.connect();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            int contentLength = urlcon.getContentLength();
//            Message msg = handler.obtainMessage();
//            msg.what =1;
//            msg.arg1 = contentLength;
//            handler.sendMessage(msg);
//        }
//     }).start();
//    }
//    private int lenght = 0;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                   int lenght = msg.arg1;
//                    Logger.e("--------------文件大小",file.length()+"-----------"+lenght);
//                    break;
//            }
//        }
//    };
}
