package vr.xinjing.com.vrmc.presenter;

import android.os.Handler;
import android.util.Log;

import java.util.Map;

import vr.xinjing.com.vrmc.bean.Prescription;
import vr.xinjing.com.vrmc.bean.PrescriptionContent;
import vr.xinjing.com.vrmc.bean.PrescriptionInfo;
import vr.xinjing.com.vrmc.imp.QueryPrescription;
import vr.xinjing.com.vrmc.model.QueryPrescriptionModel;

/**
 * Created by raytine on 2017/2/11.
 */

public class QueryPrescriptionPresenter {
    private Handler handler = new Handler();
    private QueryPrescription queryPrescription;
    private QueryPrescriptionModel queryPrescriptionModel;
    private Map<String,String> map;

    public QueryPrescriptionPresenter(QueryPrescription queryPrescription) {
        this.queryPrescription = queryPrescription;
        queryPrescriptionModel = new QueryPrescriptionModel();
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
    //获取处方
//    public void  getPrescription(){
//        queryPrescriptionModel.getPrescription(map, new QueryPrescriptionModel.OnLoginListener() {
//            @Override
//            public void loginSuccess(final Prescription user) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPrescription.updateView(user);
//                        queryPrescription.hideProgressDialog();
//                    }
//                });
//
//            }
//
//            @Override
//            public void tokenChange() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPrescription.tokenchange();
//                        queryPrescription.hideProgressDialog();
//                    }
//                });
//            }
//
//            @Override
//            public void loginFailed(final Prescription user) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPrescription.showError(user.getMessage());
//                        queryPrescription.hideProgressDialog();
//                    }
//                });
//            }
//        });
//
//    }
//    //获取内容列表
//    public void  getPrescriptionlist(){
//        queryPrescriptionModel.getPrescriptionlist(map, new QueryPrescriptionModel.OnLoginListenero() {
//            @Override
//            public void loginSuccess(final PrescriptionContent user) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPrescription.update(user);
//                    }
//                });
//            }
//
//            @Override
//            public void tokenChange() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPrescription.tokenchange();
//
//                    }
//                });
//            }
//
//            @Override
//            public void loginFailed(final PrescriptionContent user) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPrescription.showError(user.getMessage());
//                    }
//                });
//            }
//        });
//    }
    //获取内容详情
    public void  getPrescriptionlistcontent(boolean is){
        queryPrescriptionModel.getPrescriptionlistcontent(map, new QueryPrescriptionModel.OnLoginListenert() {
            @Override
            public void loginSuccess(final PrescriptionInfo user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        String s  = user.toString();
//                        Log.e("----------------user",user.getData().getExt().getVideosize()+"");
                        queryPrescription.update(user,is);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.tokenchange();

                    }
                });
            }

            @Override
            public void loginFailed(final PrescriptionInfo user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        queryPrescription.showError(user,is);
                    }
                });
            }
        });
    }
}
