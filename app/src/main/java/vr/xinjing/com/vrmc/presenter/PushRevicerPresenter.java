package vr.xinjing.com.vrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.imp.LoginView;
import vr.xinjing.com.vrmc.imp.PushReciverimp;
import vr.xinjing.com.vrmc.model.LoginModel;
import vr.xinjing.com.vrmc.model.PushReciverModel;

/**
 *
 * Created by raytine on 2018/3/29.
 */

public class PushRevicerPresenter {
    private PushReciverimp pushView;
    private PushReciverModel pushModel;
    private Map<String,String> map;
    private Handler mHandler = new Handler();
    public PushRevicerPresenter(PushReciverimp pushView,Map<String,String> map) {
        this.pushView = pushView;
        pushModel = new PushReciverModel();
        this.map = map;
    }
    public void Login(){
        pushView.showProgressDialog();
            pushModel.pushRevicer(map, new PushReciverModel.OnPushListener() {
            @Override
            public void pushSuccess(final String user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pushView.updateView(user);
                        pushView.hideProgressDialog();
                    }
                });
            }



            @Override
            public void pushFailed(final String user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pushView.showError(user);
                        pushView.hideProgressDialog();
                    }
                });
            }
        });


    }
}
