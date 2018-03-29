package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 123 on 2017/2/4.
 */

public class Prescription implements Serializable {

    /**
     * success : true
     * code : 0
     * message : 获取患者处方列表成功
     * data : [{"id":"8a2b4be8598866dd0159a746764d0036","creator":"00000000000000000000000000000001","createdAt":"2017-01-16 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-16 20:35:30","status":1,"total":0,"suggestion":"","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"test","admissionNumber":"","outpatientNumber":"","disease":"123213","hidden":0},{"id":"8a2b4be8598866dd015990ba825c0015","creator":"00000000000000000000000000000001","createdAt":"2017-01-12 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-12 11:30:59","status":1,"total":0,"suggestion":"1111","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"陈医生","admissionNumber":"","outpatientNumber":"","disease":"11","hidden":0},{"id":"8a2b4be8598866dd015990bad93e0017","creator":"00000000000000000000000000000001","createdAt":"2017-01-12 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-12 11:31:22","status":1,"total":0,"suggestion":"223","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"张医生","admissionNumber":"","outpatientNumber":"","disease":"122","hidden":0},{"id":"8a2b4be8598866dd015990bc46f5001a","creator":"00000000000000000000000000000001","createdAt":"2017-01-12 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-12 11:32:55","status":1,"total":0,"suggestion":"222","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"陈中意","admissionNumber":"","outpatientNumber":"","disease":"22","hidden":0},{"id":"8a2b4be8598866dd015990bc95d0001c","creator":"00000000000000000000000000000001","createdAt":"2017-01-12 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-12 11:33:15","status":1,"total":0,"suggestion":"222","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"陈中意","admissionNumber":"","outpatientNumber":"","disease":"22","hidden":0},{"id":"8a2b4be8598866dd015990bcd14b001e","creator":"00000000000000000000000000000001","createdAt":"2017-01-12 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-12 11:33:31","status":1,"total":0,"suggestion":"222","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"张医生","admissionNumber":"","outpatientNumber":"","disease":"22","hidden":0},{"id":"8a2b4be8598866dd01599152d9820027","creator":"00000000000000000000000000000001","createdAt":"2017-01-12 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-12 14:17:23","status":1,"total":0,"suggestion":"222","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"陈中意","admissionNumber":"","outpatientNumber":"","disease":"222","hidden":0},{"id":"8a2b4be8598866dd01598af4f1390004","creator":"00000000000000000000000000000001","createdAt":"2017-01-11 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-11 08:37:06","status":1,"total":0,"suggestion":"11111","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"1111","admissionNumber":"","outpatientNumber":"","disease":"111","hidden":0},{"id":"8a2b4be8598866dd01598af561870007","creator":"00000000000000000000000000000001","createdAt":"2017-01-11 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-11 08:37:34","status":1,"total":0,"suggestion":"22","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"222","admissionNumber":"","outpatientNumber":"","disease":"222","hidden":0},{"id":"8a2b4be8598778440159884917ce0008","creator":"00000000000000000000000000000001","createdAt":"2017-01-10 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2017-01-10 20:10:09","status":1,"total":0,"suggestion":"秀梅","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"test","admissionNumber":"","outpatientNumber":"","disease":"秀梅","hidden":0},{"id":"8a2b4be859296567015938a52c040001","creator":"00000000000000000000000000000001","createdAt":"2016-12-26 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2016-12-26 09:01:06","status":1,"total":0,"suggestion":"5","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"3","admissionNumber":"2","outpatientNumber":"1","disease":"4","hidden":0},{"id":"8a2b4be859293aad0159293b9a1f0001","creator":null,"createdAt":"2016-12-23 00:00:00","updator":null,"updatedAt":"2016-12-23 09:11:29","status":1,"total":0,"suggestion":"test","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"test","admissionNumber":"0002","outpatientNumber":"0001","disease":"test","hidden":0},{"id":"8a2b4be859293aad0159293ba2440003","creator":null,"createdAt":"2016-12-23 00:00:00","updator":null,"updatedAt":"2016-12-23 09:11:31","status":1,"total":0,"suggestion":"test","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"test","admissionNumber":"0002","outpatientNumber":"0001","disease":"test","hidden":0},{"id":"8a2b4be859293aad0159293c9a2f0005","creator":null,"createdAt":"2016-12-23 00:00:00","updator":null,"updatedAt":"2016-12-23 09:12:35","status":1,"total":0,"suggestion":"test","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"test","admissionNumber":"0002","outpatientNumber":"0001","disease":"test","hidden":0},{"id":"8a2b4be859293aad0159293d70ca0007","creator":null,"createdAt":"2016-12-23 00:00:00","updator":null,"updatedAt":"2016-12-23 09:13:30","status":1,"total":0,"suggestion":"test","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"test","admissionNumber":"0002","outpatientNumber":"0001","disease":"test","hidden":0},{"id":"8a2b4be859293aad015929452798000a","creator":"00000000000000000000000000000001","createdAt":"2016-12-23 00:00:00","updator":"00000000000000000000000000000001","updatedAt":"2016-12-23 09:21:55","status":1,"total":0,"suggestion":"test","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"tets","admissionNumber":"002","outpatientNumber":"0001","disease":"test","hidden":0},{"id":"8a2b4be858f749e701590042860f0001","creator":null,"createdAt":"2016-12-15 10:14:37","updator":null,"updatedAt":"2016-12-15 10:14:37","status":1,"total":null,"suggestion":"5","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"3","admissionNumber":"2","outpatientNumber":"1","disease":"4","hidden":0},{"id":"8a2b4be858de0dfd0158e1374a58001b","creator":null,"createdAt":"2016-12-09 09:34:07","updator":null,"updatedAt":"2016-12-09 09:34:07","status":1,"total":null,"suggestion":"","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"宋医生","admissionNumber":"ZY0003","outpatientNumber":"MZ0002","disease":"没事","hidden":0},{"id":"8a2b4be858de0dfd0158de103bf70006","creator":null,"createdAt":"2016-12-08 18:52:36","updator":null,"updatedAt":"2016-12-08 18:52:36","status":1,"total":null,"suggestion":"多给机会，多刺激","doctorId":null,"patientId":"8a2b4be858de0dfd0158de0eb2b80001","source":2,"doctorName":"陈医生","admissionNumber":"ZY0001","outpatientNumber":"MZ0001","disease":"拖延症","hidden":0}]
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
         * id : 8a2b4be8598866dd0159a746764d0036
         * creator : 00000000000000000000000000000001
         * createdAt : 2017-01-16 00:00:00
         * updator : 00000000000000000000000000000001
         * updatedAt : 2017-01-16 20:35:30
         * status : 1
         * total : 0
         * suggestion :
         * doctorId : null
         * patientId : 8a2b4be858de0dfd0158de0eb2b80001
         * source : 2
         * doctorName : test
         * admissionNumber :
         * outpatientNumber :
         * disease : 123213
         * hidden : 0
         */

        private String id;
        private String creator;
        private String createdAt;
        private String updator;
        private String updatedAt;
        private int status;
        private int total;
        private String suggestion;
        private Object doctorId;
        private String patientId;
        private int source;
        private String doctorName;
        private String admissionNumber;
        private String outpatientNumber;
        private String disease;
        private int hidden;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public Object getDoctorId() {
            return doctorId;
        }

        public void setDoctorId(Object doctorId) {
            this.doctorId = doctorId;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getAdmissionNumber() {
            return admissionNumber;
        }

        public void setAdmissionNumber(String admissionNumber) {
            this.admissionNumber = admissionNumber;
        }

        public String getOutpatientNumber() {
            return outpatientNumber;
        }

        public void setOutpatientNumber(String outpatientNumber) {
            this.outpatientNumber = outpatientNumber;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public int getHidden() {
            return hidden;
        }

        public void setHidden(int hidden) {
            this.hidden = hidden;
        }
    }
}
