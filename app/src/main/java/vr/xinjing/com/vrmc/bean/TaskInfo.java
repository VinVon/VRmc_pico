package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by raytine on 2017/4/10.
 */

public class TaskInfo implements Serializable {

    /**
     * success : true
     * code : 0
     * message : 获取任务列表成功
     * data : [{"id":"8a2b4be85cc9b84c015cc9c08e56001e","creator":"8a2b4be85b6a59c0015b6a5af7cb0002","createdAt":"2017-06-21 16:24:19","updator":"8a2b4be85b6a59c0015b6a5af7cb0002","updatedAt":"2017-06-21 16:24:19","type":2,"content":null,"userId":"8a2b4be85b556f26015b564d63f20006","status":3,"voidpassword":123456789,"prescriptionContentId":null,"playType":false,"endType":true},{"id":"8a2b4be85cc9b84c015cc9c08e58001f","creator":"8a2b4be85b6a59c0015b6a5af7cb0002","createdAt":"2017-06-21 16:24:19","updator":"8a2b4be85b6a59c0015b6a5af7cb0002","updatedAt":"2017-06-21 16:24:19","type":1,"content":"8a2b4be857e696e20157f99ab81f0006","userId":"8a2b4be85b556f26015b564d63f20006","status":2,"voidpassword":123456789,"prescriptionContentId":"8a2b4be859631f9e01596328a3300009","playType":true,"endType":false}]
     */

    private boolean success;
    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * id : 8a2b4be85cc9b84c015cc9c08e56001e
         * creator : 8a2b4be85b6a59c0015b6a5af7cb0002
         * createdAt : 2017-06-21 16:24:19
         * updator : 8a2b4be85b6a59c0015b6a5af7cb0002
         * updatedAt : 2017-06-21 16:24:19
         * type : 2
         * content : null
         * userId : 8a2b4be85b556f26015b564d63f20006
         * status : 3
         * voidpassword : 123456789
         * prescriptionContentId : null
         * playType : false
         * endType : true
         */

        private String id;
        private String creator;
        private String createdAt;
        private String updator;
        private String updatedAt;
        private int type;
        private Object content;
        private String userId;
        private int status;
        private String voidpassword;
        private int isencryption ;//0未加密 1已加密
        private int jmvalues;
        private Object prescriptionContentId;
        private boolean playType;
        private boolean endType;

        public int getIsencryption() {
            return isencryption;
        }

        public void setIsencryption(int isencryption) {
            this.isencryption = isencryption;
        }

        public int getJmvalues() {
            return jmvalues;
        }

        public void setJmvalues(int jmvalues) {
            this.jmvalues = jmvalues;
        }

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getVoidpassword() {
            return voidpassword;
        }

        public void setVoidpassword(String voidpassword) {
            this.voidpassword = voidpassword;
        }

        public Object getPrescriptionContentId() {
            return prescriptionContentId;
        }

        public void setPrescriptionContentId(Object prescriptionContentId) {
            this.prescriptionContentId = prescriptionContentId;
        }

        public boolean isPlayType() {
            return playType;
        }

        public void setPlayType(boolean playType) {
            this.playType = playType;
        }

        public boolean isEndType() {
            return endType;
        }

        public void setEndType(boolean endType) {
            this.endType = endType;
        }
    }
}
