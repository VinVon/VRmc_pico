package vr.xinjing.com.vrmc.bean.requestbody;

/**
 * Created by raytine on 2017/2/28.
 */

public class UserReq {

    private String appId;
    private String appVersion;
    private String channel;
    private String deviceModel;
    private String deviceSystem;
    private String deviceVersion;
    private String password;
    private String username;
    private String pushId;
    public UserReq() {
    }
    public UserReq(String password, String username) {
        this.password = password;
        this.username = username;
    }
    public UserReq(String appId, String appVersion, String channel, String deviceModel, String deviceSystem, String deviceVersion, String password, String username,String pushId) {
        this.appId = appId;
        this.appVersion = appVersion;
        this.channel = channel;
        this.deviceModel = deviceModel;
        this.deviceSystem = deviceSystem;
        this.deviceVersion = deviceVersion;
        this.password = password;
        this.username = username;
        this.pushId = pushId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public void setDeviceSystem(String deviceSystem) {
        this.deviceSystem = deviceSystem;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
