package vr.xinjing.com.vrmc.imp;

import vr.xinjing.com.vrmc.bean.LoginInfo;

/**
 * Created by raytine on 2018/3/29.
 */

public interface PushReciverimp {
    void updateView(String user);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String msg);
}
