package vr.xinjing.com.vrmc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.demo.hotrepair.entryptlibrary.EncryptClick;
import com.demo.hotrepair.entryptlibrary.EncryptFile;
import com.utovr.player.UVMediaPlayer;
import com.utovr.player.UVMediaType;

import de.greenrobot.event.EventBus;
import me.itangqi.greendao.Note;
import vr.xinjing.com.vrmc.bean.TaskInfo;
import vr.xinjing.com.vrmc.utils.Logger;
import vr.xinjing.com.vrmc.utils.MyLog;
import vr.xinjing.com.vrmc.utils.MyToast;
import vr.xinjing.com.vrmc.utils.NoteService;

/**
 * Created by raytine on 2017/2/11.
 */

public class PlayerFragmentAcivity extends FragmentActivity implements EncryptClick{
    private PowerManager.WakeLock mWakeLock = null;
    private PlayerFragment mPlayerFragment;
    private MusicFragment musicFragment;
    private String url;
    private int type;
    private TaskInfo.DataBean dataBean;
    private MyReceiver receiver;
    private NoteService noteService;//数据库操作类
    private EncryptFile encryptFile;
    private Note n;
//    private int password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_fragment_activity);
        setContentView(R.layout.player_fragment_activity);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "mytag");
        mWakeLock.acquire();
        receiver = new MyReceiver();
        noteService = ((Appaplication) getApplication()).noteService;//数据库操作类
        encryptFile = new EncryptFile(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MY_RECEIVERS");
        //注册
        registerReceiver(receiver, filter);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        type = i.getIntExtra("type",0);
        Bundle bundle2 = new Bundle();
        bundle2.putString("url", url);
        if (type == 1){
            mPlayerFragment = PlayerFragment.newInstance();
            mPlayerFragment.setArguments(bundle2);
            getSupportFragmentManager().beginTransaction().add(R.id.fragactivity_flPlayer, mPlayerFragment).commit();
        }else if (type == 2){
            musicFragment = MusicFragment.newInstance();
            musicFragment.setArguments(bundle2);
            getSupportFragmentManager().beginTransaction().add(R.id.fragactivity_flPlayer, musicFragment).commit();
        }

    }
    public  boolean VrVideoIsPlaying(){
        return mPlayerFragment.getmMediaplayer().isPlaying();
    }

    @Override
    protected void onPause() {
        Logger.e("++--activity","onPause");

        EventBus.getDefault().post("back");

        super.onPause();

    }

    @Override
    protected void onDestroy()
    {
        Logger.e("++---activity","dasda");
        super.onDestroy();
        mWakeLock.release();
        unregisterReceiver(receiver);
//        Intent mIntent = new Intent();
//        mIntent.putExtra("id",dataBean.getId());
//        Logger.e("----ondestroy",dataBean.getId());
//        // 设置结果，并进行传送
//        this.setResult(2, mIntent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Logger.e("-------path","KEYCODE_BACK");
            if (mPlayerFragment != null) {
                mPlayerFragment.releasePlayer();
                finish();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
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
                if (mPlayerFragment.getmMediaplayer().isPlaying()){
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
                UVMediaPlayer uvMediaPlayer = mPlayerFragment.getmMediaplayer();
                if (uvMediaPlayer.isPlaying()){
                    if (n == null || !n.getState()){
                        finish();
                        MyToast.makeText(getApplicationContext(),"资源同步中",Toast.LENGTH_SHORT).show();
                    }else{
                        uvMediaPlayer.reset();
                        uvMediaPlayer.setSource(UVMediaType.UVMEDIA_TYPE_MP4, n.getPath());}
                }
            }
        }
    }
}
