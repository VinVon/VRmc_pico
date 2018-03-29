package vr.xinjing.com.vrmc.update;

/**
 * Created by raytine on 2017/2/22.
 */

public interface UpdateStatus {
    /**
     * 没有新版本
     */
    public static int NO = 1;

    /**
     * 有新版本
     */
    public static int YES = 2;

    /**
     * 链接超时
     */
    public static int TIMEOUT = 3;

    /**
     * 没有wifi
     */
    public static int NOWIFI = 4;

    /**
     * 数据解析出错
     */
    public static int ERROR = -1;
}
