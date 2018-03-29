package vr.xinjing.com.vrmc.presenter;

import android.os.Handler;

import java.util.Map;

import vr.xinjing.com.vrmc.bean.TaskInfo;
import vr.xinjing.com.vrmc.imp.TaskListimp;
import vr.xinjing.com.vrmc.model.TasklistModel;

/**
 * Created by raytine on 2017/4/10.
 */

public class TasklistPresenter {
    private TaskListimp taskListimp;
    private TasklistModel getTaskModel;
    private Handler handler = new Handler();

    public TasklistPresenter(TaskListimp taskListimp) {
        this.taskListimp = taskListimp;
        getTaskModel = new TasklistModel();
    }

    public TasklistPresenter() {
    }

    public void getTask(Map<String, String> map){
        getTaskModel.getTask(map, new TasklistModel.TaskListener() {
            @Override
            public void getTaskSuccess(TaskInfo info) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskListimp.gettasksuccess(info);
                    }
                });
            }

            @Override
            public void tokenChange() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskListimp.tokenchange();

                    }
                });
            }

            @Override
            public void getTaskFailed(String msg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskListimp.gettaskfailed(msg);

                    }
                });
            }
        });
    }
}
