package vr.xinjing.com.vrmc.UrlPath;

/**
 * Created by raytine on 2017/2/8.
 */

public class UrlHttp {
    //测试地址czy.dd.dosnsoft.com  ==10.12.254.34:8080
    public static final String BASE_URL="http://test.med-vision.cn/api/v1/";
    //正式地址
//    public static final String BASE_URL="http://support.med-vision.cn/api/v1/";
    //登录
    public static final String PATH_LOGIN =BASE_URL+"appVrRoom/login";
    //登录
    public static final String PATH_PUSH=BASE_URL+"appVrRoom/PushReciver";
    //获取患者接口
//    public static final String PATH_PATIENT=BASE_URL+"appVrRoom/patient/getByKeyword";
    //获取患者处方列表接口
//    public static final String PATH_PRESCRIPTION = BASE_URL+"appVrRoom/prescription/getByPatientId";
    //获取内容列表接口
//    public static final String PATH_PRESCRIPTIONCONTENT=BASE_URL+"appVrRoom/prescriptionContent/getByPrescriptionId";
    //获取单一内容
    public static final String PATH_PRESCRIPTIONCONTENT_ONE = BASE_URL+"appVrRoom/content/info";
    //获取版本信息
    public  static final String PATH_VERSION=BASE_URL+"appVrRoom/getVersion";
    //获取同步内容
    public  static  final String PATH_AYNCLAST=BASE_URL+"appVrRoom/content/sync";
    //获取任务列表
    public  static  final String PATH_GETTASK=BASE_URL+"appVrRoom/task/list";
    //结束任务
    public  static  final String PATH_OVERTASK=BASE_URL+"appVrRoom/task/end";
}
