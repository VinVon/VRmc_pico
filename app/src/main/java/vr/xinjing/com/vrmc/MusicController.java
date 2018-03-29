package vr.xinjing.com.vrmc;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by raytine on 2017/2/14.
 */

public class MusicController implements View.OnClickListener{
    private SeekBar skTime;                    // 播放进度条
    private TextView tvTime;                   // 时间长度
    private ToggleButton tbtnPlayPause;        // 启动、暂停按钮
    private RelativeLayout rlPlayPro;          // 播放进度
    private ImageView imgFullscreen;           // 全屏按钮
    private String videoTimeString = null;     // 时间长度文本
    private MusicControl player;

    private Handler CacheProHandler = null;    //缓冲进度条
    private Timer mTimer = new Timer();
    private boolean changeOrientation;
    public MusicController(RelativeLayout toolbar, MusicControl player, boolean changeOrientation)
    {
        this.player = player;
        this.changeOrientation = changeOrientation;
        skTime = (SeekBar) toolbar.findViewById(R.id.music_tool_Seekbar);                  // 进度
        tvTime = (TextView) toolbar.findViewById(R.id.music_tool_tvTime);                  // 时间
        imgFullscreen = (ImageView) toolbar.findViewById(R.id.music_tool_imgFullscreen); // 点击全屏
        tbtnPlayPause = (ToggleButton) toolbar.findViewById(R.id.music_tool_tbtnPlayPause);// 播放/暂停
        rlPlayPro = (RelativeLayout) toolbar.findViewById(R.id.music_tool_rlPlayProg);
        if (changeOrientation)
            imgFullscreen.setOnClickListener(this);
        skTime.setOnSeekBarChangeListener(mSeekBarChange);
        tbtnPlayPause.setOnClickListener(this);
        mTimer.schedule(timerTask, 0, 1000);

    }

    public TimerTask getTimerTask() {
        return timerTask;
    }


    //进度条随着屏幕的变化监听的事件
    private SeekBar.OnSeekBarChangeListener mSeekBarChange = new SeekBar.OnSeekBarChangeListener()
    {
        int progress ;
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            player.seekTo(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            this.progress = progress * player.getDuration()
                    / seekBar.getMax();
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId())
            {
                case R.id.music_tool_imgFullscreen:// 是否全屏播放
                    if (player != null)
                    {
                        player.toFullScreen();
                    }
                    break;
                case R.id.music_tool_tbtnPlayPause:// 播放/暂停
                    if (((ToggleButton) v).isChecked()) {
                        player.pause();
                    } else {
                        player.play();
                        // 每一秒触发一
                    }
                    break;
        }
    }
    // 计时器
    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            updateCurrentPosition();
        }
    };
    public void updateCurrentPosition()
    {
        // 发出更新进度条的message
        handleProgress.sendEmptyMessage(0);

    }
    void changeOrientation(boolean isLandscape, int NavigationH)
    {
        if (!changeOrientation)
        {
            return;
        }
        if (isLandscape)
        {
            imgFullscreen.setVisibility(View.GONE);
            RelativeLayout.LayoutParams playParams = (RelativeLayout.LayoutParams) rlPlayPro.getLayoutParams();
            playParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_tbtnGyro);

            rlPlayPro.setLayoutParams(playParams);
            RelativeLayout.LayoutParams timeParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            timeParams.leftMargin = (int) rlPlayPro.getContext().getResources().getDimension(R.dimen.voide_tool_middle);
            tvTime.setLayoutParams(timeParams);

            RelativeLayout.LayoutParams seekParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            seekParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_tvTime);
            seekParams.addRule(RelativeLayout.CENTER_VERTICAL);
            skTime.setLayoutParams(seekParams);
        }
        else
        {
            imgFullscreen.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams playParams = (RelativeLayout.LayoutParams) rlPlayPro.getLayoutParams();
            playParams.addRule(RelativeLayout.LEFT_OF, R.id.video_tool_imgFullscreen);
            rlPlayPro.setLayoutParams(playParams);

            RelativeLayout.LayoutParams seekParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            skTime.setLayoutParams(seekParams);

            RelativeLayout.LayoutParams timeParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            timeParams.addRule(RelativeLayout.BELOW, R.id.video_tool_Seekbar);
            timeParams.leftMargin = (int) tvTime.getContext().getResources().getDimension(R.dimen.little_spacing);
            tvTime.setLayoutParams(timeParams);

        }
    }
    /**
     * 设置时间和进度条初始信息
     */
    public void setInfo() {
        int duration = 0;
        if (player != null)
        {
            duration = (int)player.getDuration();
        }
        if (duration == skTime.getMax())
        {
            return;
        }
        // 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
        skTime.setProgress(0);
//        skTime.setMax(duration);
        // 设置播放时间
        videoTimeString = Utils.getShowTime(duration);
        tvTime.setText("00:00:00/" + videoTimeString);
    }
    /*******************************************************
     * 通过Handler来更新进度条
     ******************************************************/
    private Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0://更新进度条

                    int position = player.getCurrentPosition();
                    int duration = player.getDuration();

                    if (duration > 0) {
                        // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                        long pos = skTime.getMax() * position / duration;
                        skTime.setProgress((int) pos);
                        // 设置播放时间
                        String cur = Utils.getShowTime(position);
                        tvTime.setText(cur + "/" + videoTimeString);
                    }
                    break;
            }

        };
    };


    interface MusicControl
    {
        void seekTo(int positionMs);
        void play();
        void pause();
        int getDuration();
        int getCurrentPosition();
        void toFullScreen();
    }
}
