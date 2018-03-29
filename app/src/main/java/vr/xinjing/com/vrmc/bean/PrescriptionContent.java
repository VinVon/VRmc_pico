package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 处方内容列表
 * Created by 123 on 2017/2/4.
 */

public class PrescriptionContent implements Serializable{

    /**
     * success : true
     * code : 0
     * message : 获取患者处方内容列表成功
     * data : [{"unitPrice":400,"content_name":"测试视频2","prescriptionId":"8a2b4be8598866dd0159a746764d0036","period":1,"creator":"00000000000000000000000000000001","contentId":"8a2b4be857e696e20157f99ab81f0006","periodUnit":1,"frequency":1,"content_helpCode":"video2","createdAt":"2017-01-16 20:35:30","times":1,"content_type":1,"price":400,"lastUseAt":"2017-01-16 20:35:42","updator":"00000000000000000000000000000001","id":"8a2b4be8598866dd0159a74676680037","status":null,"useTimes":1,"updatedAt":"2017-01-16 20:35:42"}]
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
         * unitPrice : 400.0
         * content_name : 测试视频2
         * prescriptionId : 8a2b4be8598866dd0159a746764d0036
         * period : 1
         * creator : 00000000000000000000000000000001
         * contentId : 8a2b4be857e696e20157f99ab81f0006
         * periodUnit : 1
         * frequency : 1
         * content_helpCode : video2
         * createdAt : 2017-01-16 20:35:30
         * times : 1
         * content_type : 1
         * price : 400.0
         * lastUseAt : 2017-01-16 20:35:42
         * updator : 00000000000000000000000000000001
         * id : 8a2b4be8598866dd0159a74676680037
         * status : null
         * useTimes : 1
         * updatedAt : 2017-01-16 20:35:42
         */

        private double unitPrice;
        private String content_name;
        private String prescriptionId;
        private int period;
        private String creator;
        private String contentId;
        private int periodUnit;
        private int frequency;
        private String content_helpCode;
        private String createdAt;
        private int times;
        private int content_type;
        private double price;
        private String lastUseAt;
        private String updator;
        private String id;
        private Object status;
        private int useTimes;
        private String updatedAt;

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getContent_name() {
            return content_name;
        }

        public void setContent_name(String content_name) {
            this.content_name = content_name;
        }

        public String getPrescriptionId() {
            return prescriptionId;
        }

        public void setPrescriptionId(String prescriptionId) {
            this.prescriptionId = prescriptionId;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public int getPeriodUnit() {
            return periodUnit;
        }

        public void setPeriodUnit(int periodUnit) {
            this.periodUnit = periodUnit;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getContent_helpCode() {
            return content_helpCode;
        }

        public void setContent_helpCode(String content_helpCode) {
            this.content_helpCode = content_helpCode;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getContent_type() {
            return content_type;
        }

        public void setContent_type(int content_type) {
            this.content_type = content_type;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getLastUseAt() {
            return lastUseAt;
        }

        public void setLastUseAt(String lastUseAt) {
            this.lastUseAt = lastUseAt;
        }

        public String getUpdator() {
            return updator;
        }

        public void setUpdator(String updator) {
            this.updator = updator;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public int getUseTimes() {
            return useTimes;
        }

        public void setUseTimes(int useTimes) {
            this.useTimes = useTimes;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
