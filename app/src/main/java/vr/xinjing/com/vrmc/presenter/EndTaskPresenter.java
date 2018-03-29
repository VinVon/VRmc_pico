package vr.xinjing.com.vrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.xinjing.com.vrmc.imp.EndTaskimp;
import vr.xinjing.com.vrmc.model.EndTaskModel;

/**
 * Created by raytine on 2017/4/12.
 */

public class EndTaskPresenter {
    private EndTaskimp endTaskimp;
    private EndTaskModel endTaskModel;
    private Handler handler =new Handler();

    public EndTaskPresenter(EndTaskimp endTaskimp) {
        this.endTaskimp = endTaskimp;
        endTaskModel = new EndTaskModel();
    }
    public  void endTask(Map<String,String> map){
        endTaskModel.Endtask(map, new EndTaskModel.EndTaskListener() {
            @Override
            public void endTaskSuccess(String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        endTaskimp.endSuccess(msg);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        endTaskimp.tokenchange();
                    }
                });
            }

            @Override
            public void endTaskFailed(String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        endTaskimp.endFailed(msg);
                    }
                });
            }
        });
    }
}
