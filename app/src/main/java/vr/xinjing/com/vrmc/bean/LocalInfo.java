package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2017/2/28.
 */

public class LocalInfo implements Serializable{
    private String username;
    private String password;
    private boolean firstLogin = false;
    private String token;
    public LocalInfo() {
    }

    public LocalInfo(String username, String password, boolean firstLogin, String token) {
        this.username = username;
        this.password = password;
        this.firstLogin = firstLogin;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
