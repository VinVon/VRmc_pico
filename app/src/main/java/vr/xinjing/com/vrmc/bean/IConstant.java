package vr.xinjing.com.vrmc.bean;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import vr.xinjing.com.vrmc.utils.SdCardUtil;

/**
 * Created by raytine on 2017/2/14.
 */

public class IConstant {
    private static String device_model ;
    private static String version_release ;
    private static String version ;
    private static String device_id ;
    //文件保存路径
    public static String STROAGE_PATH = Environment.getExternalStorageDirectory()+"/vrmc/video/";

    public static String getModel(){
        Build b = new Build();
        return  b.MODEL;
    }

    public static String getStroagePath(Context context) {
        return SdCardUtil.getSdPath(context)+"/vrmc/video/";
    }

    public static String getVersionRelease(){
        Build b = new Build();
         return Build.VERSION.RELEASE;
    }
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context content) {
        String version = null;
        try {
            PackageManager manager = content.getPackageManager();
            PackageInfo info = manager.getPackageInfo(content.getPackageName(), 0);
            version = info.versionName;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return  version;
    }
    public static String devoceId(Context content){

        TelephonyManager tm = (TelephonyManager) content.getSystemService(content.TELEPHONY_SERVICE);
        return tm.getDeviceId();

    }
}
