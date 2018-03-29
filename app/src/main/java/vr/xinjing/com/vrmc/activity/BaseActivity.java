package vr.xinjing.com.vrmc.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vr.xinjing.com.vrmc.utils.ExitAppReceiver;
import vr.xinjing.com.vrmc.utils.NetworkStat;

/**
 * Created by raytine on 2017/2/14.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerExitReceiver();
    }
    private ExitAppReceiver exitReceiver = new ExitAppReceiver();
    // 自定义退出应用Action,实际应用中应该放到整个应用的Constant类中.
    private static final String EXIT_APP_ACTION = "com.micen.exit_app";

    private void registerExitReceiver() {

        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    private void unRegisterExitReceiver() {
        if (exitReceiver != null)
        unregisterReceiver(exitReceiver);
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unRegisterExitReceiver();
    }

    public void exitApp() {
        Intent intent = new Intent();
        intent.setAction(EXIT_APP_ACTION);
        sendBroadcast(intent);
    }
    public int checkNetWork() {
        switch (NetworkStat.getNetWorkType(this)){
            case NetworkStat.NETWORKTYPE_INVALID:
                return 0;
            case NetworkStat.NETWORKTYPE_2G:
            case NetworkStat.NETWORKTYPE_3G:
            case NetworkStat.NETWORKTYPE_WAP:
                return 1;
            case NetworkStat.NETWORKTYPE_WIFI:
                return 2;
            default:
                return 0;
        }
    }
}
