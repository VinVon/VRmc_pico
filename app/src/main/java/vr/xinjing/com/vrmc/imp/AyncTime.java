package vr.xinjing.com.vrmc.imp;

import vr.xinjing.com.vrmc.bean.LastAyncInfo;

/**
 * Created by raytine on 2017/3/3.
 */

public interface AyncTime {
    void showsuccess(LastAyncInfo s);
    void showfaild();
    void tokenchange();
}
