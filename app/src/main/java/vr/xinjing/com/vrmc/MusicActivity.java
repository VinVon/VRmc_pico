package vr.xinjing.com.vrmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


import butterknife.BindView;
import butterknife.ButterKnife;
import vr.xinjing.com.vrmc.activity.BaseActivity;
import vr.xinjing.com.vrmc.utils.Player;

/**
 * Created by raytine on 2017/2/14.
 */

public class MusicActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.btn_online_play)
    Button btnOnlinePlay;
    @BindView(R.id.music_progress)
    SeekBar musicProgress;
    private Player player;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        btnOnlinePlay.setOnClickListener(this);
        player = new Player(musicProgress);
        musicProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());
    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                player.playUrl(url);
            }
        }).start();

    }
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player = null;
        }
    }
}
