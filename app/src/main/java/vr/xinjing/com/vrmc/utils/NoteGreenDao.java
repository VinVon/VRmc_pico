package vr.xinjing.com.vrmc.utils;

/**
 * Created by raytine on 2017/3/4.
 */

import android.content.Context;

import me.itangqi.greendao.DaoMaster;
import me.itangqi.greendao.DaoSession;

/**
 * Created by raytine on 2017/2/27.
 */

public class NoteGreenDao {
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static DaoMaster getDaoMaster(Context context){
        if (daoMaster == null){
            DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(context,"note-db",null);
            daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        }
        return  daoMaster;
    }
    public static DaoSession getDaoSession(Context context){
        if (daoSession == null){
            if (daoMaster == null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
