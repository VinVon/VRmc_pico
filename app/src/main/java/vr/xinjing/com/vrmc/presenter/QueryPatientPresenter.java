package vr.xinjing.com.vrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.xinjing.com.vrmc.bean.PatientInfo;
import vr.xinjing.com.vrmc.imp.QueryPayient;
import vr.xinjing.com.vrmc.model.QueryPatientModel;

/**
 * Created by raytine on 2017/2/11.
 */

public class QueryPatientPresenter {
//    private QueryPayient queryPayient;
//    private QueryPatientModel queryPatientModel;
//    private Handler handler = new Handler();
//    private Map<String,String> map;
//
//    public QueryPatientPresenter(QueryPayient queryPayient,Map<String,String> map) {
//        this.queryPayient = queryPayient;
//        queryPatientModel = new QueryPatientModel();
//        this.map = map;
//    }
//    public void  getPatient(){
//        queryPayient.showProgressDialog();
//        queryPatientModel.getPatient(map, new QueryPatientModel.OnLoginListener() {
//            @Override
//            public void loginSuccess(final PatientInfo user) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPayient.updateView(user);
////                        queryPayient.hideProgressDialog();
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
//                        queryPayient.tokenchange();
//                        queryPayient.hideProgressDialog();
//                    }
//                });
//            }
//
//            @Override
//            public void loginFailed(final PatientInfo user) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        queryPayient.showError(user.getMessage());
//                        queryPayient.change();
//                        queryPayient.hideProgressDialog();
//                    }
//                });
//
//            }
//        });
//
//    }
}
