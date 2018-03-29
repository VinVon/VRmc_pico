package vr.xinjing.com.vrmc.bean;

import java.io.Serializable;

/**
 * 单一内容详情
 * Created by raytine on 2017/2/10.
 */

public class PrescriptionInfo implements Serializable {

    /**
     * success : true
     * code : 0
     * message : 获取单个内容成功
     * data : {"ext":{"id":"8a2b4be857e696e20157f99ab8320009","content":"http://vod.med-vision.cn/test/ijsKXh8AKD.mp4?Expires=1497513736&OSSAccessKeyId=LTAIJNwd9qadQHPz&Signature=bbg8Gq74BOkq1ely4tcpjjborEA%3D","videosize":140812699},"creator":"00000000000000000000000000000001","hidden":0,"videoupdateAt":"2017-06-05 16:32:50","description":"<p>测试视频2<\/p>","remark":"测试视频2","helpCode":"video21112312","type":1,"otherdisease":"","duration":0,"createdAt":"2016-10-25 10:10:49","isFree":0,"price":400,"name":"测试视频254554","clicks":0,"updator":"00000000000000000000000000000001","coverPic":"http://static.dosnsoft.com/test%2F1477361448881.png","id":"8a2b4be857e696e20157f99ab81f0006","status":2,"updatedAt":"2017-06-05 16:32:50"}
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

    public static class DataBean {
        /**
         * ext : {"id":"8a2b4be857e696e20157f99ab8320009","content":"http://vod.med-vision.cn/test/ijsKXh8AKD.mp4?Expires=1497513736&OSSAccessKeyId=LTAIJNwd9qadQHPz&Signature=bbg8Gq74BOkq1ely4tcpjjborEA%3D","videosize":140812699}
         * creator : 00000000000000000000000000000001
         * hidden : 0
         * videoupdateAt : 2017-06-05 16:32:50
         * description : <p>测试视频2</p>
         * remark : 测试视频2
         * helpCode : video21112312
         * type : 1
         * otherdisease :
         * duration : 0
         * createdAt : 2016-10-25 10:10:49
         * isFree : 0
         * price : 400
         * name : 测试视频254554
         * clicks : 0
         * updator : 00000000000000000000000000000001
         * coverPic : http://static.dosnsoft.com/test%2F1477361448881.png
         * id : 8a2b4be857e696e20157f99ab81f0006
         * status : 2
         * updatedAt : 2017-06-05 16:32:50
         */

        private ExtBean ext;
        private String creator;
        private int hidden;
        private String videoupdateAt;
        private String description;
        private String remark;
        private String helpCode;
        private int type;
        private String otherdisease;
        private int duration;
        private String createdAt;
        private int isFree;
        private int price;
        private String name;
        private int clicks;
        private String updator;
        private String coverPic;
        private String id;
        private int status;
        private String updatedAt;

        public ExtBean getExt() {
            return ext;
        }

        public void setExt(ExtBean ext) {
            this.ext = ext;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public int getHidden() {
            return hidden;
        }

        public void setHidden(int hidden) {
            this.hidden = hidden;
        }

        public String getVideoupdateAt() {
            return videoupdateAt;
        }

        public void setVideoupdateAt(String videoupdateAt) {
            this.videoupdateAt = videoupdateAt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getHelpCode() {
            return helpCode;
        }

        public void setHelpCode(String helpCode) {
            this.helpCode = helpCode;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getOtherdisease() {
            return otherdisease;
        }

        public void setOtherdisease(String otherdisease) {
            this.otherdisease = otherdisease;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getClicks() {
            return clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

        public String getUpdator() {
            return updator;
        }

        public void setUpdator(String updator) {
            this.updator = updator;
        }

        public String getCoverPic() {
            return coverPic;
        }

        public void setCoverPic(String coverPic) {
            this.coverPic = coverPic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public static class ExtBean {
            /**
             * id : 8a2b4be857e696e20157f99ab8320009
             * content : http://vod.med-vision.cn/test/ijsKXh8AKD.mp4?Expires=1497513736&OSSAccessKeyId=LTAIJNwd9qadQHPz&Signature=bbg8Gq74BOkq1ely4tcpjjborEA%3D
             * videosize : 140812699
             */

            private String id;
            private String content;
            private long videosize;
            private int isencryption ;//0未加密 1已加密

            public int getIsencryption() {
                return isencryption;
            }

            public void setIsencryption(int isencryption) {
                this.isencryption = isencryption;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getVideosize() {
                return videosize;
            }

            public void setVideosize(long videosize) {
                this.videosize = videosize;
            }
        }
    }
}
