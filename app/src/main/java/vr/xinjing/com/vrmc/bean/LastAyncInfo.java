package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 同步内容
 * Created by raytine on 2017/3/3.
 */

public class LastAyncInfo implements Serializable {

    /**
     * success : true
     * code : 0
     * message : 获取同步内容成功
     * data : [{"id":"8a2b4be857e696e20157f9ed14ed000c","creator":"00000000000000000000000000000001","createdAt":"2016-10-25","updator":"00000000000000000000000000000001","updatedAt":"2017-02-20","helpCode":"111","name":"111","type":2,"isFree":0,"price":1,"remark":"11","status":1,"coverPic":"http://static.dosnsoft.com/test%2F1477366845954.jpg","hidden":0},{"id":"8a2b4be857e696e20157f99ab81f0006","creator":"00000000000000000000000000000001","createdAt":"2016-10-25","updator":"00000000000000000000000000000001","updatedAt":"2016-10-25","helpCode":"video2","name":"测试视频2","type":1,"isFree":0,"price":400,"remark":"测试视频2","status":2,"coverPic":"http://static.dosnsoft.com/test%2F1477361448881.png","hidden":0},{"id":"8a2b4be857e696e20157f999a4cf0002","creator":"00000000000000000000000000000001","createdAt":"2016-10-25","updator":"00000000000000000000000000000001","updatedAt":"2016-12-08","helpCode":"video1","name":"测试视频1","type":1,"isFree":1,"price":0,"remark":"测试视频1","status":2,"coverPic":"http://static.dosnsoft.com/test%2F1477361378308.jpg","hidden":0},{"id":"8a2b4be857d718e70157db72c2a70009","creator":"00000000000000000000000000000001","createdAt":"2016-10-19","updator":"00000000000000000000000000000001","updatedAt":"2016-10-19","helpCode":"audio2","name":"测试音频2","type":2,"isFree":0,"price":15,"remark":"测试音频2","status":2,"coverPic":"http://static.dosnsoft.com/test%2F1476855513736.png","hidden":0},{"id":"8a2b4be857d718e70157db71ea0b0007","creator":"00000000000000000000000000000001","createdAt":"2016-10-19","updator":"00000000000000000000000000000001","updatedAt":"2016-10-19","helpCode":"audio1","name":"测试音频1","type":2,"isFree":1,"price":null,"remark":"测试音频1","status":2,"coverPic":"http://static.dosnsoft.com/test%2F1476855457995.png","hidden":0}]
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
         * id : 8a2b4be857e696e20157f9ed14ed000c
         * creator : 00000000000000000000000000000001
         * createdAt : 2016-10-25
         * updator : 00000000000000000000000000000001
         * updatedAt : 2017-02-20
         * helpCode : 111
         * name : 111
         * type : 2
         * isFree : 0
         * price : 1
         * remark : 11
         * status : 1
         * coverPic : http://static.dosnsoft.com/test%2F1477366845954.jpg
         * hidden : 0
         */

        private String id;
        private String creator;
        private String createdAt;
        private String updator;
        private String updatedAt;
        private String helpCode;
        private String name;
        private int type;
        private int isFree;
        private int price;
        private String remark;
        private int status;
        private String coverPic;
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

        public String getHelpCode() {
            return helpCode;
        }

        public void setHelpCode(String helpCode) {
            this.helpCode = helpCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIsFree() {
            return isFree;
        }

        public void setIsFree(int isFree) {
            this.isFree = isFree;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
        }

        public int getHidden() {
            return hidden;
        }

        public void setHidden(int hidden) {
            this.hidden = hidden;
        }
    }
}
