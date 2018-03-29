package vr.xinjing.com.vrmc.imp;

import vr.xinjing.com.vrmc.bean.TaskInfo;

/**
 * Created by raytine on 2017/4/10.
 */

public interface TaskListimp {
    public void gettasksuccess(TaskInfo info);
    public void gettaskfailed(String msh);
    public void tokenchange();
}
