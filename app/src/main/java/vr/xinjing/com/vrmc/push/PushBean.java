package vr.xinjing.com.vrmc.push;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by raytine on 2017/12/12.
 */

public class PushBean implements Serializable{

    /**
     * new : true
     * creator :
     * patientcaseId : 2c90e40a5d2a79e2015d2a9b0f32001e
     * endType : false
     * jmvalues : 1
     * type : 1
     * userId : 2c90e5c760445ca00160447549f90004
     * content : 2c90e40a5f61eee9015fd4ca740e1494
     * createdAt : null
     * playType : true
     * clickRecordId : 8a8c7e8b60486d8501604871b8820002
     * prescriptionContentId :
     * updator :
     * id :
     * isencryption : 0
     * status : 0
     * updatedAt : null
     * voidpassword : 0,0,0,24,102,116,121,112,109,112,52,50,0,0,0,0,109,112,52,50,109,112,52,49,0,4,110,88,109,111,111,118,0,0,0,108,109,118,104,100,0,0,0,0,214,47,23,251,214,47,24,27,0,1,95,144,3,101,143,128,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0
     */

    @SerializedName("new")
    private boolean newX;
    private String creator;
    private String patientcaseId;
    private boolean endType;
    private int jmvalues;
    private int type;
    private String userId;
    private String content;
    private Object createdAt;
    private boolean playType;
    private String clickRecordId;
    private String prescriptionContentId;
    private String updator;
    private String id;
    private int isencryption;
    private int status;
    private Object updatedAt;
    private String voidpassword;

    public boolean isNewX() {
        return newX;
    }

    public void setNewX(boolean newX) {
        this.newX = newX;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPatientcaseId() {
        return patientcaseId;
    }

    public void setPatientcaseId(String patientcaseId) {
        this.patientcaseId = patientcaseId;
    }

    public boolean isEndType() {
        return endType;
    }

    public void setEndType(boolean endType) {
        this.endType = endType;
    }

    public int getJmvalues() {
        return jmvalues;
    }

    public void setJmvalues(int jmvalues) {
        this.jmvalues = jmvalues;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPlayType() {
        return playType;
    }

    public void setPlayType(boolean playType) {
        this.playType = playType;
    }

    public String getClickRecordId() {
        return clickRecordId;
    }

    public void setClickRecordId(String clickRecordId) {
        this.clickRecordId = clickRecordId;
    }

    public String getPrescriptionContentId() {
        return prescriptionContentId;
    }

    public void setPrescriptionContentId(String prescriptionContentId) {
        this.prescriptionContentId = prescriptionContentId;
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

    public int getIsencryption() {
        return isencryption;
    }

    public void setIsencryption(int isencryption) {
        this.isencryption = isencryption;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVoidpassword() {
        return voidpassword;
    }

    public void setVoidpassword(String voidpassword) {
        this.voidpassword = voidpassword;
    }
}
