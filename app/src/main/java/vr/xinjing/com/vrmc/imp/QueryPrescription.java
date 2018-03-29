package vr.xinjing.com.vrmc.imp;

import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.bean.Prescription;
import vr.xinjing.com.vrmc.bean.PrescriptionContent;
import vr.xinjing.com.vrmc.bean.PrescriptionInfo;

/**
 * Created by raytine on 2017/2/11.
 */

public interface QueryPrescription {
    //处方列表
//    void updateView(Prescription user);
    //内容列表
    void update(PrescriptionContent p);
    //内容详情
    void update(PrescriptionInfo p,boolean is);
//    void showProgressDialog();

//    void hideProgressDialog();
    void tokenchange();
//    void showError(String msg);
    void showError(PrescriptionInfo msg,boolean is);
}
