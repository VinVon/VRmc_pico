package vr.xinjing.com.vrmc.update;

import java.io.Serializable;

/**
 * Created by raytine on 2017/2/22.
 */

public class VersionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * success : true
     * code : 0
     * message : 获取成功
     * data : {"id":"8a2b4be85a65caf3015a65eacacf0004","creator":"00000000000000000000000000000001","createdAt":"2017-02-22 21:02:51","updator":"00000000000000000000000000000001","updatedAt":"2017-02-22 21:02:51","appCode":"VR_ANDROID","downloadUrl":"http://xinjingstatic.oss-cn-hangzhou.aliyuncs.com/dev/VRmc.apk","versionCode":"1","versionDesc":"内测版","versionName":"1.0","versionSize":"6.474M"}
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
         * id : 8a2b4be85a65caf3015a65eacacf0004
         * creator : 00000000000000000000000000000001
         * createdAt : 2017-02-22 21:02:51
         * updator : 00000000000000000000000000000001
         * updatedAt : 2017-02-22 21:02:51
         * appCode : VR_ANDROID
         * downloadUrl : http://xinjingstatic.oss-cn-hangzhou.aliyuncs.com/dev/VRmc.apk
         * versionCode : 1
         * versionDesc : 内测版
         * versionName : 1.0
         * versionSize : 6.474M
         */

        private String id;
        private String creator;
        private String createdAt;
        private String updator;
        private String updatedAt;
        private String appCode;
        private String downloadUrl;
        private String versionCode;
        private String versionDesc;
        private String versionName;
        private String versionSize;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdator() {
            return updator;
        }

        public void setUpdator(String updator) {
            this.updator = updator;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getAppCode() {
            return appCode;
        }

        public void setAppCode(String appCode) {
            this.appCode = appCode;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionDesc() {
            return versionDesc;
        }

        public void setVersionDesc(String versionDesc) {
            this.versionDesc = versionDesc;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionSize() {
            return versionSize;
        }

        public void setVersionSize(String versionSize) {
            this.versionSize = versionSize;
        }
    }
}
