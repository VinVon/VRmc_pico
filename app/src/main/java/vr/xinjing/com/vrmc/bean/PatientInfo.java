package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;

/**
 * Created by raytine on 2017/2/9.
 */

public class PatientInfo implements Serializable{

    /**
     * success : true
     * code : 0
     * message : 根据条件查询患者信息成功
     * data : {"token":null,"userId":"8a2b4be858de0dfd0158de0eb2b80001","realname":"陈中意","email":null,"mobile":"13868001234","medicareType":null,"medicareCardNumber":null,"idNumber":null,"age":30,"address":null,"emergencyContact":null,"emergencyContactPhone":null,"gender":1}
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
         * token : null
         * userId : 8a2b4be858de0dfd0158de0eb2b80001
         * realname : 陈中意
         * email : null
         * mobile : 13868001234
         * medicareType : null
         * medicareCardNumber : null
         * idNumber : null
         * age : 30
         * address : null
         * emergencyContact : null
         * emergencyContactPhone : null
         * gender : 1
         */

        private Object token;
        private String userId;
        private String realname;
        private Object email;
        private String mobile;
        private Object medicareType;
        private Object medicareCardNumber;
        private Object idNumber;
        private int age;
        private Object address;
        private Object emergencyContact;
        private Object emergencyContactPhone;
        private int gender;

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getMedicareType() {
            return medicareType;
        }

        public void setMedicareType(Object medicareType) {
            this.medicareType = medicareType;
        }

        public Object getMedicareCardNumber() {
            return medicareCardNumber;
        }

        public void setMedicareCardNumber(Object medicareCardNumber) {
            this.medicareCardNumber = medicareCardNumber;
        }

        public Object getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(Object idNumber) {
            this.idNumber = idNumber;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getEmergencyContact() {
            return emergencyContact;
        }

        public void setEmergencyContact(Object emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Object getEmergencyContactPhone() {
            return emergencyContactPhone;
        }

        public void setEmergencyContactPhone(Object emergencyContactPhone) {
            this.emergencyContactPhone = emergencyContactPhone;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }
}
