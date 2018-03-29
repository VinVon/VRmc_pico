package vr.xinjing.com.vrmc.utils;

/**
 * Created by raytine on 2017/2/28.
 */
import android.content.Context;
import android.util.Log;


import vr.xinjing.com.vrmc.bean.LocalInfo;
import vr.xinjing.com.vrmc.bean.LoginInfo;

public class SpUtils extends SpUtilsBase {
    private static SpUtils instance;

    public SpUtils() {
    }

    public static SpUtils getInstance() {
        if (instance == null) {
            instance = new SpUtils();
            return instance;
        } else {
            return instance;
        }
    }

    public void init(Context context) {
        if (mAppPreferences == null) {
            mAppPreferences = new MyModulePreference(context, context.getPackageName());
        }
    }

    public LocalInfo getUser() {
        Object module = getModule(SpDictionary.SP_USER);
        if (module != null) {
            return (LocalInfo) module;
        } else {
            Logger.e("-----------","用户信息为空");
            return null;
        }
    }
    public boolean getFirstLogin(){
        boolean aBoolean = getBoolean(SpDictionary.TEXT);
        return  aBoolean;
    }
    public String getLastAyncTime(){
        String string = getString(SpDictionary.SYNC_TIME);
        return string;
    }
    public void saveLastAynvTime(String value){
        putString(SpDictionary.SYNC_TIME,value);
    }
    public void saveUser(LocalInfo user) {
        putModule(SpDictionary.SP_USER, user);
    }

    public void saveFirstLogin(boolean is){putBoolean(SpDictionary.TEXT,is);}

    public String getToken() {
        LocalInfo user = getUser();
        if (user != null) {
            return user.getToken();
        }
        return "";
    }
}
