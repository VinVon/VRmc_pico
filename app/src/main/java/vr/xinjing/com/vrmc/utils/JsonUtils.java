package vr.xinjing.com.vrmc.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vr.xinjing.com.vrmc.bean.LoginInfo;
import vr.xinjing.com.vrmc.bean.PatientInfo;
import vr.xinjing.com.vrmc.bean.Prescription;
import vr.xinjing.com.vrmc.bean.PrescriptionContent;
import vr.xinjing.com.vrmc.bean.PrescriptionInfo;
import vr.xinjing.com.vrmc.update.VersionInfo;

/**
 * Created by raytine on 2017/2/9.
 */

public class JsonUtils {
    public static VersionInfo jsonVersion(String info){
        VersionInfo infos  = new VersionInfo();
        try {
            JSONObject object  = new JSONObject(info);
            infos.setSuccess(object.getBoolean("success"));
            infos.setCode(object.getInt("code"));
            infos.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            VersionInfo.DataBean dataBean = new VersionInfo.DataBean();
            dataBean.setId(obj.getString("id"));
            dataBean.setCreatedAt(obj.getString("createdAt"));
            dataBean.setCreator(obj.getString("creator"));
            dataBean.setUpdator(obj.getString("updator"));
            dataBean.setUpdatedAt(obj.getString("updatedAt"));
            dataBean.setAppCode(obj.getString("appCode"));
            dataBean.setDownloadUrl(obj.getString("downloadUrl"));
            dataBean.setVersionCode(obj.getString("versionCode"));
            dataBean.setVersionDesc(obj.getString("versionDesc"));
            dataBean.setVersionName(obj.getString("versionName"));
            dataBean.setVersionSize(obj.getString("versionSize"));
            infos.setData(dataBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }
    public static LoginInfo jsonPares(String info){
        LoginInfo infos  = new LoginInfo();
        try {
            JSONObject object  = new JSONObject(info);
            infos.setSuccess(object.getBoolean("success"));
            infos.setCode(object.getInt("code"));
            infos.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            LoginInfo.DataBean dataBean = new LoginInfo.DataBean();
            dataBean.setVrRoomId(obj.getString("vrRoomId"));
            dataBean.setUserId(obj.getString("userId"));
            dataBean.setUsername(obj.getString("username"));
            dataBean.setRealname(obj.getString("realname"));
            dataBean.setToken(obj.getString("token"));
            infos.setData(dataBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infos;
    }
    public static PatientInfo patientPares(String info){
        PatientInfo patientInfo = new PatientInfo();
        try {
            JSONObject object = new JSONObject(info);
            patientInfo.setSuccess(object.getBoolean("success"));
            patientInfo.setCode(object.getInt("code"));
            patientInfo.setMessage(object.getString("message"));
            JSONObject obj = object.getJSONObject("data");
            PatientInfo.DataBean pBean = new PatientInfo.DataBean();
            pBean.setToken(obj.getString("token"));
            pBean.setUserId(obj.getString("userId"));
            pBean.setRealname(obj.getString("realname"));
            pBean.setEmail(obj.getString("email"));
            pBean.setMobile(obj.getString("mobile"));
            pBean.setMedicareType(obj.getString("medicareType"));
            pBean.setMedicareCardNumber(obj.getString("medicareCardNumber"));
            pBean.setIdNumber(obj.getString("idNumber"));
            pBean.setAge(obj.getInt("age"));
            pBean.setAddress(obj.getString("address"));
            pBean.setEmergencyContact(obj.getString("emergencyContact"));
            pBean.setEmergencyContactPhone(obj.getString("emergencyContactPhone"));
            pBean.setGender(obj.getInt("gender"));
            patientInfo.setData(pBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return patientInfo;
    }
    //解析处方列表
    public static Prescription prescriptionPares(String info){
        Prescription prescription = new Prescription();
        try {
            JSONObject object = new JSONObject(info);
            prescription.setSuccess(object.getBoolean("success"));
            prescription.setCode(object.getInt("code"));
            prescription.setMessage(object.getString("message"));
            JSONArray array = object.getJSONArray("data");
            List<Prescription.DataBean> mlist = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Prescription.DataBean presBean = new Prescription.DataBean();
                presBean.setId(obj.getString("id"));
                presBean.setCreator(obj.getString("creator"));
                presBean.setCreatedAt(obj.getString("createdAt"));
                presBean.setUpdator(obj.getString("updator"));
                presBean.setUpdatedAt(obj.getString("updatedAt"));
                presBean.setStatus(obj.getInt("status"));
//                presBean.setTotal(obj.getInt("total"));
                presBean.setSuggestion(obj.getString("suggestion"));
                presBean.setDoctorId(obj.getString("doctorId"));
                presBean.setPatientId(obj.getString("patientId"));
                presBean.setSource(obj.getInt("source"));
                presBean.setDoctorName(obj.getString("doctorName"));
                presBean.setAdmissionNumber(obj.getString("admissionNumber"));
                presBean.setOutpatientNumber(obj.getString("outpatientNumber"));
                presBean.setDisease(obj.getString("disease"));
                presBean.setHidden(obj.getInt("hidden"));
                mlist.add(presBean);
            }
            prescription.setData(mlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prescription;
    }
    //解析内容列表
    public static PrescriptionContent prescriptionContentParess(String infos){
        PrescriptionContent prescriptionc = new PrescriptionContent();
        try {
            JSONObject object = new JSONObject(infos);
            prescriptionc.setSuccess(object.getBoolean("success"));
            prescriptionc.setCode(object.getInt("code"));
            prescriptionc.setMessage(object.getString("message"));
            JSONArray array = object.getJSONArray("data");
            List<PrescriptionContent.DataBean> mlist = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                PrescriptionContent.DataBean presBean = new PrescriptionContent.DataBean();
//                presBean.setUnitPrice(obj.getDouble("unitPrice"));
                presBean.setContent_name(obj.getString("content_name"));
//                presBean.setPrescriptionId(obj.getString("prescriptionId"));
//                presBean.setPeriod(obj.getInt("period"));
//                presBean.setCreator(obj.getString("creator"));
                presBean.setContentId(obj.getString("contentId"));
//                presBean.setPeriodUnit(obj.getInt("periodUnit"));
//                presBean.setFrequency(obj.getInt("frequency"));
//                presBean.setContent_helpCode(obj.getString("content_helpCode"));
//                presBean.setCreatedAt(obj.getString("createdAt"));
                presBean.setTimes(obj.getInt("times"));
                presBean.setContent_type(obj.getInt("content_type"));
//                presBean.setPrice(obj.getDouble("price"));
//                presBean.setLastUseAt(obj.getString("lastUseAt"));
//                presBean.setUpdator(obj.getString("updator"));
                presBean.setId(obj.getString("id"));
//                presBean.setStatus(obj.getString("status"));
                presBean.setUseTimes(obj.getInt("useTimes"));
                presBean.setUpdatedAt(obj.getString("updatedAt"));
                mlist.add(presBean);
            }
            prescriptionc.setData(mlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prescriptionc;
    }
    //解析单一内容
//    public static PrescriptionInfo prescriptioninfoPares(String info){
//        PrescriptionInfo po = new PrescriptionInfo();
//        PrescriptionInfo.DataBean dn = new PrescriptionInfo.DataBean();
//        PrescriptionInfo.DataBean.ExtBean en = new PrescriptionInfo.DataBean.ExtBean();
//        try {
//            JSONObject object = new JSONObject(info);
//            po.setSuccess(object.getBoolean("success"));
//            po.setCode(object.getInt("code"));
//            po.setMessage(object.getString("message"));
//            JSONObject obj = object.getJSONObject("data");
//            JSONObject ob = obj.getJSONObject("ext");
//            en.setId(ob.getString("id"));
////            en.setCreator(ob.getString("creator"));
////            en.setCreatedAt(ob.getString("createdAt"));
////            en.setUpdator(ob.getString("updator"));
////            en.setUpdatedAt(ob.getString("updatedAt"));
////            en.setContentId(ob.getString("contentId"));
//            en.setContent(ob.getString("content"));
//            dn.setExt(en);
//            dn.setCreator(obj.getString("creator"));
//
//            dn.setHidden(obj.getInt("hidden"));
//            dn.setVideoupdateAt(obj.getString("videoupdateAt"));
//            dn.setRemark(obj.getString("remark"));
//            dn.setHelpCode(obj.getString("helpCode"));
//            dn.setType(obj.getInt("type"));
//            dn.setCreatedAt(obj.getString("createdAt"));
//            dn.setIsFree(obj.getInt("isFree"));
//            dn.setPrice(obj.getDouble("price"));
//            dn.setName(obj.getString("name"));
//            dn.setUpdatedAt(obj.getString("updatedAt"));
//            dn.setUpdator(obj.getString("updator"));
//            dn.setCoverPic(obj.getString("coverPic"));
//            dn.setId(obj.getString("id"));
//            dn.setStatus(obj.getInt("status"));
//            po.setData(dn);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        return po;
//    }
}
