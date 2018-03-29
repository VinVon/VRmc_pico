package vr.xinjing.com.vrmc;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import vr.xinjing.com.vrmc.utils.DownFileService;
import vr.xinjing.com.vrmc.utils.Logger;
import vr.xinjing.com.vrmc.utils.ToastCommom;

/**
 * Created by raytine on 2017/2/14.
 */

public class MusicFragment extends Fragment implements MusicController.MusicControl, MediaPlayer.OnCompletionListener{
    @BindView(R.id.cd)
    ImageView cd;
    @BindView(R.id.point)
    ImageView point;
    private MusicController mCtrl = null;
    private String url;
    private MediaPlayer mediaPlayer;
    private ImageView imgBack;
    private ToggleButton tb;
    private String names;
    private File file;//视频文件
    private SeekBar seekBar;
    private AlertDialog.Builder dialog;
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private int bufferpercent;//mediaplayer在prepare时缓冲的百分比
    private boolean isNet = true;//是否是网络播放
    public static MusicFragment newInstance() {
        MusicFragment f = new MusicFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle1 = getArguments();
        url = bundle1.getString("url");
        file = new File(url);
//        isNet = file.exists();
//        IntentFilter mFilter = new IntentFilter();
//        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        getActivity().registerReceiver(mReceiver, mFilter);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.videoplay_fragment, container, false);
        initView(v);
        plays();
        RelativeLayout rlToolbar = (RelativeLayout) v.findViewById(R.id.videofragment_rlToolbar);
        mCtrl = new MusicController(rlToolbar, this, false);
        mCtrl.setInfo();
        ButterKnife.bind(this, v);
        return v;
    }

    private void plays() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.reset();
//            if (isNet){
            Logger.e("--------------音乐","本地播放");
                mediaPlayer.setDataSource(file.getAbsolutePath());
//            }else{
//                Logger.e("--------------音乐","网络播放");
//                mediaPlayer.setDataSource(url);
//                Intent intent = new Intent(getActivity(),DownFileService.class);
//                intent.putExtra("downloadUrl",url);
////                intent.putExtra("path",file.getAbsolutePath());
//                intent.putExtra("type",2);
//                getActivity().startService(intent);
//            } // 设置数据源
            mediaPlayer.prepare(); // prepare自动播放
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

                bufferpercent = percent;

            }
        });
    }

    private void initView(View view) {
         seekBar = (SeekBar) view.findViewById(R.id.music_tool_Seekbar);
        imgBack = (ImageView) view.findViewById(R.id.videofragment_imgBack);
        tb = (ToggleButton) view.findViewById(R.id.music_tool_tbtnPlayPause);
        tb.setChecked(true);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();
                getActivity().finish();
            }
        });
    }

    public void releasePlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
        TimerTask timerTask = mCtrl.getTimerTask();
        timerTask.cancel();
        super.onDestroy();
    }

    @Override
    public void seekTo(int positionMs) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(positionMs);
        }
    }

    @Override
    public void play() {

//
//        if (!isNet && !isIntner){//网络播放，且没有网络时
//            tb.setChecked(true);
//            ToastCommom.createInstance().ToastShow(getActivity(),"没有网络！");
//        }else{
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                pointdown(point);
                cdstart();
            }
//        }
    }

    @Override
    public void pause() {

          if (mediaPlayer != null && mediaPlayer.isPlaying()) {
              mediaPlayer.pause();
              pointup(point);
              cd.clearAnimation();
          }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TimerTask timerTask = mCtrl.getTimerTask();
        if (timerTask != null)
            timerTask.cancel();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
//        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public int getDuration() {
        return mediaPlayer != null ? mediaPlayer.getDuration() : 0;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
    }

    @Override
    public void toFullScreen() {

    }

    private void showDialog() {

        new AlertDialog.Builder(getActivity())
                .setMessage("欣赏结束，请点击")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                })
                .setCancelable(false)
                .show();


    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        tb.setChecked(true);
        mediaPlayer.seekTo(0);
        TimerTask timerTask = mCtrl.getTimerTask();
        timerTask.cancel();
        pointup(point);
        cd.clearAnimation();
        showDialog();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }
    //指针下拉动画
    public void pointdown(ImageView t){
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.point_down);
        t.startAnimation(animation);
    }
    public void pointup(ImageView t){
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.point_up);
        t.startAnimation(animation);
    }
    public void cdstart(){
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.cb_run);
//        animation.setDuration(mediaPlayer.getDuration());
        cd.startAnimation(animation);

    }
//    private boolean iscount=false;//判断是否播放断网重连
//    private boolean isIntner = true;
//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                Logger.d("mark", "网络状态已经改变");
//                connectivityManager = (ConnectivityManager)
//                        getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                info = connectivityManager.getActiveNetworkInfo();
//
//                    if (!isNet){
//                        if(info != null && info.isAvailable())
//                        {//有网时
//                            isIntner = true;
//                            if (iscount){
//                                try {
//
//                                    mediaPlayer.prepare();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        } else
//                        {//没有网络时。判断缓冲，按钮状态
//                            mediaPlayer.stop();
//                            iscount = true;
//                            isIntner = false;
//                            tb.setChecked(true);
//
//                        }
//                    }
//            }
//        }
//    };
}
