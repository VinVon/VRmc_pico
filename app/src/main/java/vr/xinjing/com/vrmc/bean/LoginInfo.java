package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2017/2/9.
 */

public class LoginInfo implements Serializable{


    /**
     * success : true
     * code : 0
     * message : 登录成功
     * data : {"vrRoomId":"8a2b4be859b48ead0159b49136b00002","userId":"8a2b4be859b48ead0159b491616a0003","username":"test","realname":"test","token":"8a2b4be85a1d1080015a2088c4720012"}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * vrRoomId : 8a2b4be859b48ead0159b49136b00002
         * userId : 8a2b4be859b48ead0159b491616a0003
         * username : test
         * realname : test
         * token : 8a2b4be85a1d1080015a2088c4720012
         */

        private String vrRoomId;
        private String userId;
        private String username;
        private String realname;
        private String token;

        public String getVrRoomId() {
            return vrRoomId;
        }

        public void setVrRoomId(String vrRoomId) {
            this.vrRoomId = vrRoomId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
