package vr.xinjing.com.vrmc.utils;

/**
 * Created by raytine on 2017/2/28.
 */

import android.util.Base64;


import net.grandcentrix.tray.core.ItemNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SpUtilsBase {
    protected MyModulePreference mAppPreferences;

    public SpUtilsBase() {
    }

    protected void putString(String key, String value) {
        mAppPreferences.put(key, value);
    }

    protected String getString(String key) {

       return   mAppPreferences.getString(key,"2016-10-20 09:38:45");

    }
    //不同包中不能访问
    protected void putBoolean(String key, boolean b) {
        mAppPreferences.put(key, b);
    }

    protected boolean getBoolean(String key) {
        boolean aBoolean = mAppPreferences.getBoolean(key, false);
        return  aBoolean;
    }

    protected void putInt(String key, int i) {
        mAppPreferences.put(key, i);
    }

    protected void getInt(String key) {
        mAppPreferences.getInt(key, 0);
    }

    protected void putFloat(String key, float f) {
        mAppPreferences.put(key, f);
    }

    protected void getFloat(String key) {
        mAppPreferences.getFloat(key, 0);
    }

    protected void putLong(String key, long l) {
        mAppPreferences.put(key, l);
    }

    protected void getLong(String key) {
        mAppPreferences.getLong(key, 0l);
    }

    protected void putModule(String key, Object o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            mAppPreferences.put(key, oAuth_Base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Object getModule(String key) {
        Object object = null;
        try {
            String string = mAppPreferences.getString(key);
            if (string == "" || string == null) {
                return null;
            }
            byte[] base64 = Base64.decode(string.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64);
            try {
                ObjectInputStream bis = new ObjectInputStream(bais);
                object = bis.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    protected void remove(String key) {
        mAppPreferences.remove(key);
    }


    /**------------以上是基本方法-------------**/
}
