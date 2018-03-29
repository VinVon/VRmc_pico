package vr.xinjing.com.vrmc.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.app.Service;
/**
 * Created by raytine on 2017/2/14.
 */

public class ExitAppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {

            if (context instanceof Activity) {
                Logger.i("exit", ((Activity) context).getLocalClassName());
                ((Activity) context).finish();
            } else if (context instanceof FragmentActivity) {
                Logger.i("exit", ((Activity) context).getLocalClassName());
                ((FragmentActivity) context).finish();
            } else if (context instanceof Service) {
                Logger.i("exit", ((Activity) context).getLocalClassName());
                ((Service) context).stopSelf();
            }
        }
    }
}
