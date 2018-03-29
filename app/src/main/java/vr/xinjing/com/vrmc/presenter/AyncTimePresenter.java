package vr.xinjing.com.vrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.xinjing.com.vrmc.bean.LastAyncInfo;
import vr.xinjing.com.vrmc.imp.AyncTime;
import vr.xinjing.com.vrmc.model.AyncTimeModel;

/**
 * Created by raytine on 2017/3/3.
 */

public class AyncTimePresenter {
    private AyncTime ayncTime;
    private AyncTimeModel ayncTimeModel;
    private Handler handler = new Handler();
    private Map<String,Object> map;

    public AyncTimePresenter(AyncTime ayncTime) {

        this.ayncTime = ayncTime;
        ayncTimeModel = new AyncTimeModel();
    }
    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
    public  void  getAync(){
        ayncTimeModel.getAync(map, new AyncTimeModel.OnLoginListener() {
            @Override
            public void loginSuccess(LastAyncInfo user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ayncTime.showsuccess(user);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ayncTime.tokenchange();

                    }
                });
            }

            @Override
            public void loginFailed(LastAyncInfo user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ayncTime.showfaild();
                    }
                });
            }
        });
    }
}

