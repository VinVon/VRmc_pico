package vr.xinjing.com.vrmc.imp;

/**
 * 下载进度条显示接口回调
 * Created by raytine on 2017/4/17.
 */

public interface Downprogress {
    void progressToUi(String name,long percent);
    void progressToFailed();
    void progressToComplete();
}
